package com.cst.xinhe.terminal.monitor.server.process;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.constant.ConstantUrl;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.utils.FileType;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dao.chat.ChatMsgMapper;
import com.cst.xinhe.persistence.dao.staff.StaffMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.terminal.monitor.server.client.KafkaClient;
import com.cst.xinhe.terminal.monitor.server.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ProcessVoice
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/29 13:11
 * @Vserion v0.0.1
 */
@Component
public class ProcessVoice {


    private volatile static ProcessVoice processVoice;
    @Resource
    private KafkaClient kafkaClient;

    private Date date=new Date();
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        processVoice = this;
        processVoice.kafkaClient = this.kafkaClient;
    }



    public static ProcessVoice getProcessVoice() {
        if (null == processVoice) {
            synchronized (ProcessVoice.class) {
                if (null == processVoice) {
                    processVoice = new ProcessVoice();
                }
            }
        }
        return processVoice;
    }

    //TODO 修改为 applicationContext.getBean()
    private static Map<String, String> mapOfSequenceId = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 创建语音文件，语音数据写入本地
     */
    public String process(RequestData requestData) {
        Integer sequenceId = requestData.getSequenceId();
        Integer terminalId = requestData.getTerminalId();
        String stationIp = requestData.getStationIp();
        String terminalIp = requestData.getTerminalIp();
        Date postTime = requestData.getTime();
        int length = requestData.getLength();
//        int cmd = requestData.getCmd();
        byte[] body = requestData.getBody();
//        int ndName = ((body[0] & 0xff) << 8) + (body[1] & 0xff);
        int bodyLength = body.length;
        /*int count = ((body[0] & 0xff) << 8) + ((body[1] & 0xff));
        logger.info("来自[" + requestData.getTerminalId() + "]的第[" + count + "]个语音数据包");*/

        //创建根目录文件夹
        StringBuilder folderName = new StringBuilder(ConstantUrl.basePath);
        folderName.append(requestData.getTerminalId()); //根路径

        String currentTime = DateConvert.convert(new Date(), 15);

        //创建文件名称  格式 ： 时间 + 终端ID + 序列号
        StringBuilder fileName = new StringBuilder(); //文件夹
        fileName.append(currentTime).append(requestData.getTerminalId()).append(requestData.getSequenceId());

       /* StringBuilder file = new StringBuilder(fileName);
        file.append(".").append(FileType.ogg);  //文件名称*/

        /*StringBuilder realPath = new StringBuilder(folderName);
        realPath.append(fileName).append(".").append(FileType.ogg);*/

        //  logger.info("文件路径 : [" + folderName.toString() + "] , 文件名称 ：[" + fileName.toString() + "] ，文件类型 [" + FileType.PCM + "]");

        String key = Integer.toString(sequenceId) + terminalId;
        if (!mapOfSequenceId.containsKey(key)) {  //如果不存在，则认为是语音的第一条 也可以利用语音数据包的序号判断 数组的31，32位
            mapOfSequenceId.put(key, currentTime);
            //logger.info("当前终端: ID>[" + terminalId + "] || IP>[" + terminalId + "] 当前语音队列中无该终端语音信息，已创建该音频文件");
            FileUtils.createFile(folderName.toString(), fileName.toString(), FileType.ogg);
            WriteFileUtil.writeByteToFile(folderName.toString() + fileName.toString() + FileType.ogg, body, 0, bodyLength , true);
        } else {

            Date currentDate = new Date();
            //避免终端设备发两次66778899
            if(currentDate.getTime()-date.getTime()>1000){
                date=new Date();
                if (length == 38) {
                    logger.info("接收到单条语音得55667788");
                    // 执行 PCM ==> WAV 函数
                    //  logger.info("当前终端: ID>[" + terminalId + "] || IP>[" + terminalIp + "] 接收语音结束，执行PCM转WAV");
                    //  logger.info("当前终端: ID>[" + terminalId + "] || IP>[" + terminalIp + "] 已被移除语音队列");
                    String v = mapOfSequenceId.get(key);
                    StringBuilder real = new StringBuilder(folderName);
                    real.append(File.separator).append(v).append(terminalId).append(sequenceId).append(".").append(FileType.ogg);
                    try {
                        FileUtils.createFile(folderName.toString(), v + terminalId + sequenceId, FileType.ogg);
                        // PcmToWavConvert.convert(temp.toString(), real.toString());
                        //FileUtils.removeFile(temp.toString());
                        ChatMsg chatMsg = new ChatMsg();
                        chatMsg.setPostIp(terminalIp);
                        chatMsg.setStationIp(stationIp);
                        chatMsg.setPostTime(postTime);
                        chatMsg.setConvertTime(new Date());
                        chatMsg.setPostMsg(real.toString());
                        chatMsg.setStatus(false);
                        chatMsg.setTerminalId(terminalId);
                        //存储序列号：时间+终端编号+序列号
                        chatMsg.setSequenceId(DateConvert.convert(postTime, 15)+terminalId+sequenceId);
                        kafkaClient.sendChatMsgData("receiveVoice.tut",JSON.toJSONString(chatMsg));
                        return real.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mapOfSequenceId.remove(Integer.toString(sequenceId) + terminalId);
                }
            }

            // logger.info("当前终端: ID>[" + terminalId + "] || IP>[" + terminalIp + "] 当前语音队列中已存在终端语音信息，持续接收中......");

            String val = mapOfSequenceId.get(key);


            WriteFileUtil.writeByteToFile(folderName + File.separator + val + terminalId + sequenceId + "." + FileType.ogg, body, 0, bodyLength, true);
        }
        return "error";

    }


}
