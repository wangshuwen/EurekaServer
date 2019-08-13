package com.cst.xinhe.station.monitor.server.service;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:03
 **/
public interface StationMonitorServerService {
    void sendCmd(ResponseData responseData);

    void wifiForkProcess(RequestData reqMsg);

    void updateStationIp(RequestData reqMsg);

    void sendControlAqTest();
}
