package com.cst.xinhe.staffgroupterminal.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.staffgroupterminal.service.client.TerminalMonitorClient;
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
    public Integer getBatteryByTerminalNum(Integer terminalNum) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
