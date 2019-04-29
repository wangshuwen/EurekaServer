package com.cst.xinhe.terminal.monitor.server.controller;

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
public class TerminalMonitorController {

    @RequestMapping("")
    public boolean getChannelByIpPort(@RequestParam String ipPort){
        return true;
    }
}
