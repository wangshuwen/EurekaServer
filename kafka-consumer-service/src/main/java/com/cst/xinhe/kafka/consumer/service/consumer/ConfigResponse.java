package com.cst.xinhe.kafka.consumer.service.consumer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.kafka.consumer.service.client.WebServiceClient;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @desc 基站的网络配置响应队列消费类
 * @date 2018-1-14 13：41
 * @author lifeng
 * @version 1.0
 */
@Component
public class ConfigResponse {

    private static final Logger logger = LoggerFactory.getLogger(ConfigResponse.class);
//    @Resource
//    private BaseStationMapper baseStationMapper;

    @Resource
    private WebServiceClient  webServiceClient;

    private static final String TOPIC = "configResp.tut";
   // @KafkaListener(id = "confRespToDB", topics = "configResp.tut")
   @KafkaListener(groupId = "ConfigResponse", id = "ConfigResponseid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void listen0(List<ConsumerRecord<?, ?>> records) {
       for (ConsumerRecord<?, ?> record : records) {
           Optional<?> kafkaMessage = Optional.ofNullable(record.value());
           logger.info("Received: " + record);
           if (kafkaMessage.isPresent()) {
               Object message = kafkaMessage.get();
               String str = (String) message;
              configResp(str);
           }
       }
    }
    @KafkaListener(groupId = "ConfigResponse", id = "ConfigResponseid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void listen1(List<ConsumerRecord<?, ?>> records) {
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                configResp(str);
            }
        }
    }
    @KafkaListener(groupId = "ConfigResponse", id = "ConfigResponseid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void listen2(List<ConsumerRecord<?, ?>> records) {
        for (ConsumerRecord<?, ?> record : records) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            logger.info("Received: " + record);
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                String str = (String) message;
                configResp(str);
            }
        }
    }
    private void configResp(String message){
        JSONObject jsonObject = JSON.parseObject(message);
        Integer stationNum = jsonObject.getInteger("stationId");
        BaseStation baseStation = new BaseStation();
        baseStation.setBaseStationNum(stationNum);
        baseStation.setRemark("1");

        webServiceClient.updateByStationNumSelective(baseStation);
    }
}
