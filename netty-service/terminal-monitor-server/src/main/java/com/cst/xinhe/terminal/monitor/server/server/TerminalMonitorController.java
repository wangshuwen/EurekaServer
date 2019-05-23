package com.cst.xinhe.terminal.monitor.server.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import com.cst.xinhe.terminal.monitor.server.utils.SequenceIdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 15:39
 **/
@RestController
public class TerminalMonitorController {

    @Autowired
    private TerminalMonitorService terminalMonitorService;

    @RequestMapping("sendResponseData")
    public String sendResponseData(@RequestBody ResponseData responseData){
        responseData.getCustomMsg().setSequenceId(SequenceIdGenerate.getSequenceId());
        terminalMonitorService.sendResponseData(responseData);
        return ResultUtil.jsonToStringSuccess();
    }

    @RequestMapping("getChannelByIpPort")
    public boolean getChannelByIpPort(@RequestParam String ipPort){
        //存在IpPort返回true
        boolean f = terminalMonitorService.getChannelByIpPort(ipPort);
        return f;
    }

    @RequestMapping("getBatteryByTerminalNum")
    public Integer getBattery(@RequestParam Integer terminalNum){
        return terminalMonitorService.getBatteryNumByTerminalNum(terminalNum);
    }

    @GetMapping("getSequenceId")
    public Integer getSequenceId(){
        return SequenceIdGenerate.getSequenceId();
    }
}
