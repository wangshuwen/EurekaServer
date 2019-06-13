package com.cst.xinhe.web.service.station_partition.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cst.xinhe.common.ws.WebSocketData;
import com.cst.xinhe.persistence.dao.base_station.OfflineStationMapper;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.base_station.OfflineStation;
import com.cst.xinhe.persistence.model.base_station.OfflineStationExample;
import com.cst.xinhe.web.service.feign.client.WsPushServiceClient;
import com.cst.xinhe.web.service.station_partition.service.BaseStationService;
import com.cst.xinhe.web.service.station_partition.service.OfflineStationService;
import com.cst.xinhe.web.service.station_partition.service.PartitionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: demo
 * @description:
 * @author: lifeng
 * @create: 2019-02-19 10:28
 **/
@Service
public class OfflineStationServiceImpl implements OfflineStationService {

    private Map<String, Object> map;


    @Autowired
    private OfflineStationMapper offlineStationMapper;

    @Autowired
    private BaseStationService baseStationService;

    @Autowired
    private PartitionService partitionService;

    @Autowired
    private WsPushServiceClient wsPushServiceClient;


    public OfflineStationServiceImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public PageInfo findOfflineStation(Map<String, Object> params, Integer startPage, Integer pageSize) {
        Integer partitionId = (Integer) params.get("partitionId");

        if (partitionId != null) {
            List<Integer> list = partitionService.getSonIdsById(partitionId);
            Set<Integer> set = new HashSet<>();
            for (Integer item : list) {
                List<BaseStation> baseStations = baseStationService.findBaseStationByZoneId(item);
                if (baseStations.size() > 0) {
                    for (BaseStation key : baseStations) {
                        set.add(key.getBaseStationNum());
                    }
                }
            }
            if (set.isEmpty()) {
                Page page = PageHelper.startPage(startPage, pageSize);
                List<Map<String, Object>> offlineStationList = offlineStationMapper.selectOfflineStationPartitionIdsIsNull(params);
                PageInfo pageInfo = new PageInfo(page);
                return pageInfo;
            } else {
                params.put("stationIds", set);
                Page page = PageHelper.startPage(startPage, pageSize);
                List<Map<String, Object>> offlineStationList = offlineStationMapper.selectOfflineStationPartitionIdsNotNull(params);
                PageInfo pageInfo = new PageInfo(page);
                return pageInfo;
            }
        }
        Page page = PageHelper.startPage(startPage, pageSize);
        List<Map<String, Object>> offlineStationList = offlineStationMapper.selectOfflineStation(params);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }

    @Override
    public int getOfflineStationCount() {
        OfflineStationExample offlineStationExample = new OfflineStationExample();
        OfflineStationExample.Criteria criteria = offlineStationExample.createCriteria();
        return offlineStationMapper.selectByExample(offlineStationExample).size();
    }

    /**
     * 移除基站掉线表中的基站信息
     *
     * @param stationId
     * @return
     */
    @Override
    public int removeOfflineStation(Integer stationId) {
        int result = offlineStationMapper.deleteByPrimaryKey(stationId);
        OfflineStationExample offlineStationExample = new OfflineStationExample();
        List<OfflineStation> offlineStationList = offlineStationMapper.selectByExample(offlineStationExample);
        int offlineNum = offlineStationList.size();
        map.put("offlineNum", offlineNum);
        // WebsocketServer.sendInfo(JSONObject.toJSONString(new WebSocketData(8, map)));
        wsPushServiceClient.sendInfo(JSONObject.toJSONString(new WebSocketData(8, map)));
        return result == 1 ? 1 : 0;
    }
}
