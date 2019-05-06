package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.persistence.dao.lack_electric.LackElectricMapper;
import com.cst.xinhe.persistence.dao.malfunction.MalfunctionMapper;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: demo
 * @description: 自检处理类
 * @author: lifeng
 * @create: 2019-02-27 10:01
 **/
@Component
public class MalfunctionProcess extends BaseLog {

    private Map<String ,Object> map;

    @Resource
    private LackElectricMapper lackElectricMapper;

    @Resource
    private MalfunctionMapper malfunctionMapper;

//    @Resource
//    private TerminalService terminalService;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

//    @Resource
//    private StaffService staffService;
//
//    @Resource
//    private StaffTerminalRelationService staffTerminalRelationService;
    private ExecutorService fixedThreadPool = Executors.newCachedThreadPool();
    private ExecutorService fixedThreadPool1 = Executors.newCachedThreadPool();
    private ExecutorService fixedThreadPool2 = Executors.newCachedThreadPool();

    public MalfunctionProcess() {
        this.map = new HashMap<>();
    }

   // @KafkaListener(id = "malfunctionProcess", topics = "malfunction.tut")
    private static final String TOPIC = "malfunction.tut";
    private void process(String str){
        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" + thread.getId());
        JSONObject jsonObject = JSON.parseObject(str);

        Date selfCheckTime = jsonObject.getDate("selfCheckTime");
        int wifiError = jsonObject.getInteger("wifiError");
        int voiceError = jsonObject.getInteger("voiceError");
        int coError = jsonObject.getInteger("coError");
        int co2Error = jsonObject.getInteger("co2Error");
        int o2Error = jsonObject.getInteger("o2Error");
        int ch4Error = jsonObject.getInteger("ch4Error");
        int tError = jsonObject.getInteger("tError");
        int hError = jsonObject.getInteger("hError");
        int electric = jsonObject.getInteger("electric");
        int status = jsonObject.getInteger("status");
        String terminalIp = jsonObject.getString("terminalIp");
        int terminalId = jsonObject.getInteger("terminalId");
        Date createTime = new Date();
        //终端自检，电量大于30，移除缺电提醒
        if(electric > 30){
            LackElectric lackElectric = new LackElectric();
            lackElectric.setUploadId(terminalId);
            lackElectric.setLackType(1);
            staffGroupTerminalServiceClient.deleteLeLackElectricByLackElectric(lackElectric);
        } else {
//            LackElectricExample example = new LackElectricExample();
//            example.createCriteria().andUploadIdEqualTo(terminalId);
//            example.createCriteria().andLackTypeEqualTo(1);
            LackElectric lackElectric = new LackElectric();
            lackElectric.setElectricValue(electric);
            lackElectric.setUploadId(terminalId);
            lackElectric.setLackType(1);
//            lackElectricMapper.updateByExampleSelective(lackElectric,example);
            staffGroupTerminalServiceClient.updateLackElectric(lackElectric);
        }



        map.put("batteryAlarmValue", staffGroupTerminalServiceClient.getLackElectricList().size());

        try {
//            WebsocketServer.sendInfo(JSON.toJSONString(new WebSocketData(6, map)));
            wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(6, map)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Malfunction malfunction = new Malfunction();

        malfunction.setSelfCheckTime(selfCheckTime);
        malfunction.setCo2Error(co2Error);
        malfunction.setCoError(coError);
        malfunction.setCh4Error(ch4Error);
        malfunction.setWifiError(wifiError);
        malfunction.setVoiceError(voiceError);
        malfunction.setTerminalId(terminalId);
        malfunction.setTerminalIp(terminalIp);
        malfunction.setCreateTime(createTime);
        malfunction.setO2Error(o2Error);
        malfunction.settError(tError);
        malfunction.sethError(hError);
        malfunction.setStatus(status);
        malfunction.setElectric(electric);
        //判断上传的自检结果是否有异常的模块，如有异常则推送到客户端

//        Map<String, Object> map = staffService.findStaffIdByTerminalId(terminalId);
        Map<String, Object> map = staffGroupTerminalServiceClient.findStaffIdByTerminalId(terminalId);
        Integer staffId = (Integer) map.get("staff_id");

//        StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByStaffId(staffId);
        StaffTerminalRelation staffTerminalRelation = staffGroupTerminalServiceClient.findNewRelationByStaffId(staffId);

        malfunction.setTerminalId(staffTerminalRelation.getStaffTerminalRelationId());

        malfunctionMapper.insertSelective(malfunction);


        if (co2Error == 1 || coError == 1 || ch4Error == 1 || wifiError == 1 || voiceError == 1 || o2Error == 1 || tError == 1 ){
            try {
                int malfunctionValue  = ((Long)(malfunctionMapper.selectCountMalfunction().get("malfunctionCount"))).intValue();
                // 查询数据推送
                map.put("malfunctionValue",malfunctionValue);
//                WebsocketServer.sendInfo(JSONObject.toJSONString(new WebSocketData(4,map)));
                wsPushServiceClient.sendWebsocketServer(JSONObject.toJSONString(new WebSocketData(4,map)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processT(List<ConsumerRecord<?, ?>> records){
        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" + thread.getId());
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;

                JSONObject jsonObject = JSON.parseObject(str);

                Date selfCheckTime = jsonObject.getDate("selfCheckTime");
                int wifiError = jsonObject.getInteger("wifiError");
                int voiceError = jsonObject.getInteger("voiceError");
                int coError = jsonObject.getInteger("coError");
                int co2Error = jsonObject.getInteger("co2Error");
                int o2Error = jsonObject.getInteger("o2Error");
                int ch4Error = jsonObject.getInteger("ch4Error");
                int tError = jsonObject.getInteger("tError");
                int hError = jsonObject.getInteger("hError");
                int electric = jsonObject.getInteger("electric");
                int status = jsonObject.getInteger("status");
                String terminalIp = jsonObject.getString("terminalIp");
                int terminalId = jsonObject.getInteger("terminalId");
                Date createTime = new Date();
                //终端自检，电量大于30，移除缺电提醒
                if (electric > 30) {

                    LackElectric lackElectric  = new LackElectric();
                    lackElectric.setUploadId(terminalId);
                    lackElectric.setLackType(1);
                    staffGroupTerminalServiceClient.deleteLeLackElectricByLackElectric(lackElectric);
                } else {
//                    LackElectricExample example = new LackElectricExample();
//                    example.createCriteria().andUploadIdEqualTo(terminalId);
//                    example.createCriteria().andLackTypeEqualTo(1);
                    LackElectric lackElectric = new LackElectric();
                    lackElectric.setElectricValue(electric);
                    lackElectric.setUploadId(terminalId);
                    lackElectric.setLackType(1);
//                    lackElectricMapper.updateByExampleSelective(lackElectric, example);
                    staffGroupTerminalServiceClient.updateLackElectric(lackElectric);
                }


                map.put("batteryAlarmValue", staffGroupTerminalServiceClient.getLackElectricList().size());

                try {
//                    WebsocketServer.sendInfo(JSON.toJSONString(new WebSocketData(6, map)));
                    wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(6, map)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Malfunction malfunction = new Malfunction();

                malfunction.setSelfCheckTime(selfCheckTime);
                malfunction.setCo2Error(co2Error);
                malfunction.setCoError(coError);
                malfunction.setCh4Error(ch4Error);
                malfunction.setWifiError(wifiError);
                malfunction.setVoiceError(voiceError);
                malfunction.setTerminalId(terminalId);
                malfunction.setTerminalIp(terminalIp);
                malfunction.setCreateTime(createTime);
                malfunction.setO2Error(o2Error);
                malfunction.settError(tError);
                malfunction.sethError(hError);
                malfunction.setStatus(status);
                malfunction.setElectric(electric);
                //判断上传的自检结果是否有异常的模块，如有异常则推送到客户端

//                Map<String, Object> map = staffService.findStaffIdByTerminalId(terminalId);
                Map<String, Object> map = staffGroupTerminalServiceClient.findStaffIdByTerminalId(terminalId);
                Integer staffId = (Integer) map.get("staff_id");

//                StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByStaffId(staffId);
                StaffTerminalRelation staffTerminalRelation = staffGroupTerminalServiceClient.findNewRelationByStaffId(staffId);

                malfunction.setTerminalId(staffTerminalRelation.getStaffTerminalRelationId());

//                malfunctionMapper.insertSelective(malfunction);
                staffGroupTerminalServiceClient.addMalfunction(malfunction);
                if (co2Error == 1 || coError == 1 || ch4Error == 1 || wifiError == 1 || voiceError == 1 || o2Error == 1 || tError == 1) {
                    try {
//
                        int malfunctionValue = ((Long) (staffGroupTerminalServiceClient.getCountMalfunction().get("malfunctionCount"))).intValue();
                        // 查询数据推送
                        map.put("malfunctionValue", malfunctionValue);
//                        WebsocketServer.sendInfo(JSONObject.toJSONString(new WebSocketData(4, map)));
                        wsPushServiceClient.sendWebsocketServer(JSONObject.toJSONString(new WebSocketData(4, map)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
   @KafkaListener(groupId = "MalfunctionProcess", id = "MalfunctionProcessid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendSiteInfo0(List<ConsumerRecord<?, ?>> records) {
//       for (ConsumerRecord<?, ?> record : records) {
//           Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//           logger.info("Received: " + record);
//           if (kafkaMessage.isPresent()) {
//               Object message = kafkaMessage.get();
//               String str = (String) message;

               fixedThreadPool.execute(() -> processT(records));
//           }
//       }
    }
    @KafkaListener(groupId = "MalfunctionProcess", id = "MalfunctionProcessid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendSiteInfo1(List<ConsumerRecord<?, ?>> records) {
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool1.execute(() -> processT(records));
//            }
//        }
    }
    @KafkaListener(groupId = "MalfunctionProcess", id = "MalfunctionProcessid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendSiteInfo2(List<ConsumerRecord<?, ?>> records) {
//        for (ConsumerRecord<?, ?> record : records) {
//            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
//            logger.info("Received: " + record);
//            if (kafkaMessage.isPresent()) {
//                Object message = kafkaMessage.get();
//                String str = (String) message;

                fixedThreadPool2.execute(() -> processT(records));
//            }
//        }
    }

}
