package com.cst.xinhe.station.monitor.server.server;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.service.StationMonitorServerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:01
 **/
@RequestMapping("station-monitor-server/")
@RestController
public class StationMonitorServerController {

    @Resource
    private StationMonitorServerService stationMonitorServerService;

    @PostMapping("sendCmd")
    public void sendCmd(@RequestBody ResponseData responseData){
        stationMonitorServerService.sendCmd(responseData);
    }
}
