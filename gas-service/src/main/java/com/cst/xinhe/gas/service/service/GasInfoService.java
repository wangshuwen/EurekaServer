package com.cst.xinhe.gas.service.service;

import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.github.pagehelper.Page;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GasInfoService
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/21 16:10
 * @Vserion v0.0.1
 */

public interface GasInfoService {

    /**
     * @param [staffName, startPage, pageSize]  员工姓名， 分页查询参数的起始页数 ，每页显示的数量
     * @return java.lang.String
     * @description 通过员工姓名查询气体信息
     * @date 17:36 2018/10/26
     * @auther lifeng
     **/
//    String findGasInfoByStaffName(String staffName, Integer startPage, Integer pageSize);


    Page findGasInfoByStaffName(Integer gasFlag, String staffName, Integer startPage, Integer pageSize);
    /**
     * @param [terminalId] 终端Id
     * @return com.cst.xinhe.persistence.vo.resp.GasWSRespVO
     * @description 通过终端ID 获取气体信息
     * @date 17:37 2018/10/26
     * @auther lifeng
     **/
    GasWSRespVO findGasInfoByStaffIdAndTerminalId(Integer terminalId) throws ParseException;

    List<GasWSRespVO> findGasInfoLastTenRecords(int number);

    Map<String,Object> findRecentlyGasInfoByStaffId(Integer staffId);

    Map<String, Object> selectGasInfoByTerminalLastTime(Integer terminalId);

//    TerminalRoad selectRoadById(Integer positionId);
}
