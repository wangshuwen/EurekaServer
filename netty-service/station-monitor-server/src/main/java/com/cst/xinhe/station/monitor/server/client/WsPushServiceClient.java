package com.cst.xinhe.station.monitor.server.client;

import com.cst.xinhe.station.monitor.server.client.callback.WsPushServiceFallback;
import com.cst.xinhe.station.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ws-push-service",
        configuration = FeignConfig.class,
        fallback = WsPushServiceFallback.class,
        url = "http://192.168.1.100:8779/")
//@RequestMapping("ws/")
//@RequestMapping( produces = {"application/json;charset=UTF-8"})
public interface WsPushServiceClient {


    @PostMapping("sendWebsocketServer")
    String sendWebsocketServer(@RequestBody String jsonObject);

    @PostMapping("sendWSSiteServer")
    void sendWSSiteServer(String jsonString);

    @GetMapping("setOrdId")
    void setOrgId(@RequestParam(name = "o") Integer o);

    @GetMapping("setZoneId")
    void setZoneId(@RequestParam(name = "o") Integer o);

    @GetMapping("setOrgIdIsNull")
    void setOrgIdIsNull();

    @GetMapping("setZoneIdIsNull")
    void setZoneIdIsNull();
}
