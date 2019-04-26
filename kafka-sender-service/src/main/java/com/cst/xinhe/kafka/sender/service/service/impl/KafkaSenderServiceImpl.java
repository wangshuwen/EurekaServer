package com.cst.xinhe.kafka.sender.service.service.impl;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.kafka.sender.service.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 16:00
 **/
@Service
public class KafkaSenderServiceImpl implements KafkaSenderService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${spring.kafka.partition-number}")
    private Integer partitions;

    @Override
    public void sendData(String topic, RequestData requestData) {
        Integer partition = Math.abs(requestData.getTerminalPort().hashCode()) % partitions;
        kafkaTemplate.send(topic,partition,partition+ "",requestData);
    }

    @Override
    public void sendByPort(String topic, String obj, Integer port) {
        Integer partition = Math.abs(port.hashCode()) % partitions;
        kafkaTemplate.send(topic,partition,partition+ "",obj);
//        logger.info("kafka_producer send msg: {}",obj);
    }

    @Override
    public void sendByCount(String topic, String data, int count) {

    }
}
