package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.SystemServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "system-service",
        configuration = FeignConfig.class,
        fallback = SystemServiceClientFallback.class,
        url = "https://127.0.0.1:8778/")
public interface SystemServiceClient {

    @GetMapping("findRangByType")
    List<RangSetting> findRangByType(@RequestParam("i") int i);

    @GetMapping("getWarnLevelSettingByGasLevelId")
    GasLevelVO getWarnLevelSettingByGasLevelId(@RequestParam("standardId") Integer standardId);

    @GetMapping("findRangUrlByLevelDataId")
    String findRangUrlByLevelDataId(@RequestParam("contrastParameter") int contrastParameter);
}
