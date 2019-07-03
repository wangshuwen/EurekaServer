package com.cst.xinhe.kafka.sender.service.service;

import com.cst.xinhe.common.netty.data.request.RequestData;

public interface KafkaSenderService {

    void sendData(String topic, RequestData requestData);

    void sendByTerminalId(String topic, String obj,Integer port);

    void sendByCount(String topic,String data,int count );

    void sendChatMsgData(String topic, String chatMsg);
}
