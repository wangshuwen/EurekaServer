package com.cst.xinhe.web.service.staff_group_terminal.service;


import com.cst.xinhe.persistence.model.staff.StaffOrganization;

import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/9/10:20
 */
public interface StaffOrganizationService {
    List<StaffOrganization> findStaffOrganization();

    Integer add(StaffOrganization staffOrganization);

    Integer delete(Integer id);

    Integer update(StaffOrganization staffOrganization);

    String getDeptNameByGroupId(Integer groupId);
    List<Map<String,Object>> getDeptNameByGroupIds(List<Integer> group_ids);

    List<Integer> findSonIdsByDeptName(String DeptName);

    List<Integer> findSonIdsByDeptId(Integer deptId);

    List<StaffOrganization> getOneSonByParent(int i);


}
