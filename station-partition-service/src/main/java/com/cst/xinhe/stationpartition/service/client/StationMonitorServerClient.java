package com.cst.xinhe.stationpartition.service.client;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.stationpartition.service.client.callback.StationMonitorServerClientFallback;
import com.cst.xinhe.stationpartition.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 14:53
 **/
@FeignClient(value = "station-monitor-server",
    configuration = FeignConfig.class,
    fallback = StationMonitorServerClientFallback.class,
        url = "http://127.0.0.1:8765/")
//@RequestMapping("station-monitor-server")
public interface StationMonitorServerClient {

    @PostMapping("sendCmd")
    void sendCmd(@RequestBody ResponseData responseData);
}
