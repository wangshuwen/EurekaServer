package com.cst.xinhe.stationpartition.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.stationpartition.service.client.StationMonitorServerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 14:53
 **/
@Component
public class StationMonitorServerClientFallback implements StationMonitorServerClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sendCmd(ResponseData responseData) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }
}
