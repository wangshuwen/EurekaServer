package com.cst.xinhe.web.service.overman.service.impl;


import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dao.overman_area.OvermanAreaMapper;
import com.cst.xinhe.persistence.model.overman_area.OvermanArea;
import com.cst.xinhe.web.service.overman.service.OvermanService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-05/9:14
 */
@Service
public class OvernmanServiceImpl implements OvermanService {
    @Resource
    private OvermanAreaMapper overmanAreaMapper;

    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Override
    public Page getOvermanAreaInfo(Integer startPage, Integer pageSize, String starOverTime, String endOverTime, String areaName) {
        Date starTime=null;
        Date endTime=null;

        try {
            if(starOverTime!=null&&endOverTime!=null&!"".equals(starOverTime)){
                starTime = DateConvert.convertStringToDate(starOverTime, 10);
                endTime  = DateConvert.convertStringToDate(starOverTime, 10);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Page  page = PageHelper.startPage(startPage, pageSize);
        List<OvermanArea> list =overmanAreaMapper.getOvermanAreaInfo(starTime,endTime,areaName);


        return page;
    }

    @Override
    public Page getOvermanStaffInfo(Integer startPage, Integer pageSize, String staffName, Integer overmanAreaId) {
         Page page = PageHelper.startPage(startPage, pageSize);
        List<HashMap<String,Object>> list =overmanAreaMapper.getOvermanStaffInfo(staffName,overmanAreaId);
        for (HashMap<String, Object> map : list) {
            map.put("deptName",staffOrganizationService.getDeptNameByGroupId((Integer) map.get("groupId")));
        }

        return page;
    }
}
