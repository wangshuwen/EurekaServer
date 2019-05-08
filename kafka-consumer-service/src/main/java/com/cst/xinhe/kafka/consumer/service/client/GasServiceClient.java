package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.GasServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 10:46
 **/
@FeignClient(value = "gas-service",
        configuration = FeignConfig.class,
        fallback = GasServiceClientFallback.class)
public interface GasServiceClient {

    @GetMapping("findGasInfoByStaffIdAndTerminalId")
    GasWSRespVO findGasInfoByStaffIdAndTerminalId(@RequestParam("terminalId") Integer terminalId);
}
