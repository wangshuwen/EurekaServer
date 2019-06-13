package com.cst.xinhe.web.service.feign.client;

import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.web.service.feign.callback.SystemServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("getStandardNameByStandardIds")
    Map<Integer, String> getStandardNameByStandardIds(@RequestBody Map<Integer, Integer> standardIdList);
}
