package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.terminal.monitor.server.client.callback.WsPushServiceFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ws-push-service",
        configuration = FeignConfig.class,
        fallback = WsPushServiceFallback.class,
        url = "http://127.0.0.1:8779/")
//@RequestMapping("ws/")
public interface WsPushServiceClient {

    @PostMapping("sendWSSiteServer")
    void sendWSSiteServer(@RequestBody String jsonString);
}
