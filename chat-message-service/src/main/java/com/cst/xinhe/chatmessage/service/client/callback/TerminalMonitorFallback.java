package com.cst.xinhe.chatmessage.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.chatmessage.service.client.TerminalMonitorClient;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 14:55
 **/
@Component
public class TerminalMonitorFallback implements TerminalMonitorClient {

    private static Logger logger = LoggerFactory.getLogger(TerminalMonitorFallback.class);
    @Override
    public String sendResponseData(ResponseData responseData) {

        return ResultUtil.jsonToStringError(ResultEnum.TerminalMonitorFallback);
    }

    @Override
    public Boolean getChanelByName(String ipPort) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.GET_CHANNEL_FAIL));
        return false;
    }

    @Override
    public Integer getSequenceId() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
