package com.cst.xinhe.stationpartition.service.service;

import com.cst.xinhe.persistence.model.coordinate.Coordinate;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.vo.resp.WarningAreaVO;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/3/18/13:58
 */
public interface WarningAreaService {

    List<Coordinate> findCoordinateByAreaId(Integer areaId);


    Integer addAreaInfo(WarningArea warningArea);


    Integer deleteAreaInfo(Integer id);

    Integer updateAreaInfo(WarningArea warningArea);


    Integer addCoordinateList(List<Coordinate> list);

    Integer updateCoordinateList(List<Coordinate> list, Integer areaId);

    WarningAreaVO getAllAreaInfo(Integer areaId);

    Page findAreaInfoByType(Integer type, Integer pageSize, Integer startPage, String name, Integer areaId);

    Page findAreaRecordByAreaId(Integer type, Integer areaId, Integer pageSize, Integer startPage, String staffName, Integer deptId);

    Page findHistoryAreaRecord(Integer areaId, Integer pageSize, Integer startPage, String staffName, Integer deptId);

    Page findAreaRecordByStaffId(Integer type, Integer areaId, Integer pageSize, Integer startPage, Integer staffId);

  /*  Integer addAreaRecord(WarningAreaRecord warningAreaRecord);

    Integer updateOutTime(Integer staffId);*/

    WarningAreaVO getAll();

    Integer findStaffNumByType(Integer type);

    Integer deleteCoordinate(Integer id);

    Page printReportOfWarningArea(Integer type, String date,Integer startPage, Integer pageSize);
}
