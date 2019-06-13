package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.*;
import com.cst.xinhe.persistence.dao.chat.ChatMsgMapper;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.cst.xinhe.persistence.vo.resp.VoiceWSRespVo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName VoiceInfoProcess
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/11 15:21
 * @Vserion v0.0.1
 */
@Component
public class VoiceInfoProcess {

    private static final Logger logger = LoggerFactory.getLogger(VoiceInfoProcess.class);

//    @Resource
//    WebsocketServer websocketServer;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

//    @Resource
//    private StaffService staffService;



//    @Resource
//    private GasInfoService gasInfoService;

    @Resource
    private WebServiceClient webServiceClient;

    @Resource
    private ChatMsgMapper chatMsgMapper;



//    @Resource
//    private StaffTerminalRelationService staffTerminalRelationService;
//    @Resource
//    private RangSettingService rangSettingService;


//    @Resource
//    private Constant constant;

    @Value("basePath")
    String basePath;
    @Value("webBaseUrl")
    String webBaseUrl;
//    @Resource
//    KafkaSender kafkaSender;

    private static final String TOPIC = "voice.tut";
    private void process(String str){

        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" , thread.getId());
        JSONObject jsonObject = JSON.parseObject(str);

        String postIp = jsonObject.getString("postIp");
        String stationIp = jsonObject.getString("stationIp");
        Date postTime = jsonObject.getDate("postTime");
        Date convertTime = jsonObject.getDate("convertTime");
        String postMsg = jsonObject.getString("postMsg");
        boolean status = jsonObject.getBoolean("status");
        Integer terminalId = jsonObject.getInteger("terminalId");
        String sequenceId = jsonObject.getString("sequenceId");//后补

        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setPostIp(postIp);
        chatMsg.setStationIp(stationIp);
        chatMsg.setPostTime(postTime);
        chatMsg.setConvertTime(convertTime);
        chatMsg.setPostMsg(postMsg);
        chatMsg.setStatus(status);

//        Map<String, Object> map = staffService.findStaffIdByTerminalId(terminalId);
        Map<String, Object> map = webServiceClient.findStaffIdByTerminalId(terminalId);
        Integer staffId = (Integer) map.get("staff_id");
        chatMsg.setPostUserId(staffId);
        chatMsg.setReceiceUserId(1);
        chatMsg.setSequenceId(sequenceId);

//            StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByTerminalId(terminalId);
//            chatMsg.setTerminalId(staffTerminalRelation.getStaffTerminalRelationId());
        chatMsg.setTerminalId(terminalId);
        chatMsgMapper.insertSelective(chatMsg);
//        chatMsgServiceClient.insertChatMsgSelective(chatMsg);
//        GasWSRespVO staffInfo = staffService.findStaffNameByTerminalId(terminalId);
        GasWSRespVO staffInfo = webServiceClient.findStaffNameByTerminalId(terminalId);

//        Map<String, Object> resultMap = staffService.findStaffGroupAndDeptByStaffId(staffInfo.getStaffId());
        Map<String, Object> resultMap = webServiceClient.findStaffGroupAndDeptByStaffId(staffInfo.getStaffId());

        GasWSRespVO gasWSRespVO = null;
        try {
//            gasWSRespVO = gasInfoService.findGasInfoByStaffIdAndTerminalId(terminalId);
            gasWSRespVO = webServiceClient.findGasInfoByStaffIdAndTerminalId(terminalId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gasWSRespVO.setStaffId(staffInfo.getStaffId());
        gasWSRespVO.setStaffName(staffInfo.getStaffName());
        //TODO 封装WS 数据

        String voiceUrl = postMsg.replace(basePath, webBaseUrl);
        VoiceWSRespVo voiceWSRespVo = new VoiceWSRespVo();
        voiceWSRespVo.setStaffId(gasWSRespVO.getStaffId());
        voiceWSRespVo.setStatus(status);
        voiceWSRespVo.setGasWSRespVO(gasWSRespVO);

        voiceWSRespVo.setVoiceUrl(voiceUrl);
        voiceWSRespVo.setUploadTime(postTime);
        voiceWSRespVo.setTerminalId(terminalId);

        voiceWSRespVo.setDeptName((String) resultMap.get("dept_name"));
        voiceWSRespVo.setGroupName((String) resultMap.get("group_name"));
        HashMap<Object, Object> voiceMap = new HashMap<>();
        voiceMap.put("voiceInfo",voiceWSRespVo);
        voiceMap.put("code",1);//code=1,表示单条语音

        //发送铃声的url
//        List<RangSetting> rangs = rangSettingService.findRangByType(1);
        List<RangSetting> rangs = webServiceClient.findRangByType(1);

        for (RangSetting rang : rangs) {
            if(rang.getStatus()==1){
                voiceMap.put("url",rang.getUrl());
            }
        }
        try {
//            websocketServer.sendInfo(JSONObject.toJSONString(new WebSocketData(2,voiceMap)));
            wsPushServiceClient.sendWebsocketServer(JSONObject.toJSONString(new WebSocketData(2,voiceMap)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  //  @KafkaListener(id = "VoiceInfoToDataBaseAndWs", topics = "voice.tut")
    @KafkaListener(id = "VoiceInfoProcessid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void processVoiceInfoToDB0(List<ConsumerRecord<?, ?>> records) {
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                process(str);
            }
        }
    }

    @KafkaListener(id = "VoiceInfoProcessid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void processVoiceInfoToDB1(List<ConsumerRecord<?, ?>> records) {
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                process(str);
            }
        }
    }

    @KafkaListener(id = "VoiceInfoProcessid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void processVoiceInfoToDB2(List<ConsumerRecord<?, ?>> records) {
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                process(str);
            }
        }
    }

//        @KafkaListener(id = "VoiceInfoToWebSocket", topics = "voiceToWs.tut")
//    public void processVoiceInfoToWS(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) throws ParseException {
//        //判断是否NULL
//        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//
//        if (kafkaMessage.isPresent()) {
//            //获取消息
//            Object message = kafkaMessage.get();
//
//            String str = (String) message;
//            JSONObject jsonObject = JSON.parseObject(str);
//            logger.info(jsonObject.toJSONString());
//            logger.info("SEND WS");
//            String postIp = jsonObject.getString("postIp");
//            String stationIp = jsonObject.getString("stationIp");
//            Date postTime = jsonObject.getDate("postTime");
//            Date convertTime = jsonObject.getDate("convertTime");
//            String postMsg = jsonObject.getString("postMsg");
//            boolean status = jsonObject.getBoolean("status");
//            Integer terminalId = jsonObject.getInteger("terminalId");
//
//            //生成语音文件路径
//            String voiceUrl = postMsg.replace(ConstantValue.basePath, ConstantValue.webBaseUrl);
//
//            GasWSRespVO staffInfo = staffService.findStaffNameByTerminalId(terminalId);
//
//            Map<String, Object> resultMap = staffService.findStaffGroupAndDeptByStaffId(staffInfo.getStaffId());
//
//            GasWSRespVO gasWSRespVO = gasInfoService.findGasInfoByStaffIdAndTerminalId(terminalId);
//
//            gasWSRespVO.setStaffId(staffInfo.getStaffId());
//            gasWSRespVO.setStaffName(staffInfo.getStaffName());
//            //TODO 封装WS 数据
//
//            VoiceWSRespVo voiceWSRespVo = new VoiceWSRespVo();
//            voiceWSRespVo.setStaffId(gasWSRespVO.getStaffId());
//            voiceWSRespVo.setStatus(status);
//            voiceWSRespVo.setGasWSRespVO(gasWSRespVO);
//
//            voiceWSRespVo.setVoiceUrl(voiceUrl);
//            voiceWSRespVo.setUploadTime(postTime);
//            voiceWSRespVo.setTerminalId(terminalId);
//
//            voiceWSRespVo.setDeptName((String) resultMap.get("dept_name"));
//            voiceWSRespVo.setGroupName((String) resultMap.get("group_name"));
//
//            System.out.println("json:" + JSONObject.toJSONString(voiceWSRespVo));
//            try {
//                WSVoiceServer.sendInfo(JSONObject.toJSONString(voiceWSRespVo));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            logger.info("Receive： +++++++++++++++ Topic:" + topic);
//            logger.info("Receive： +++++++++++++++ Record:" + record);
//            logger.info("Receive： +++++++++++++++ Message:" + message);
//        }
//    }
}
