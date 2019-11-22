package com.cst.xinhe.web.service.gas.elasticsearch.service;


import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.web.service.gas.elasticsearch.entity.GasPositionEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;


import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GasPositionService {
    Iterable<GasPositionEntity> findTimeFlag(Integer staffId, String startTime, String currentMonth);

    List<Map<String, Object>> findRoadByStaffIdAndTime(int staffId,  Integer startPage, Integer pageSize,String startTime,String endTime,Integer isOre) throws ParseException;

    Page<GasPositionEntity> searchGasPositionWarnInfoByStaffId(Integer gasFlag, String staffName, Integer startPage, Integer pageSize, String startTime, String endTime);

    Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOreTime, String startTime, String endTime, Integer startPage, Integer pageSize);

    TerminalRoad findNowSiteByStaffId(int staffId);

    Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId);

    Map<String, Object> findRecentlyGasInfoByStaffId(Integer staffId);

    List<Map<String, Object>> selectGasInfoLastTenData(int number);

    PageInfo<Map<String, Object>> getEMsgList(Integer pageSize, Integer startPage, String staffName, Integer staffId, Integer type);

//    Long getWarningGasCount();
}
