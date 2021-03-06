package com.cst.xinhe.kafka.sender.service.controller;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.kafka.sender.service.service.KafkaSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 15:53
 **/
@RestController
public class KafkaSenderController {

    @Autowired
    KafkaSenderService kafkaSenderService;

    @PostMapping("kafka-sender-service/sendData")
    public void sendData(@RequestParam("topic") String topic, @RequestBody RequestData requestData){
        kafkaSenderService.sendData(topic,requestData);
    }

    @PostMapping("kafka-sender-service/sendChatMsg")
    public void sendChatMsgData(@RequestParam String topic,@RequestBody String chatMsg){
        kafkaSenderService.sendChatMsgData(topic,chatMsg);
    }

    @PostMapping("kafka-sender-service/sendSelfCheckResult")
    public void sendSelfCheckResult(@RequestParam String s,@RequestBody String toJSONString, @RequestParam("terminalId") Integer terminalId){
        kafkaSenderService.sendByTerminalId(s,toJSONString,terminalId);
    }

    @PostMapping("kafka-sender-service/send")
    public void send(@RequestParam String topic, @RequestBody String obj,@RequestParam("terminalId") Integer terminalId){
        kafkaSenderService.sendByTerminalId(topic,obj,terminalId);
    }
}
