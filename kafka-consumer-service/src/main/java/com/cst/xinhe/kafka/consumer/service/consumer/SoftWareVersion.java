package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.persistence.dao.terminal.StaffTerminalMapper;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/9/3/9:17
 */
@Component
public class SoftWareVersion {
    private static final Logger logger = LoggerFactory.getLogger(PowerStatusRemind.class);
    @Resource
    private StaffTerminalMapper staffTerminalMapper;



    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(9);
//    @Resource
//    private StaffTerminalRelationService staffTerminalRelationService;


    // @KafkaListener(id = "processPowerStatus", topics = "powerStatus.tut")
    private static final String TOPIC = "softWareVersion.tut";

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
                staffTerminal.setSoftwareVersion(first+"."+second);
                staffTerminal.setTerminalId(terminalId);

                staffTerminalMapper.updateByPrimaryKeySelective(staffTerminal);
//

            }
        }

    }
    @KafkaListener(groupId = "SoftWareVersion", id = "SoftWareVersionid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendSiteInfo0(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "SoftWareVersion", id = "SoftWareVersionid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendSiteInfo1(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }
    @KafkaListener(groupId = "SoftWareVersion", id = "SoftWareVersionid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendSiteInfo2(List<ConsumerRecord<?, ?>> records) {

        fixedThreadPool.execute(() -> processT(records));
    }

}
