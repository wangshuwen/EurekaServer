package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.terminal.monitor.server.client.callback.KafkaConsumerClientFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "kafka-consumer-service",
configuration = FeignConfig.class,
fallback = KafkaConsumerClientFallback.class,
url = "http://192.168.1.50:8773/")
//@RequestMapping("kafka-consumer-service/")
public interface KafkaConsumerClient {

    @PutMapping("removeCarSet")
    void removeCarSet(@RequestParam Integer staffId);

    @PutMapping("removeOutPersonSet")
    void removeOutPersonSet(@RequestParam Integer staffId);

    @PutMapping("removeLeaderSet")
    void removeLeaderSet(@RequestParam Integer staffId);

    @PutMapping("removeStaffSet")
    void removeStaffSet(@RequestParam Integer staffId);

    @GetMapping("pushRtPersonData")
    void pushRtPersonData();
}
