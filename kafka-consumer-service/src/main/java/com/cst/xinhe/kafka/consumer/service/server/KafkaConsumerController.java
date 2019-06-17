package com.cst.xinhe.kafka.consumer.service.server;

import com.alibaba.fastjson.JSON;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import com.cst.xinhe.kafka.consumer.service.consumer.GasKafka;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @Autowired
    WsPushServiceClient wsPushServiceClient;

    @Autowired
    WarningAreaMapper warningAreaMapper;

    @PutMapping("overmanedAlarm")
    public void overmanedAlarm(@RequestParam("type") Integer type,@RequestParam("staffId") Integer staffId,@RequestParam("areaId") Integer areaId){
        WarningArea warningArea = warningAreaMapper.selectByPrimaryKey(areaId);
        if(type==1){
            GasKafka.importantArea.remove(staffId);
            if(GasKafka.importantArea.size()>warningArea.getContainNumber()){
                WebSocketData data = WebSocketData.getInstance();
                HashMap<String, Object> map = new HashMap<>();
                map.put("areaInfo",warningArea);
                map.put("personNum",GasKafka.importantArea.size());
                data.setType(9);
                data.setData(data);
                wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
            }
        }else{
            GasKafka.limitArea.remove(staffId);
            if(GasKafka.limitArea.size()>warningArea.getContainNumber()){
                WebSocketData data = WebSocketData.getInstance();
                HashMap<String, Object> map = new HashMap<>();
                map.put("areaInfo",warningArea);
                map.put("personNum",GasKafka.limitArea.size());
                data.setType(10);
                data.setData(data);
                wsPushServiceClient.sendWSPersonNumberServer(JSON.toJSONString(data));
            }
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

    @GetMapping("updateAreaInfoObserver")
    public void updateAreaInfo(){
        kafkaConsumerService.updateWarningAreaInfo();
    }
}
