package com.cst.xinhe.kafka.sender.service.service.impl;

import com.alibaba.fastjson.JSON;
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
        Integer partition = Math.abs(requestData.getTerminalId().hashCode()) % partitions;
        kafkaTemplate.send(topic,partition,partition+ "", JSON.toJSONString(requestData));
    }

    @Override
    public void sendByTerminalId(String topic, String obj, Integer terminalId) {
        Integer partition = Math.abs(terminalId.hashCode()) % partitions;
        kafkaTemplate.send(topic,partition,partition+ "",obj);
    }

    @Override
    public void sendByCount(String topic, String data, int count) {
        kafkaTemplate.send(topic,partitions,count+ "",data);
    }

    @Override
    public void sendChatMsgData(String topic, String chatMsg) {
        kafkaTemplate.send(topic,0,partitions+ "",chatMsg);
    }
}
