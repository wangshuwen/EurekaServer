package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.terminal.monitor.server.client.callback.WsPushServiceFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "ws-push-service",
        configuration = FeignConfig.class,
        fallback = WsPushServiceFallback.class,
        url = "http://192.168.1.100:8779/")
//@RequestMapping("ws/")
public interface WsPushServiceClient {

    @PostMapping("sendWSSiteServer")
    void sendWSSiteServer(String jsonString);
}
