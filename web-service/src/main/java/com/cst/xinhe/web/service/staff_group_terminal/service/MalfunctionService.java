package com.cst.xinhe.web.service.staff_group_terminal.service;


import com.cst.xinhe.persistence.model.malfunction.Malfunction;

import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/11/30/9:29
 */
public interface MalfunctionService {


    List<Malfunction> findMalfunctionInfo(Integer teminalId, Integer status);

    int updateStatusById(Integer selfCheckId);

    Integer deleteMalfunctionByIds(Integer[] ids);

    Integer getCount();

    void addMalfunction(Malfunction malfunction);

    Map<String, Object> getCountMalfunction();

    List<Malfunction> findMalfunctionInfoByStatus1();
}
