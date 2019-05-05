package com.cst.xinhe.voice.monitor.server.client;

import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.voice.monitor.server.client.callback.GasServiceClientFallback;
import com.cst.xinhe.voice.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "gas-service",
        configuration = FeignConfig.class,
        fallback = GasServiceClientFallback.class)
public interface GasServiceClient {

    @GetMapping("selectGasInfoByTerminalLastTime")
    Map<String, Object> selectGasInfoByTerminalLastTime(@RequestParam Integer terminalId);

    @GetMapping("selectRoadById")
    TerminalRoad selectRoadById(Integer positionId);

}