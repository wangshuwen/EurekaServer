package com.cst.xinhe.voice.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.voice.monitor.server.client.GasServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:24
 **/
@Component
public class GasServiceClientFallback implements GasServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public TerminalRoad selectRoadById(Integer positionId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
