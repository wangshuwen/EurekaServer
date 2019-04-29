package com.cst.xinhe.terminal.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.terminal.monitor.server.client.KafkaClient;
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
    public void sendChatMsgData(String topic, ChatMsg chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.SEND_TO_SERVER_CHAT_MSG_FAIL));
    }
}
