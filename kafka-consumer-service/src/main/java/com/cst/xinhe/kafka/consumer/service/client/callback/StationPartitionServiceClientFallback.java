package com.cst.xinhe.kafka.consumer.service.client.callback;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.kafka.consumer.service.client.StationPartitionServiceClient;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Override
    public BaseStation findBaseStationByNum(Integer basestationid) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public Map<String, Object> findBaseStationByType(int i) {

        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public List<Integer> getSonIdsById(Integer zoneId) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

        return null;
    }

    @Override
    public List<BaseStation> findBaseStationByZoneIds(List<Integer> zoneIds) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));
        return null;
    }

    @Override
    public void updateByStationNumSelective(BaseStation baseStation) {
        logger.error(ResultUtil.jsonToStringError(ResultEnum.CALL_REMOTE_SERVER_FAIL));

    }
}
