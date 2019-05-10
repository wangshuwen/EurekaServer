package com.cst.xinhe.station.monitor.server.client;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.station.monitor.server.client.callback.KafkaClientFallback;
import com.cst.xinhe.station.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 15:13
 **/
@FeignClient(value = "kafka-sender-service",
        configuration = FeignConfig.class,
        fallback = KafkaClientFallback.class,
url = "http://127.0.0.1:8768/")
public interface KafkaClient {
    @PostMapping("kafka-sender-service/sendData")
    void sendData(@RequestParam("topic") String topic, @RequestBody RequestData requestData);

    @PostMapping("kafka-sender-service/sendChatMsg")
    void sendChatMsgData(@RequestParam("topic") String topic,@RequestBody String chatMsg);

    @PostMapping("kafka-sender-service/sendSelfCheckResult")
    void sendSelfCheckResult(@RequestParam("s") String s, @RequestBody String toJSONString,@RequestParam("terminalPort")  Integer terminalPort);

    @PostMapping("kafka-sender-service/send")
    void send(@RequestParam("topic") String topic, @RequestBody String obj,@RequestParam("port") Integer port);
}
