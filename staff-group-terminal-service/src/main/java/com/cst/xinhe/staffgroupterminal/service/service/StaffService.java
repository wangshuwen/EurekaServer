package com.cst.xinhe.staffgroupterminal.service.service;


import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.vo.req.StaffInfoVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.github.pagehelper.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StaffService
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 10:23
 * @Vserion v0.0.1
 */

public interface StaffService {


    int addStaff(StaffInfoVO staffInfoVO);

    int deleteStaffByIds(Integer[] ids);

    int updateStaffInfo(StaffInfoVO staffInfoVO);

    String getStaffInfoByStaff(String staffName, Integer startPage, Integer pageSize, Integer orgId, Integer isPerson);

    GasWSRespVO findStaffNameByTerminalId(Integer terminalId);

    Map<String, Object> findStaffGroupAndDeptByStaffId(Integer staffId);

    Map<String, Object> findStaffIdByTerminalId(Integer terminalId);

    HashMap<String,Object> getDeptAndGroupNameByStaffId(Integer staffId);

    Page findContacts(Integer staffId, String staffName, Integer groupId, Integer deptId, Integer pageSize, Integer startPage);

    List<Integer> findAllStaffByDeptId(Integer deptId);

    List<Integer> findAllStaffByGroupId(Integer groupId);

    void updateBindingBaseStation(List<Integer> ids);

    void updateBindingBaseStation(List<Integer> ids, Integer stationId);

    Staff findStaffById(Integer staffId);

    List<Staff> findStaffByTimeStandardId(Integer item);

    int bindingTimeStandard(Integer[] staffIds, Integer standardId);

    int addStaffs(List<StaffInfoVO> staffInfoVOs);

    Map<String, Object> selectStaffInfoByTerminalId(Integer terminalId);

    List<Staff> selectStaffListByJobType(Integer jobType);

    List<Staff> selectStaffByLikeName(String staffName);

    Map<Integer, List<Staff>> findStaffByTimeStandardIds(Integer[] ids);

    Map<Integer, List<Map<String, Object>>> findStaffNameAndGroupNameByStaffIds(List<Integer> list1);

    Map<Integer,Map<String,Object>> findGroupNameByIds(List<Integer> list1);

    List<Map<String,Object>> findStaffByIds(List<Integer> staffIds);
}
