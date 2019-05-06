package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.terminal.monitor.server.client.callback.WsPushServiceFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "ws-push-service",configuration = FeignConfig.class,fallback = WsPushServiceFallback.class)
//@RequestMapping("ws/")
public interface WsPushServiceClient {

    @PostMapping("sendWSSiteServer")
    void sendWSSiteServer(String jsonString);
}
