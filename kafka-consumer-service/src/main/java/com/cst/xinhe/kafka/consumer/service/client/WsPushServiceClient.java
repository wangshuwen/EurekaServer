package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.WsPushServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 18:09
 **/
@FeignClient(value = "ws-push-service",
    configuration = FeignConfig.class,
    fallback = WsPushServiceClientFallback.class,
        url = "http://192.168.1.100:8779/")
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


    @GetMapping("getWSSiteServerOrgId")
    Integer getWSSiteServerOrgId();

    @GetMapping("getWSSiteServerZoneId")
    Integer getWSSiteServerZoneId();

    @GetMapping("getWSServerOrgId")
    Integer getWSServerOrgId();

    @GetMapping("getWSServerZoneId")
    Integer getWSServerZoneId();
}
