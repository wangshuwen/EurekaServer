package com.cst.xinhe.stationpartition.service.client;

import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.stationpartition.service.client.callback.SystemServiceClientFallback;
import com.cst.xinhe.stationpartition.service.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "system-service",
        configuration = FeignConfig.class,
        fallback = SystemServiceClientFallback.class,
        url = "http://127.0.0.1:8778/")
public interface SystemServiceClient {

    @GetMapping("findRangByType")
    List<RangSetting> findRangByType(@RequestParam("i") int i);

    @GetMapping("getStandardNameByStandardId")
    Map<String, Object> getStandardNameByStandardId(@RequestParam("standardId") Integer standardId);
}
