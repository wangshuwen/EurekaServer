package com.cst.xinhe.web.service.feign.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.web.service.feign.client.KafkaClient;
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
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void sendChatMsgData(String topic, String chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void sendSelfCheckResult(String s, String toJSONString, Integer terminalPort) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void send(String topic, String obj, Integer port) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }
}
