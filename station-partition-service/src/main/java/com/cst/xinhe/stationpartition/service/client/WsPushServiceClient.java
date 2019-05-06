package com.cst.xinhe.stationpartition.service.client;

import com.cst.xinhe.stationpartition.service.client.callback.WsPushServiceClientFallback;
import com.cst.xinhe.stationpartition.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 18:09
 **/
@FeignClient(value = "ws-push-service",
    configuration = FeignConfig.class,
    fallback = WsPushServiceClientFallback.class)
//@RequestMapping("ws-push-service/")
public interface WsPushServiceClient {

    @PostMapping("sendInfo")
    void sendInfo(String toJSONString);
}
