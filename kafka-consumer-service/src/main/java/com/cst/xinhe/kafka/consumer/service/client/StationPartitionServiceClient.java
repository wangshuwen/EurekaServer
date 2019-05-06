package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.StationPartitionServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 13:22
 **/
@FeignClient(value = "station-partition-service",
        configuration = FeignConfig.class,
        fallback = StationPartitionServiceClientFallback.class)
//@RequestMapping(value = "station-partition-service/")
public interface StationPartitionServiceClient {

    @GetMapping("findFrequencyByStationId")
    double findFrequencyByStationId(@RequestParam Integer stationId);

    @GetMapping("findBaseStationByNum")
    BaseStation findBaseStationByNum(@RequestParam Integer basestationid);

    @GetMapping("findBaseStationByType")
    Map<String, Object> findBaseStationByType(@RequestParam int i);

    @GetMapping("getSonIdsById")
    List<Integer> getSonIdsById(@RequestParam Integer zoneId);

    @PostMapping("findBaseStationByZoneIds")
    List<BaseStation> findBaseStationByZoneIds(@RequestBody List<Integer> zoneIds);

    @PutMapping("updateByStationNumSelective")
    void updateByStationNumSelective(@RequestBody BaseStation baseStation);

}
