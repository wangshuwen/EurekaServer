package com.cst.xinhe.gas.service.elasticsearch.service;

import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface GasPositionService {
    Iterable<GasPositionEntity> findTimeFlag(Integer startPage, Integer pageSize, Integer staffId);

    List<Map<String, Object>> findRoadByStaffIdAndTime(int staffId, String currentTime) throws ParseException;

    Page<GasPositionEntity> searchGasPositionWarnInfoByStaffId(Integer gasFlag, String staffName, Integer startPage, Integer pageSize);
}
