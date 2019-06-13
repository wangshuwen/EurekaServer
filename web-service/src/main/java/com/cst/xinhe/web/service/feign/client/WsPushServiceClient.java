package com.cst.xinhe.web.service.feign.client;

import com.cst.xinhe.web.service.feign.callback.WsPushServiceClientFallback;
import com.cst.xinhe.web.service.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("setOrdId")
    void setOrgId(@RequestParam(name = "o") Integer o);

    @GetMapping("setZoneId")
    void setZoneId(@RequestParam(name = "o") Integer o);

    @GetMapping("setOrgIdIsNull")
    void setOrgIdIsNull();

    @GetMapping("setZoneIdIsNull")
    void setZoneIdIsNull();

}
