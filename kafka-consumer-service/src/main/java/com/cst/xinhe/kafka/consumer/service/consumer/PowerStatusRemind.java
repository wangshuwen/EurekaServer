package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.persistence.dao.lack_electric.LackElectricMapper;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @description: 欠电上传信息消费类
 * @author: lifeng
 * @create: 2019-01-15 11:28
 **/
@Component
public class PowerStatusRemind {

    private static final Logger logger = LoggerFactory.getLogger(PowerStatusRemind.class);
    @Resource
    private LackElectricMapper lackElectricMapper;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    private Map<String, Object> map;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(9);
//    @Resource
//    private StaffTerminalRelationService staffTerminalRelationService;

    public PowerStatusRemind() {
        this.map = new HashMap<>();
    }

   // @KafkaListener(id = "processPowerStatus", topics = "powerStatus.tut")
    private static final String TOPIC = "powerStatus.tut";

//    private void process(String str){
//
//        Thread thread = Thread.currentThread();
//        logger.error("ThreadId: {}" , thread.getId());
//        JSONObject jsonObject = JSON.parseObject(str);
//
//        Integer uploadId = jsonObject.getInteger("uploadId");
//        Date uploadTime = jsonObject.getDate("uploadTime");
//        Integer electricValue = jsonObject.getInteger("electricValue");
//        Integer lackType = jsonObject.getInteger("lackType");
//
//        LackElectric lackElectric = new LackElectric();
//        lackElectric.setUploadTime(uploadTime);
//        lackElectric.setElectricValue(electricValue);
//        lackElectric.setLackType(lackType);
//        lackElectric.setUploadId(uploadId);
////            lackElectric.setIsRead(0);
//        LackElectricExample lackElectricExample = new LackElectricExample();
//        LackElectricExample.Criteria criteria = lackElectricExample.createCriteria();
////            criteria.andLackTypeEqualTo(1);
//        criteria.andUploadIdEqualTo(uploadId);
//
////        StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByTerminalId(uploadId);
//        StaffTerminalRelation staffTerminalRelation = staffGroupTerminalServiceClient.findNewRelationByTerminalId(uploadId);
//
//        Integer relationId = staffTerminalRelation.getStaffTerminalRelationId();
//
//        lackElectric.setUploadId(relationId);
//
//        List<LackElectric> electricList = lackElectricMapper.selectByExample(lackElectricExample);
//
//        if (electricList != null && electricList.size() > 0){
//            lackElectricMapper.updateByExampleSelective(lackElectric, lackElectricExample);
//        }else{
//            lackElectricMapper.insertSelective(lackElectric);
//        }
//        LackElectricExample lackElectricExample1 = new LackElectricExample();
//        map.put("batteryAlarmValue", lackElectricMapper.selectByExample(lackElectricExample1).size());
//
//        try {
////            WebsocketServer.sendInfo(JSON.toJSONString(new WebSocketData(6, map)));
//            wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(6, map)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    private void processT(List<ConsumerRecord<?, ?>> records){

        Thread thread = Thread.currentThread();
        logger.error("ThreadId: {}" , thread.getId());
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                JSONObject jsonObject = JSON.parseObject(str);

                Integer uploadId = jsonObject.getInteger("uploadId");
                Date uploadTime = jsonObject.getDate("uploadTime");
                Integer electricValue = jsonObject.getInteger("electricValue");
                Integer lackType = jsonObject.getInteger("lackType");

                LackElectric lackElectric = new LackElectric();
                lackElectric.setUploadTime(uploadTime);
                lackElectric.setElectricValue(electricValue);
                lackElectric.setLackType(lackType);
                lackElectric.setUploadId(uploadId);
//            lackElectric.setIsRead(0);
                LackElectricExample lackElectricExample = new LackElectricExample();
                LackElectricExample.Criteria criteria = lackElectricExample.createCriteria();
//            criteria.andLackTypeEqualTo(1);
                criteria.andUploadIdEqualTo(uploadId);

//                StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findNewRelationByTerminalId(uploadId);
                StaffTerminalRelation staffTerminalRelation = staffGroupTerminalServiceClient.findNewRelationByTerminalId(uploadId);

                Integer relationId = staffTerminalRelation.getStaffTerminalRelationId();

                lackElectric.setUploadId(relationId);

                List<LackElectric> electricList = lackElectricMapper.selectByExample(lackElectricExample);

                if (electricList != null && electricList.size() > 0) {
                    lackElectricMapper.updateByExampleSelective(lackElectric, lackElectricExample);
                } else {
                    lackElectricMapper.insertSelective(lackElectric);
                }
                LackElectricExample lackElectricExample1 = new LackElectricExample();
                map.put("batteryAlarmValue", lackElectricMapper.selectByExample(lackElectricExample1).size());

                try {
//                    WebsocketServer.sendInfo(JSON.toJSONString(new WebSocketData(6, map)));
                    wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(6, map)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @KafkaListener(groupId = "PowerStatusRemind", id = "PowerStatusRemindid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendSiteInfo0(List<ConsumerRecord<?, ?>> records) {
        logger.info("Id0 Listener, Thread ID: " + Thread.currentThread().getId());
        logger.info("Id0 records size " +  records.size());

                fixedThreadPool.execute(() -> processT(records));
//            }
//        }
    }
    @KafkaListener(groupId = "PowerStatusRemind", id = "PowerStatusRemindid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendSiteInfo1(List<ConsumerRecord<?, ?>> records) {
        logger.info("Id0 Listener, Thread ID: " + Thread.currentThread().getId());
        logger.info("Id0 records size " +  records.size());

                fixedThreadPool.execute(() -> processT(records));
//            }
//        }
    }
    @KafkaListener(groupId = "PowerStatusRemind", id = "PowerStatusRemindid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendSiteInfo2(List<ConsumerRecord<?, ?>> records) {
        logger.info("Id0 Listener, Thread ID: " + Thread.currentThread().getId());
        logger.info("Id0 records size " +  records.size());

                fixedThreadPool.execute(() -> processT(records));
            }
//        }
//    }
}

