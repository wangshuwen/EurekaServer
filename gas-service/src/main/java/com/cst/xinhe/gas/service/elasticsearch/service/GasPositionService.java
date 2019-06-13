package com.cst.xinhe.gas.service.elasticsearch.service;

import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GasPositionService {
    Iterable<GasPositionEntity> findTimeFlag(Integer startPage, Integer pageSize, Integer staffId);

    List<Map<String, Object>> findRoadByStaffIdAndTime(int staffId, String currentTime) throws ParseException;

    Page<GasPositionEntity> searchGasPositionWarnInfoByStaffId(Integer gasFlag, String staffName, Integer startPage, Integer pageSize);

    Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, String startTime, String endTime, Integer startPage, Integer pageSize);

    TerminalRoad findNowSiteByStaffId(int staffId);

    Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId);

    Map<String, Object> findRecentlyGasInfoByStaffId(Integer staffId);

    List<Map<String, Object>> selectGasInfoLastTenData(int number);

    PageInfo<Map<String, Object>> getEMsgList(Integer pageSize, Integer startPage, String staffName, Integer staffId,Integer type);

//    Long getWarningGasCount();
}
