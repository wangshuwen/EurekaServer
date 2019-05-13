package com.cst.xinhe.stationpartition.service.service;


import com.cst.xinhe.persistence.model.mac_station.MacStation;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface MacStationService {

    List<Map<String, Object>> findMacNumber();

    PageInfo<MacStation> findPersonInfoByStation(Integer stationId, Integer startPage, Integer pageSize);
}
