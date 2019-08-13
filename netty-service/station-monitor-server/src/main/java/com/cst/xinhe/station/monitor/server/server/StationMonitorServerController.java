package com.cst.xinhe.station.monitor.server.server;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.request.SingletonStationClient;
import com.cst.xinhe.station.monitor.server.service.StationMonitorServerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:01
 **/
//@RequestMapping("station-monitor-server/")
@RestController
public class StationMonitorServerController {

    @Resource
    private StationMonitorServerService stationMonitorServerService;

    @PostMapping("sendCmd")
    public void sendCmd(@RequestBody ResponseData responseData){
        stationMonitorServerService.sendCmd(responseData);
    }

    @GetMapping("sendControl")
    public String sendControl(){
        stationMonitorServerService.sendControlAqTest();
        return ResultUtil.jsonToStringSuccess();
    }
}
