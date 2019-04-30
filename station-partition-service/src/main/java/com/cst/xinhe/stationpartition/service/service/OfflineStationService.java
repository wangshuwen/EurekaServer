package com.cst.xinhe.stationpartition.service.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OfflineStationService {

    PageInfo findOfflineStation(Map<String, Object> params, Integer startPage, Integer pageSize);

    int getOfflineStationCount();

    int removeOfflineStation(Integer stationId);
}
