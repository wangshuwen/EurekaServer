package com.cst.xinhe.terminal.monitor.server.sendInfo;

import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.constant.ConstantValue;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.terminal.monitor.server.request.SingletonClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @program: demo
 * @description: 发送声音 文件
 * @author: lifeng
 * @create: 2019-03-05 09:16
 **/
@Component
public class ProcessVoiceFile {

    private static final Logger logger = LoggerFactory.getLogger(ProcessVoiceFile.class);


    private static final String TOPIC = "voiceSender.tut";
    private  Integer byteLength=0;

    private void process(String str_msg){
            JSONObject jsonObject = JSONObject.parseObject(str_msg);

            String voiceUrl = jsonObject.getString("voiceUrl");
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
            responseData.setCode(ConstantValue.MSG_BODY_RESULT_SUCCESS);
            requestData.setType(ConstantValue.MSG_HEADER_FREAME_HEAD);
            requestData.setCmd(ConstantValue.MSG_HEADER_COMMAND_ID_NULL);
           // requestData.setLength(546);
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

                    System.out.print("\n发送的第 " + i + " 个语音包");
                    System.out.println("语音包body的长度："+len);
                    requestData.setBody(bo);
                    requestData.setCount(i);
                    System.out.println("\n===========================");

                    for (int j = 0; j < len; j++) {
                        System.out.printf("0x%02x ", bo[j]);
                    }
                    byteLength+=len;
                    System.out.println();
                    System.out.println("语音数据的总长度："+byteLength);


                        requestData.setLength(34+len);


                    System.out.println("\n---------------------------");
                    System.out.println("\n发送的第 " + i + " 个语音包");
                    System.out.println("\n包总长度为： " + requestData.getLength());
                    System.out.println("\n发送的语音数据： " + requestData.toString());
                    System.out.println("\n===========================");
                    responseData.setCustomMsg(requestData);
                    SingletonClient.getSingletonClient().sendCmd(responseData);
                    //如果长度小于512表示最后一个语音数据包，结束循环
                    if (len < 512)
                        break;

                    try {
                        Thread.sleep((long) 15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                try {
                    Thread.sleep((long) 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestData.setLength(38);
                requestData.setCount(i);
                byte[] b = new byte[4];
                b[0] = (byte) 0x55;
                b[1] = (byte) 0x66;
                b[2] = (byte) 0x77;
                b[3] = (byte) 0x88;
                requestData.setBody(b);
                responseData.setCustomMsg(requestData);

                SingletonClient.getSingletonClient().sendCmd(responseData);
//                terminalMonitorClient.sendResponseData(responseData);
//            long end = System.currentTimeMillis();
//            System.out.println("时间差：" + (int)(end - start));
                System.out.println("\n发送的第 " + i + " 个语音包");
                System.out.println("\n包总长度为： " + requestData.getLength());
                System.out.println("\n发送的语音数据： " + requestData.toString());
                System.out.println("\n===========================");
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
