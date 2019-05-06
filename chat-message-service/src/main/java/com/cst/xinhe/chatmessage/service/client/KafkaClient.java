package com.cst.xinhe.chatmessage.service.client;

import com.cst.xinhe.chatmessage.service.client.callback.KafkaClientFallback;
import com.cst.xinhe.chatmessage.service.client.config.FeignConfig;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
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
@FeignClient(value = "kafka-sender-service/",
        configuration = FeignConfig.class,
        fallback = KafkaClientFallback.class)
public interface KafkaClient {
    @PostMapping("kafka-sender-service/sendData")
    void sendData(@RequestParam String topic, @RequestBody RequestData requestData);

    @PostMapping("kafka-sender-service/sendChatMsg")
    void sendChatMsgData(@RequestParam String topic,@RequestBody String chatMsg);

    @PostMapping("kafka-sender-service/sendSelfCheckResult")
    void sendSelfCheckResult(@RequestParam String s, @RequestBody String toJSONString,@RequestParam  Integer terminalPort);

    @PostMapping("kafka-sender-service/send")
    void send(@RequestParam String topic, @RequestBody String obj,@RequestParam Integer port);
}
