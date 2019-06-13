package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.terminal.monitor.server.client.callback.StationPartitionServiceClientFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 13:22
 **/
@FeignClient(value = "web-service",
        configuration = FeignConfig.class,
        fallback = StationPartitionServiceClientFallback.class,
        url = "http://127.0.0.1:8781/")
//@RequestMapping("station-partition-service")
public interface StationPartitionServiceClient {

    @GetMapping("findFrequencyByStationId")
    double findFrequencyByStationId(@RequestParam("stationId") Integer stationId);
}
