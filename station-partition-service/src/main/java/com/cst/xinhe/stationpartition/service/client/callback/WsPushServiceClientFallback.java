package com.cst.xinhe.stationpartition.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.stationpartition.service.client.WsPushServiceClient;
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
    public void sendInfo(String toJSONString) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }
}
