package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.ChatMsgServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.persistence.dao.chat.ChatMsgMapper;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UpdateVoiceReadStatus {

    private static final Logger logger = LoggerFactory.getLogger(UpdateVoiceReadStatus.class);
//    @Resource
//    private ChatMsgMapper chatMsgMapper;

    @Resource
    private ChatMsgServiceClient chatMsgServiceClient;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    private static final String TOPIC = "updateVoiceReadStatus.tut";

    ExecutorService executorService = Executors.newCachedThreadPool();

    private void process(List<ConsumerRecord<?, ?>> records){

        executorService.execute(() -> {
            for (ConsumerRecord<?, ?> record : records) {
                Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                logger.info("Received: " + record);
                if (kafkaMessage.isPresent()) {
                    Object message = kafkaMessage.get();
                    String str = (String) message;
                    JSONObject jsonObject = JSON.parseObject(str);
                    ChatMsg chatMsg = new ChatMsg();

                    Integer terminalId = jsonObject.getInteger("terminalId");
                    Date time = jsonObject.getDate("rt");

                    Integer seq = jsonObject.getInteger("sequenceId");

                    StringBuffer seq_str = new StringBuffer();
                    seq_str.append(time).append(terminalId).append(seq);
                    chatMsg.setSequenceId(seq_str.toString());

//                    chatMsgMapper.updateChatMegStatusBySeqId(chatMsg);
                    chatMsgServiceClient.updateChatMegStatusBySeqId(chatMsg);
                    // TODO 通过WebSocket，发送到前端，该条语音已经被读取   {seqId}
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("code",2);//code=2表示是已读未读
                    map.put("result", seq_str); //表示流水号唯一的ID
                    try {
                        wsPushServiceClient.sendWebsocketServer(JSON.toJSONString(new WebSocketData(2, map)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
  //  @KafkaListener(id = "updateVoiceReadStatus", topics = "updateVoiceReadStatus.tut")
    @KafkaListener(id = "UpdateVoiceReadStatusid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void listen0(List<ConsumerRecord<?, ?>> records) {
        process(records);

    }
    @KafkaListener(id = "UpdateVoiceReadStatusid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void listen1(List<ConsumerRecord<?, ?>> records) {
       process(records);
    }
    @KafkaListener(id = "UpdateVoiceReadStatusid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void listen2(List<ConsumerRecord<?, ?>> records) {
       process(records);
    }
}
