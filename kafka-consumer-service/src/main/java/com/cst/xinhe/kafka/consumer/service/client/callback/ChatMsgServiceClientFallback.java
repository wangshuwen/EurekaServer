package com.cst.xinhe.kafka.consumer.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.kafka.consumer.service.client.ChatMsgServiceClient;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 11:35
 **/
@Component
public class ChatMsgServiceClientFallback implements ChatMsgServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void insertChatMsgSelective(ChatMsg chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void updateChatMegStatusBySeqId(ChatMsg chatMsg) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }
}
