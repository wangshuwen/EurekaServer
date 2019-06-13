package com.cst.xinhe.web.service.attendance.client;

import com.cst.xinhe.attendance.service.client.callback.StationPartitionServiceClientFallback;
import com.cst.xinhe.attendance.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 13:22
 **/
@FeignClient(value = "station-partition-service",
        configuration = FeignConfig.class,
        fallback = StationPartitionServiceClientFallback.class,
        url = "http://127.0.0.1:8776/")
//@RequestMapping(value = "station-partition-service/")
public interface StationPartitionServiceClient {

    @GetMapping("findFrequencyByStationId")
    double findFrequencyByStationId(@RequestParam("stationId") Integer stationId);

    @GetMapping("findBaseStationByNum")
    BaseStation findBaseStationByNum(@RequestParam("basestationid") Integer basestationid);
}
