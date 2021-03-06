package com.cst.xinhe.staffgroupterminal.service.server;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.lack_electric.LackElectricExample;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.staffgroupterminal.service.service.LackElectricService;
import com.cst.xinhe.staffgroupterminal.service.service.MalfunctionService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import com.cst.xinhe.staffgroupterminal.service.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 10:46
 **/
@RestController
//@RequestMapping("staff-group-terminal-service")
public class TerminalControllerServer {
    @Autowired
    private StaffService staffService;

    @Autowired
    private TerminalService terminalService;

    @Resource
    private LackElectricService lackElectricService;

    @Resource
    private MalfunctionService malfunctionService;

    @GetMapping("findTerminalIdByIpAndPort")
    public TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam("terminalIp") String terminalIp, @RequestParam("port") Integer port){
        TerminalUpdateIp terminalUpdateIp = terminalService.findTerminalIdByIpAndPort(terminalIp, port);
        return terminalUpdateIp;
    }

    @GetMapping("selectStationIpByStationId")
    public Map<String, Object> selectStationIpByStationId(Integer stationId){
        return terminalService.selectStationIpByStationId(stationId);
    }

    @GetMapping("findTerminalInfoByStaffId")
    public Integer findTerminalInfoByStaffId(@RequestParam("parseInt") Integer parseInt){
        Integer terminalId = terminalService.findTerminalInfoByStaffId(parseInt);
        if(terminalId==null){
            return 0;
        }
        return terminalId;
    }

    @GetMapping("selectTerminalIpInfoByTerminalId")
    public Map<String, Object> selectTerminalIpInfoByTerminalId(@RequestParam("terminalId") Integer terminalId){
        return terminalService.selectTerminalIpInfoByTerminalId(terminalId);
    }

    @PutMapping("updateIpInfoByTerminalId")
    public void updateIpInfoByTerminalId(@RequestBody TerminalUpdateIp terminalUpdateIp){
        terminalService.updateIpInfoByTerminalId(terminalUpdateIp);
    }
    @PutMapping("updateIpInfoByStationId")
    public void updateIpInfoByStationId(@RequestBody TerminalUpdateIp terminalUpdateIp){
        terminalService.updateIpInfoByStationId(terminalUpdateIp);
    }

    @DeleteMapping("deleteLeLackElectricByLackElectric")
    public void deleteLeLackElectricByLackElectric(@RequestBody LackElectric lackElectric){
        lackElectricService.deleteLeLackElectricByLackElectric(lackElectric);
    }

    @PostMapping("addMalfunction")
    public void addMalfunction(@RequestBody Malfunction malfunction){
        malfunctionService.addMalfunction(malfunction);
    }

    @PutMapping("updateLackElectric")
    public void updateLackElectric(@RequestBody LackElectric lackElectric){
        lackElectricService.updateLackElectric(lackElectric);
    }

    @GetMapping("selectLackElectric")
    public Long getLackElectricList(){
        return lackElectricService.getLackElectricList();
    }

    @GetMapping("getCountMalfunction")
    public Map<String, Object> getCountMalfunction(){
        return malfunctionService.getCountMalfunction();
    }

}
