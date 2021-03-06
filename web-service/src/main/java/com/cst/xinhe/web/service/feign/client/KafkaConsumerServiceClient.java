package com.cst.xinhe.web.service.feign.client;

import com.cst.xinhe.web.service.feign.callback.KafkaConsumerServiceClientFallback;
import com.cst.xinhe.web.service.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/6/12/9:56
 */
@FeignClient(value = "kafka-consumer-service",
        configuration = FeignConfig.class,
        fallback = KafkaConsumerServiceClientFallback.class,
        url = "http://127.0.0.1:8773/")
public interface KafkaConsumerServiceClient {

   /* @GetMapping("overmanedAlarm")
   void overmanedAlarm(@RequestParam("type") Integer type, @RequestParam("staffId") Integer staffId, @RequestParam("areaId") Integer areaId);*/


    @GetMapping("updateAreaInfoObserver")
    void updateAreaInfoObserver();
}
