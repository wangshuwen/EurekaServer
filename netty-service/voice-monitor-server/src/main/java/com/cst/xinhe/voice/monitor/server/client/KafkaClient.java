package com.cst.xinhe.voice.monitor.server.client;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.voice.monitor.server.client.callback.KafkaClientFallback;
import com.cst.xinhe.voice.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
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
        fallback = KafkaClientFallback.class)
public interface KafkaClient {
    @PostMapping("v1/kafka/sendData")
    void sendData(@RequestParam String topic, @RequestBody RequestData requestData);
}
