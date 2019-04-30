package com.cst.xinhe.gas.service.server;

import com.cst.xinhe.gas.service.service.GasInfoService;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:46
 **/
@RestController
@RequestMapping("gas-service/")
public class GasServiceController {

    @Resource
    private GasInfoService gasInfoService;

    @GetMapping("selectGasInfoByTerminalLastTime")
    public Map<String, Object> selectGasInfoByTerminalLastTime(@RequestParam Integer terminalId){
        return gasInfoService.selectGasInfoByTerminalLastTime(terminalId);
    }

    @GetMapping("selectRoadById")
    public TerminalRoad selectRoadById(Integer positionId){
        return gasInfoService.selectRoadById(positionId);
    }
}
