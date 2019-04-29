package com.cst.xinhe.voice.monitor.server.client.callback;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.voice.monitor.server.client.KafkaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-26 15:13
 **/
@Component
public class KafkaClientFallback implements KafkaClient {
    private static Logger logger = LoggerFactory.getLogger(KafkaClientFallback.class);
    @Override
    public void sendData(String topic, RequestData requestData) {
        logger.error("call remote kafka-sender-service service error");
    }
}
