package com.cst.xinhe.web.service.staff_group_terminal.service;

import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.github.pagehelper.Page;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/2/13/17:36
 */
public interface LackElectricService {

    Page findLackElectric(Integer pageSize, Integer startPage, Integer terminalId, String staffName);

    long findIsReadCount();

    Integer updateIsRead();

    Integer findLackElectricCount();

    void deleteLeLackElectricByLackElectric(LackElectric lackElectric);

    void updateLackElectric(LackElectric lackElectric);

    Long getLackElectricList();
}
