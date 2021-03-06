package com.cst.xinhe.kafka.consumer.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.kafka.consumer.service.client.WsPushServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 18:10
 **/
@Component
public class WsPushServiceClientFallback implements WsPushServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String sendWebsocketServer(String jsonObject) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public String sendWSPersonNumberServer(String jsonObject) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public String sendWSServer(String jsonObject) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public String sendWSSiteServer(String jsonObject) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public String sendInfo(String jsonObject) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Integer getWSSiteServerOrgId() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Integer getWSSiteServerZoneId() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Integer getWSServerOrgId() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Integer getWSServerZoneId() {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
