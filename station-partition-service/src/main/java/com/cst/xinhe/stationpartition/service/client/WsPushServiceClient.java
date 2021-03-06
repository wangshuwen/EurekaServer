package com.cst.xinhe.stationpartition.service.client;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.stationpartition.service.client.callback.WsPushServiceClientFallback;
import com.cst.xinhe.stationpartition.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 18:09
 **/
@FeignClient(value = "ws-push-service",
    configuration = FeignConfig.class,
    fallback = WsPushServiceClientFallback.class,
        url = "http://127.0.0.1:8779/")
//@RequestMapping("ws-push-service/")
public interface WsPushServiceClient {


    @PostMapping("sendWebsocketServer")
    String sendWebsocketServer(@RequestBody String jsonObject);

    @PostMapping("sendWSPersonNumberServer")
    String sendWSPersonNumberServer(@RequestBody String jsonObject);

    @PostMapping("sendWSServer")
    String sendWSServer(@RequestBody String jsonObject);

    @PostMapping("sendWSSiteServer")
    String sendWSSiteServer(@RequestBody String jsonObject);

    @PostMapping("sendInfo")
    String sendInfo(@RequestBody String jsonObject);

}
