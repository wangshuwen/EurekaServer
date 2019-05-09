package com.cst.xinhe.stationpartition.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.stationpartition.service.client.SystemServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 11:46
 **/
@Component
public class SystemServiceClientFallback implements SystemServiceClient {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public List<RangSetting> findRangByType(int i) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> getStandardNameByStandardId(Integer standardId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));


        return null;
    }

    @Override
    public Map<Integer, String> getStandardNameByStandardIds(Map<Integer, Integer> standardIdList) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }
}
