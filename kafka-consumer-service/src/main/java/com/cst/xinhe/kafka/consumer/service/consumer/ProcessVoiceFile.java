package com.cst.xinhe.kafka.consumer.service.consumer;

import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.kafka.consumer.service.client.TerminalMonitorClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @program: demo
 * @description: 发送声音 文件
 * @author: lifeng
 * @create: 2019-03-05 09:16
 **/
@Component
public class ProcessVoiceFile {

    private static final Logger logger = LoggerFactory.getLogger(ProcessVoiceFile.class);

    @Resource
    private TerminalMonitorClient terminalMonitorClient;

    private static final String TOPIC = "voiceSender.tut";

    private void process(String str_msg){
//        if (kafkaMessage.isPresent()) {
//            Object msg = kafkaMessage.get();
//            String str_msg = msg.toString();
            JSONObject jsonObject = JSONObject.parseObject(str_msg);

            String voiceUrl = jsonObject.getString("voiceUrl");
//            Integer staffId = jsonObject.getInteger("staffId");
//            Integer userId = jsonObject.getInteger("userId");
            Integer seqId = jsonObject.getInteger("seqId");
            Integer stationId = jsonObject.getInteger("stationId");
            Integer stationIp1 = jsonObject.getInteger("stationIp1");
            Integer stationIp2 = jsonObject.getInteger("stationIp2");
            Integer terminalId = jsonObject.getInteger("terminalId");
            Integer terminalPort = jsonObject.getInteger("terminalPort");
            Integer stationPort = jsonObject.getInteger("stationPort");
            Integer terminalIp1 = jsonObject.getInteger("terminalIp1");
            Integer terminalIp2 = jsonObject.getInteger("terminalIp2");
            String terminalIp = jsonObject.getString("terminalIp");
            String stationIp = jsonObject.getString("stationIp");

            ResponseData responseData = new ResponseData();

            RequestData requestData = new RequestData();
            responseData.setCode((byte) 0x55);
            requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
            requestData.setCmd((byte) (ConstantValue.MSG_HEADER_COMMAND_ID_SEND_VOICE));
            requestData.setLength(548);
            requestData.setStationId(stationId);
            requestData.setStationIp1(stationIp1);
            requestData.setStationIp2(stationIp2);
            requestData.setTerminalId(terminalId);
            requestData.setTerminalPort(terminalPort);
            requestData.setStationPort(stationPort);
            requestData.setTerminalIp1(terminalIp1);
            requestData.setTerminalIp2(terminalIp2);
            requestData.setSequenceId(seqId);

            requestData.setResult(ConstantValue.MSG_BODY_RESULT_SUCCESS);
            requestData.setNdName(ConstantValue.MSG_BODY_NODE_NAME_VOICE_DATA);
            requestData.setTerminalIp(terminalIp);
            requestData.setStationIp(stationIp);

            System.out.println(requestData.toString());
            //Speex
            File send = new File(voiceUrl);
            FileInputStream inputStream = null;
            int i = 1;
            int len;
            try {
                inputStream = new FileInputStream(send);
                byte[] bo = new byte[512];
                while (true) {
                    len = inputStream.read(bo);

                    System.out.println(len);
                    if (len < 512)
                        break;

                    requestData.setBody(bo);
                    requestData.setCount(i);
                    System.out.println("\n===========================");
                    for (int j = 0; j < bo.length; j++) {
                        System.out.printf("0x%02x ", bo[j]);
                    }
                    System.out.println("\n---------------------------");
                    System.out.println("\n发送的第 " + i + " 个语音包");
                    System.out.println("\n包总长度为： " + requestData.getLength());
                    System.out.println("\n发送的语音数据： " + requestData.toString());
                    System.out.println("\n===========================");
                    responseData.setCustomMsg(requestData);
//                    SingletonClient.getSingletonClient().sendCmd(responseData);
                    terminalMonitorClient.sendResponseData(responseData);
                    try {
                        Thread.sleep((long) 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                requestData.setLength(40);
                requestData.setCount(i);
                byte[] b = new byte[4];
                b[0] = (byte) 0x55;
                b[1] = (byte) 0x66;
                b[2] = (byte) 0x77;
                b[3] = (byte) 0x88;
                requestData.setBody(b);
                responseData.setCustomMsg(requestData);

//                SingletonClient.getSingletonClient().sendCmd(responseData);
                terminalMonitorClient.sendResponseData(responseData);
//            long end = System.currentTimeMillis();
//            System.out.println("时间差：" + (int)(end - start));
                System.out.println("\n发送的第 " + i + " 个语音包");
                System.out.println("\n包总长度为： " + requestData.getLength());
                System.out.println("\n发送的语音数据： " + requestData.toString());
                System.out.println("\n===========================");
                i = 1;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
//        else {
//            //暂存队列数据
//
//        }
//    }
  //  @KafkaListener(id = "voiceProcess", topics = "voiceSender.tut")
  @KafkaListener(id = "ProcessVoiceFileid0", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "0" }) })
    public void sendVoiceToTerminal0(List<ConsumerRecord<?, ?>> records) {

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

    @KafkaListener(id = "ProcessVoiceFileid1", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "1" }) })
    public void sendVoiceToTerminal1(List<ConsumerRecord<?, ?>> records) {

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

    @KafkaListener(id = "ProcessVoiceFileid2", topicPartitions = { @TopicPartition(topic = TOPIC, partitions = { "2" }) })
    public void sendVoiceToTerminal2(List<ConsumerRecord<?, ?>> records) {

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

}
