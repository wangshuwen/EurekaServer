package com.cst.xinhe.kafka.consumer.service.server;

import com.cst.xinhe.kafka.consumer.service.consumer.GasKafka;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 15:53
 **/
@RestController
//@RequestMapping("kafka-consumer-service/")
public class KafkaConsumerController {

    @Autowired
    KafkaConsumerService kafkaConsumerService;

    @PutMapping("overmanedAlarm")
    public void overmanedAlarm(@RequestParam("type") Integer type,@RequestParam("staffId") Integer staffId){
        if(type==1){
            GasKafka.importantArea.remove(staffId);
        }else{
            GasKafka.limitArea.remove(staffId);
        }
    }

    @PutMapping("removeCarSet")
    public void removeCarSet(@RequestParam Integer staffId){
        kafkaConsumerService.removeCarSet(staffId);
    }

    @PutMapping("removeOutPersonSet")
    public void removeOutPersonSet(@RequestParam Integer staffId){
        kafkaConsumerService.removeOutPersonSet(staffId);
    }

    @PutMapping("removeLeaderSet")
    public void removeLeaderSet(@RequestParam Integer staffId){
        kafkaConsumerService.removeLeaderSet(staffId);
    }

    @PutMapping("removeStaffSet")
    public void removeStaffSet(@RequestParam Integer staffId){
        kafkaConsumerService.removeStaffSet(staffId);
    }
//    @GetMapping("pushRtPersonData")
//    public void pushRtPersonData(){
//        kafkaConsumerService.pushRtPersonData();
//    }
}
