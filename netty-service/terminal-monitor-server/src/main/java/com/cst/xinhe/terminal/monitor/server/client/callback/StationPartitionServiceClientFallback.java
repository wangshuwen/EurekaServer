package com.cst.xinhe.terminal.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.terminal.monitor.server.client.StationPartitionServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 13:22
 **/
@Component
public class StationPartitionServiceClientFallback implements StationPartitionServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public double findFrequencyByStationId(Integer stationId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return 0;
    }
}
