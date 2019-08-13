package com.cst.xinhe.web.service.feign.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.web.service.feign.client.StationMonitorServerClient;
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

    private static final Logger logger = LoggerFactory.getLogger(StationMonitorServerClientFallback.class);

    @Override
    public void sendCmd(ResponseData responseData) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
    }

    @Override
    public String sendControl() {
//        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL);
    }

}
