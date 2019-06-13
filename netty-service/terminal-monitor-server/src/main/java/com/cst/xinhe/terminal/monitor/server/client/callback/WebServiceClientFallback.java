package com.cst.xinhe.terminal.monitor.server.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.terminal.monitor.server.client.WebServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 16:28
 **/
@Component
public class WebServiceClientFallback implements WebServiceClient {
    @Override
    public List<RangSetting> findRangByType(int i) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public GasLevelVO getWarnLevelSettingByGasLevelId(Integer standardId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public String findRangUrlByLevelDataId(int contrastParameter) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public double findFrequencyByStationId(Integer stationId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return 0;
    }

    @Override
    public Map<String, Object> findStaffIdByTerminalId(int terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public TerminalUpdateIp findTerminalIdByIpAndPort(String terminalIp, int port) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> getDeptAndGroupNameByStaffId(Integer staffId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
