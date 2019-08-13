package com.cst.xinhe.web.service.aq_test;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.web.service.feign.client.StationMonitorServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-08-12 10:38
 **/
@RequestMapping("aq/test/")
@RestController
public class AqController {

    @Resource
    private StationMonitorServerClient stationMonitorServerClient;

    @GetMapping("sendControl")
    public String sendControlInstructions(){
        stationMonitorServerClient.sendControl();
        return ResultUtil.jsonToStringSuccess();
    }
}
