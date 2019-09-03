package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.WebServiceClient;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.persistence.dao.lack_electric.LackElectricMapper;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/9/3/9:18
 */
@Component
public class HandWareVersion {
    private static final Logger logger = LoggerFactory.getLogger(PowerStatusRemind.class);
    @Resource
    private StaffTerminalMapper staffTerminalMapper;



    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(9);
//    @Resource
//    private StaffTerminalRelationService staffTerminalRelationService;


    // @KafkaListener(id = "processPowerStatus", topics = "powerStatus.tut")
    private static final String TOPIC = "handWareVersion.tut";

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


                int first = jsonObject.getInteger("first");
                int second = jsonObject.getInteger("second");
                int terminalId = jsonObject.getInteger("terminalId");

                //终端自检，电量大于30，移除缺电提醒
                StaffTerminal staffTerminal = new StaffTerminal();
                staffTerminal.setHardwareVersion(first+"."+second);
                staffTerminal.setTerminalId(terminalId);

                staffTerminalMapper.updateByPrimaryKeySelective(staffTerminal);
//

            }
        }

    }
    @KafkaListener(groupId = "HandWareVersion", id = "HandWareVersionid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendSiteInfo0(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "HandWareVersion", id = "HandWareVersionid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendSiteInfo1(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "HandWareVersion", id = "HandWareVersionid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendSiteInfo2(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }

}
