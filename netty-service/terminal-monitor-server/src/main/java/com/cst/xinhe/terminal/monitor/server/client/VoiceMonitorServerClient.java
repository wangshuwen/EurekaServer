package com.cst.xinhe.terminal.monitor.server.client;


import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.terminal.monitor.server.client.callback.VoiceMonitorServerClientFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "voice-monitor-server",
configuration = FeignConfig.class,
fallback = VoiceMonitorServerClientFallback.class)
//@RequestMapping("voice-monitor-server")
public interface VoiceMonitorServerClient {

    @PostMapping("checkSendCheckOnline")
    void checkSendCheckOnline(@RequestBody RequestData customMsg);


    @PostMapping("sendCallInfo")
    void sendCallInfo(@RequestBody RequestData customMsg);

    @PostMapping("sendInfoToWs")
    void sendInfoToWs(@RequestBody String keyStr);
}
