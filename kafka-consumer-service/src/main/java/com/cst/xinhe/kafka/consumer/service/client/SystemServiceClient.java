package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "station-partition-service",
        configuration = FeignConfig.class,
        fallback = SystemServiceClient.class)
public interface SystemServiceClient {

    @GetMapping("findRangByType")
    List<RangSetting> findRangByType(@RequestParam int i);
}
