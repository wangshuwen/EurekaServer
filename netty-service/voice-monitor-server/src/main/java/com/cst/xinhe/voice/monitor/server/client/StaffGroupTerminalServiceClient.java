package com.cst.xinhe.voice.monitor.server.client;

import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.voice.monitor.server.client.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.voice.monitor.server.client.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "staff-group-terminal-service",
        configuration = FeignConfig.class,
        fallback = StaffGroupTerminalServiceClientFallback.class)
@RequestMapping("staff-group-terminal-service")
public interface StaffGroupTerminalServiceClient {

    @GetMapping("findStaffIdByTerminalId")
    Map<String, Object> findStaffIdByTerminalId(@RequestParam int terminalId);


    @GetMapping("findTerminalIdByIpAndPort")
    TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam String terminalIp, @RequestParam int port);

    @GetMapping("selectStaffInfoByTerminalId")
    Map<String, Object> selectStaffInfoByTerminalId(@RequestParam Integer terminalId);

    @GetMapping("getDeptAndGroupNameByStaffId")
    HashMap<String, Object> getDeptAndGroupNameByStaffId(@RequestParam Integer staffId);

    @GetMapping("findStaffById")
    Staff findStaffById(@RequestParam Integer staffid);

    @GetMapping("getDeptNameByGroupId")
    String getDeptNameByGroupId(@RequestParam Integer group_id);

    @GetMapping("selectStationIpByStationId")
    Map<String, Object> selectStationIpByStationId(@RequestParam Integer stationId);

    @GetMapping("findTerminalInfoByStaffId")
    Integer findTerminalInfoByStaffId(int parseInt);

    @GetMapping("selectTerminalIpInfoByTerminalId")
    Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId);
}
