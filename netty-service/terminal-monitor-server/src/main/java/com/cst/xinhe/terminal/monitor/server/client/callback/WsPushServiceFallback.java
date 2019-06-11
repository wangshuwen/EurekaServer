package com.cst.xinhe.terminal.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.terminal.monitor.server.client.WsPushServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 15:15
 **/
@Component
public class WsPushServiceFallback implements WsPushServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(WsPushServiceFallback.class);

    @Override
    public void sendWSSiteServer(String jsonString) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void setOrgId(Integer o) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

    @Override
    public void setZoneId(Integer o) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }

    @Override
    public void setOrgIdIsNull() {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public void setZoneIdIsNull() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }


    @Override
    public String sendWebsocketServer(String jsonObject) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
