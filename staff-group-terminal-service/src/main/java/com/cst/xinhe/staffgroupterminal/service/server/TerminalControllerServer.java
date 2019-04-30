package com.cst.xinhe.staffgroupterminal.service.server;

import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import com.cst.xinhe.staffgroupterminal.service.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 10:46
 **/
@RestController
@RequestMapping("staff-group-terminal-service")
public class TerminalControllerServer {
    @Autowired
    private StaffService staffService;

    @Autowired
    private TerminalService terminalService;


    @GetMapping("findTerminalIdByIpAndPort")
    public TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam String terminalIp, @RequestParam Integer port){
        TerminalUpdateIp terminalUpdateIp = terminalService.findTerminalIdByIpAndPort(terminalIp, port);
        return terminalUpdateIp;
    }

    @GetMapping("selectStationIpByStationId")
    public Map<String, Object> selectStationIpByStationId(Integer stationId){
        return terminalService.selectStationIpByStationId(stationId);
    }

    @GetMapping("findTerminalInfoByStaffId")
    public Integer findTerminalInfoByStaffId(int parseInt){
        return terminalService.findTerminalInfoByStaffId(parseInt);
    }

    @GetMapping("selectTerminalIpInfoByTerminalId")
    public Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId){
        return terminalService.selectTerminalIpInfoByTerminalId(terminalId);
    }

}
