package com.cst.xinhe.web.service.gas.service;


import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.web.service.gas.elasticsearch.entity.GasPositionEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/2/14/15:29
 */
public interface TerminalRoadService {

    List<String> findTimeList(int staffId, String startTime, String endTime);

    List<Map<String,Object>> findTerminalRoadByTime(int staffId, String currentTime, Integer pageSize, Integer startPage) throws ParseException;

    org.springframework.data.domain.Page<GasPositionEntity> findTerminalRoadByInOreTime(int staffId, Date inOre, String startTime, String endTime, Integer startPage, Integer pageSize);

    TerminalRoad findNowSiteByStaffId(int staffId);
}
