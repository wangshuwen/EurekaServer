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
@RequestMapping("v1/")
public class KafkaSenderController {

    @Autowired
    KafkaSenderService kafkaSenderService;

    @PostMapping("kafka/sendData")
    public void sender(@RequestParam String topic, @RequestBody RequestData requestData){
        kafkaSenderService.sendData(topic,requestData);
    }
}
