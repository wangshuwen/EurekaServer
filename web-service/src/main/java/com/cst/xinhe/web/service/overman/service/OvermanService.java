package com.cst.xinhe.web.service.overman.service;

import com.github.pagehelper.Page;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-05/9:14
 */
public interface OvermanService {
    Page getOvermanAreaInfo(Integer startPage, Integer pageSize, String starOverTime, String endOverTime, String areaName);

    Page getOvermanStaffInfo(Integer startPage, Integer pageSize, String staffName, Integer overmanAreaId);
}
