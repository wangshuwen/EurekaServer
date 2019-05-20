package com.cst.xinhe.chatmessage.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.base.context.SpringContextUtil;
import com.cst.xinhe.chatmessage.service.client.KafkaClient;
import com.cst.xinhe.chatmessage.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.chatmessage.service.client.TerminalMonitorClient;
import com.cst.xinhe.chatmessage.service.service.CallService;
import com.cst.xinhe.chatmessage.service.service.ChatMessageService;
import com.cst.xinhe.chatmessage.service.util.IpAddr;
import com.cst.xinhe.common.netty.utils.FileUtils;
import com.cst.xinhe.common.utils.FileType;
import com.cst.xinhe.common.utils.SequenceIdGenerate;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dto.voice.VoiceDto;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-05 10:35
 **/
@Service
public class CallServiceImpl implements CallService {

//    private static final Integer terminalId = 65539;
//    private static final String terminalIp = "1.68";
//    private static final Integer stationId = 1;
//    private static final String stationIp = "1.251";


//    @Resource
//    private TerminalService terminalService;

//    @Resource
//    private TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    private ChatMessageService chatMsgService;

//    @Resource
//    private KafkaSender kafkaSender;

    @Resource
    private KafkaClient kafkaClient;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private TerminalMonitorClient terminalMonitorClient;

    @Value("webBaseUrl")
    public String webBaseUrl ;
    @Value("basePath")
    public String basePath ;
    @Value("rangBasePath")
    public String rangBasePath ;

    @Override
    public String callStaffByStaffId(MultipartFile wavFile, Integer staffId, Integer userId) {
//        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
        Integer terminalId = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        StringBuffer folderName = new StringBuffer(basePath);
        folderName.append(terminalId).append(File.separator);
        StringBuffer fileName = new StringBuffer(DateConvert.convert(new Date(), 15));
        Integer seq = SequenceIdGenerate.getSequenceId();
        fileName.append(terminalId).append(seq);
        FileUtils.createFile(folderName.toString(), fileName.toString(), FileType.WAV);
        File sendFile = new File(folderName.toString() + fileName.toString() + "." + FileType.WAV);
        try {
            wavFile.transferTo(sendFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String realUrl = folderName.toString() + fileName.toString() + "." + FileType.WAV;

        VoiceDto voiceDto = new VoiceDto();

//        Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
        Map<String, Object> terminalInfo = staffGroupTerminalServiceClient.selectTerminalIpInfoByTerminalId(terminalId);

        if (terminalInfo != null && !terminalInfo.isEmpty()) {
            String stationIp = (String) terminalInfo.get("station_ip");

            String stationIps[] = stationIp.split("\\.");


            Integer stationIp1 = Integer.parseInt(stationIps[0]);
            Integer stationIp2 = Integer.parseInt(stationIps[1]);


            String terminalIp = (String) terminalInfo.get("terminal_ip");


            String terminalIps[] = terminalIp.split("\\.");
            Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
            Integer terminalIp2 = Integer.parseInt(terminalIps[1]);

            Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

            Integer stationPort = (Integer) terminalInfo.get("station_port");

            Integer stationId = (Integer) terminalInfo.get("station_id");
            voiceDto.setStaffId(staffId);
            voiceDto.setUserId(userId);
            voiceDto.setVoiceUrl(realUrl);
            voiceDto.setSeqId(seq);
            voiceDto.setStationId(stationId);
            voiceDto.setStationIp1(stationIp1);
            voiceDto.setStationIp2(stationIp2);
            voiceDto.setTerminalId(terminalId);
            voiceDto.setTerminalPort(terminalPort);
            voiceDto.setStationPort(stationPort);
            voiceDto.setTerminalIp1(terminalIp1);
            voiceDto.setTerminalIp2(terminalIp2);
            voiceDto.setTerminalIp(terminalIp);

//            kafkaSender.send(voiceDto, "voiceSender.tut");
            kafkaClient.send("voiceSender.tut", JSON.toJSONString(voiceDto), terminalPort);
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setTerminalId(terminalId);
            chatMsg.setSequenceId(seq.toString());
            chatMsg.setConvertTime(new Date());
            chatMsg.setStationIp(stationIp);
            chatMsg.setReceiceUserId(staffId);
            chatMsg.setIsDel(0);
            chatMsg.setPostUserId(userId);
            chatMsg.setPostTime(new Date());
            chatMsg.setPostIp(IpAddr.getCurrentIp().getHostAddress());
            chatMsg.setPostMsg(realUrl);
            chatMsg.setStatus(false);
            chatMsg.setReceiveIp(terminalIp);
            chatMsgService.insertRecord(chatMsg);


        }
        return realUrl.replace(basePath, webBaseUrl);
    }

    @Override
    public boolean pingTerminal(Integer staffId) {

//        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
        Integer terminalId = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        if (terminalId != null&&terminalId!=0) {
//            Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
            Map<String, Object> terminalInfo =staffGroupTerminalServiceClient.selectStaffInfoByTerminalId(terminalId);
            if (terminalInfo != null && !terminalInfo.isEmpty()) {
                if (terminalInfo.containsKey("terminal_ip") && terminalInfo.containsKey("terminal_port")){
                    String terminalIp = (String) terminalInfo.get("terminal_ip");
                    Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

                    String terminalIps[] = terminalIp.split("\\.");
                    Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
                    Integer terminalIp2 = Integer.parseInt(terminalIps[1]);
                    StringBuffer ipPort = new StringBuffer();
                    ipPort.append(terminalIp1).append(".").append(terminalIp2).append(":").append(terminalPort);
//                    Channel channel = ChannelMap.getChannelByName(ipPort.toString());
                    boolean flag = terminalMonitorClient.getChanelByName(ipPort.toString());
//                    if (channel == null) {
//                        return false;
//                    }
//                    return true;
                    return flag;
                }

            }
            return false;
//            String stationIp = (String) terminalInfo.get("station_ip");
//
//            String stationIps[] = stationIp.split("\\.");
//
//            Integer stationIp1 = Integer.parseInt(stationIps[0]);
//            Integer stationIp2 = Integer.parseInt(stationIps[1]);
//
//            String terminalIp = (String) terminalInfo.get("terminal_ip");
//
//            String terminalIps[] = terminalIp.split("\\.");
//            Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
//            Integer terminalIp2 = Integer.parseInt(terminalIps[1]);
//
////        Integer terminalId = (Integer)terminalInfo.get("terminal_num");
//            Integer stationId = (Integer) terminalInfo.get("station_id");
//            RequestData requestData = new RequestData();
//            requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
//            requestData.setTerminalId(terminalId);
//            requestData.setStationId(stationId);
//            requestData.setTerminalIp1(terminalIp1);
//            requestData.setTerminalIp2(terminalIp2);
//            requestData.setStationIp1(stationIp1);
//            requestData.setStationIp2(stationIp2);
//            requestData.setTerminalIp(terminalIp);
//            requestData.setLength(34);
//            requestData.setSequenceId(111);
//            requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_SEARCH);
//            requestData.setResult((byte) -1);
//            requestData.setNodeCount((byte) 0);
//            requestData.setNdName(ConstantValue.MSG_BODY_NODE_NAME_CHECK_ONLINE);
//            ResponseData responseData = new ResponseData();
//            responseData.setCode((byte) -1);
//            responseData.setCustomMsg(requestData);
//            SingletonClient.getSingletonClient().sendCmd(responseData);

        }
        return false;
    }

    @Override
    public boolean pingTerminalByTerminalNum(Integer terminalNum) {
        //        Integer terminalId = terminalService.findTerminalInfoByStaffId(staffId);
//        Integer terminalNum = staffGroupTerminalServiceClient.findTerminalInfoByStaffId(staffId);
        if (terminalNum != null&&terminalNum!=0) {
//            Map<String, Object> terminalInfo = terminalUpdateIpMapper.selectTerminalIpInfoByTerminalId(terminalId);
            Map<String, Object> terminalInfo =staffGroupTerminalServiceClient.selectTerminalIpInfoByTerminalId(terminalNum);
            if (null != terminalInfo && !terminalInfo.isEmpty()) {
                if (terminalInfo.containsKey("terminal_ip") && terminalInfo.containsKey("terminal_port")){
                    String terminalIp = (String) terminalInfo.get("terminal_ip");
                    Integer terminalPort = (Integer) terminalInfo.get("terminal_port");

                    String terminalIps[] = terminalIp.split("\\.");
                    Integer terminalIp1 = Integer.parseInt(terminalIps[0]);
                    Integer terminalIp2 = Integer.parseInt(terminalIps[1]);
                    StringBuffer ipPort = new StringBuffer();
                    ipPort.append(terminalIp1).append(".").append(terminalIp2).append(":").append(terminalPort);
                    boolean flag = terminalMonitorClient.getChanelByName(ipPort.toString());
                    return flag;
                }

            }
            return false;
        }
        return false;
    }
}
