package com.cst.xinhe.station.monitor.server.service.impl;

import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.station.monitor.server.request.SingletonStationClient;
import com.cst.xinhe.station.monitor.server.service.StationMonitorServerService;
import org.springframework.stereotype.Service;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:04
 **/
@Service
public class StationMonitorServerServiceImpl implements StationMonitorServerService {

    @Override
    public void sendCmd(ResponseData responseData) {
        SingletonStationClient.getSingletonStationClient().sendCmd(responseData);
    }
}
