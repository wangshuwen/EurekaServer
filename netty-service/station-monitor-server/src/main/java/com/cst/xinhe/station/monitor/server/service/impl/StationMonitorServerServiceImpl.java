package com.cst.xinhe.station.monitor.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeServiceException;
import com.cst.xinhe.common.netty.data.request.RequestData;
import com.cst.xinhe.common.netty.data.response.ResponseData;
import com.cst.xinhe.common.utils.GetUUID;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.base_station.OfflineStationMapper;
import com.cst.xinhe.persistence.dao.mac_station.MacStationMapper;
import com.cst.xinhe.persistence.dao.terminal.TerminalUpdateIpMapper;
import com.cst.xinhe.persistence.model.base_station.OfflineStationExample;
import com.cst.xinhe.persistence.model.mac_station.MacStation;
import com.cst.xinhe.persistence.model.mac_station.MacStationExample;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.station.monitor.server.client.WsPushServiceClient;
import com.cst.xinhe.station.monitor.server.request.SingletonStationClient;
import com.cst.xinhe.station.monitor.server.service.StationMonitorServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    TerminalUpdateIpMapper terminalUpdateIpMapper;

    @Resource
    OfflineStationMapper offlineStationMapper;

    @Resource
    WsPushServiceClient wsPushServiceClient;

    private Map<String, Object> map;

    public StationMonitorServerServiceImpl() {
        this.map = new HashMap<>();
    }

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
      if(len>0){
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

    @Override
    public void updateStationIp(RequestData reqMsg) {
//        String terminalIp = jsonObject.getString("terminalIp");
        String terminalIp = reqMsg.getTerminalIp();
//        String stationIp = jsonObject.getString("stationIp");
        String stationIp = reqMsg.getStationIp();
//        Integer terminalId = jsonObject.getInteger("terminalId");
        Integer terminalId = reqMsg.getTerminalId();
//        Integer stationId = jsonObject.getInteger("stationId");
        Integer stationId = reqMsg.getStationId();

//        Integer terminalPort = jsonObject.getInteger("terminalPort");
        Integer terminalPort = reqMsg.getTerminalPort();
//        Integer stationPort = jsonObject.getInteger("stationPort");
        Integer stationPort = reqMsg.getStationPort();

        TerminalUpdateIp terminalUpdateIp = new TerminalUpdateIp();
        terminalUpdateIp.setStationId(stationId);
        terminalUpdateIp.setStationIp(stationIp);
        terminalUpdateIp.setTerminalIp(terminalIp);
        terminalUpdateIp.setTerminalNum(terminalId);
        terminalUpdateIp.setUpdateTime(new Date());
        terminalUpdateIp.setStationPort(stationPort);
        terminalUpdateIp.setTerminalPort(terminalPort);



        /**
         * @description 根据terminalId 检查是否存在terminalId;
         *                  如果存在则更新
         *                  若不存在直接插入
         * @date 14:55 2018/10/19
         * @auther lifeng
         **/
           /* boolean isExits = terminalUpdateIpMapper.checkStationIdIsNotExist(stationId);
            if (isExits) {
                logger.info("基站IP已存在，更新IP");

            } else {*/
//                logger.info("基站IP不存在，新增IP");
//                terminalUpdateIpMapper.insertSelective(terminalUpdateIp);
        /* }*/
           terminalUpdateIpMapper.updateIpInfoByStationId(terminalUpdateIp);
//        staffGroupTerminalServiceClient.updateIpInfoByStationId(terminalUpdateIp);
        // 基站上线，在掉线统计表中，根据基站的ID移除该基站
        OfflineStationExample offlineStationExample = new OfflineStationExample();
        OfflineStationExample.Criteria criteria = offlineStationExample.createCriteria();
        offlineStationMapper.deleteByPrimaryKey(stationId);
        int count = offlineStationMapper.selectByExample(offlineStationExample).size();
        map.put("offlineNum", count);
        try {
            System.out.println(map.get("offlineNum"));
//                    WebsocketServer.sendInfo(JSONObject.toJSONString(new WebSocketData(8, map)));
            wsPushServiceClient.sendWebsocketServer(JSONObject.toJSONString(new WebSocketData(8, map)));
        } catch (Exception e) {
            throw new RuntimeServiceException(ErrorCode.SEND_WS_OFFLINE_STATION_ERROR);
        }
    }
}
