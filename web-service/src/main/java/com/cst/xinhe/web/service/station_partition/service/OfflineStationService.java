package com.cst.xinhe.web.service.station_partition.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OfflineStationService {

    PageInfo findOfflineStation(Map<String, Object> params, Integer startPage, Integer pageSize);

    int getOfflineStationCount();

    int removeOfflineStation(Integer stationId);
}
