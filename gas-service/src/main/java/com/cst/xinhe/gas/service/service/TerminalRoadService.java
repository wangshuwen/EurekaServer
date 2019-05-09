package com.cst.xinhe.gas.service.service;

import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.github.pagehelper.Page;

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

    Page findTimeList(int staffId, Integer pageSize, Integer startPage);

    List<Map<String,Object>> findTerminalRoadByTime(int staffId, String currentTime) throws ParseException;

    List<Map<String,Object>> findTerminalRoadByInOreTime(int staffId, Date inOre, Date startTime, Date endTime);

    TerminalRoad findNowSiteByStaffId(int staffId);
}
