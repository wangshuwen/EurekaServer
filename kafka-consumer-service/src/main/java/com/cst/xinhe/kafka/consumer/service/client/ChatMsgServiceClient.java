package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.ChatMsgServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "chat-message-service",
        configuration = FeignConfig.class,
        fallback = ChatMsgServiceClientFallback.class,
        url = "http://192.168.1.106:8777/")
public interface ChatMsgServiceClient {

    @PostMapping("insertChatMsgSelective")
    void insertChatMsgSelective(@RequestBody ChatMsg chatMsg);

    @PutMapping("updateChatMegStatusBySeqId")
    void updateChatMegStatusBySeqId(@RequestBody ChatMsg chatMsg);
}
