package com.cst.xinhe.station.monitor.server.service.impl;

import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.utils.GetUUID;
import com.cst.xinhe.persistence.dao.mac_station.MacStationMapper;
import com.cst.xinhe.persistence.model.mac_station.MacStation;
import com.cst.xinhe.persistence.model.mac_station.MacStationExample;
import com.cst.xinhe.station.monitor.server.request.SingletonStationClient;
import com.cst.xinhe.station.monitor.server.service.StationMonitorServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 15:04
 **/
@Service
public class StationMonitorServerServiceImpl implements StationMonitorServerService {

    @Resource
    MacStationMapper macStationMapper;

    @Override
    public void sendCmd(ResponseData responseData) {
        SingletonStationClient.getSingletonStationClient().sendCmd(responseData);
    }

    @Override
    public void wifiForkProcess(RequestData reqMsg) {
        byte[] body = reqMsg.getBody();

        for (byte b: body){
            System.out.printf("0x%02x ",b);
        }
        int len = body[0] & 0xff;   //获取mac地址的总数量
        int f = 0;  //判断计数
        List<MacStation> list = new ArrayList<>();
        StringBuffer mac_str = new StringBuffer();
        for (int i = 1 ; i <= len * 6 ; i++){

            Integer tt = (body[i] & 0xff);

            String s = Integer.toHexString(tt);
            if (s.length() < 2){
                s = "0" + s;
            }

            mac_str.append(s.toUpperCase());
            if (f < 5) {
                mac_str.append("-");
            }
            f++;
            if (f > 5){
                MacStation macStation = new MacStation();
                macStation.setMac(mac_str.toString());
                macStation.setMacStationId(GetUUID.getUuidReplace());
                macStation.setStationId(reqMsg.getStationId());
                list.add(macStation);
                mac_str = new StringBuffer();
                f = 0;
            }
        }
        MacStationExample macStationExample = new MacStationExample();
        MacStationExample.Criteria criteria = macStationExample.createCriteria();
        criteria.andStationIdEqualTo(reqMsg.getStationId());

        macStationMapper.deleteByExample(macStationExample);

        for (MacStation item: list) {
            String mac = item.getMac();
            boolean flag = macStationMapper.selectMacExistByMac(mac);

            if (flag) {
                macStationMapper.deleteByMac(mac);
            }

            macStationMapper.insert(item);
        }

    }
}
