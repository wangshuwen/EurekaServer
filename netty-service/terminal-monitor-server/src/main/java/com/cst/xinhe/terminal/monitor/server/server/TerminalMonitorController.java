package com.cst.xinhe.terminal.monitor.server.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.terminal.monitor.server.service.TerminalMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 15:39
 **/
@RestController
@RequestMapping("terminal-monitor-server")
public class TerminalMonitorController {

    @Autowired
    private TerminalMonitorService terminalMonitorService;

    @RequestMapping("sendResponseData")
    public String sendResponseData(@RequestBody ResponseData responseData){
        terminalMonitorService.sendResponseData(responseData);
        return ResultUtil.jsonToStringSuccess();
    }

    @RequestMapping("getChannelByIpPort")
    public boolean getChannelByIpPort(@RequestParam String ipPort){
        //存在IpPort返回true
        boolean f = terminalMonitorService.getChannelByIpPort(ipPort);
        return f;
    }
}
