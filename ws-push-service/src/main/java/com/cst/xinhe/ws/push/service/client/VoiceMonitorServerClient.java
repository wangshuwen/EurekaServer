package com.cst.xinhe.ws.push.service.client;

import com.cst.xinhe.ws.push.service.client.callback.VoiceMonitorServerClientFallBak;
import com.cst.xinhe.ws.push.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 09:37
 **/
@FeignClient(value = "voice-monitor-server",
        configuration = FeignConfig.class,
        fallback = VoiceMonitorServerClientFallBak.class)
public interface VoiceMonitorServerClient {

    @RequestMapping()
    String send();
}
