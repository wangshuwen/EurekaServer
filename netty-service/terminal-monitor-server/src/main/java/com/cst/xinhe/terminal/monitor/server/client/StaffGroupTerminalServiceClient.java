package com.cst.xinhe.terminal.monitor.server.client;

import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.terminal.monitor.server.client.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.terminal.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "staff-group-terminal-service",
        configuration = FeignConfig.class,
        fallback = StaffGroupTerminalServiceClientFallback.class,
        url = "http://127.0.0.1:8772/")
//@RequestMapping("staff-group-terminal-service")
public interface StaffGroupTerminalServiceClient {

    @GetMapping("findStaffIdByTerminalId")
    Map<String, Object> findStaffIdByTerminalId(@RequestParam("terminalId") int terminalId);


    @GetMapping("findTerminalIdByIpAndPort")
    TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam("terminalId") String terminalIp, @RequestParam("port") int port);

    @GetMapping("selectStaffInfoByTerminalId")
    Map<String, Object> selectStaffInfoByTerminalId(@RequestParam("terminalId") Integer terminalId);
}
