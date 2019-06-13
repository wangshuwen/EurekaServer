package com.cst.xinhe.web.service.gas.service;


import com.cst.xinhe.persistence.model.staff.StaffOrganization;

import java.util.List;

/**
 * @author lifeng
 * @Description:
 * @Date 2019/1/9/10:20
 */
public interface StaffOrganizationService {
    List<StaffOrganization> findStaffOrganization();

    Integer add(StaffOrganization staffOrganization);

    Integer delete(Integer id);

    Integer update(StaffOrganization staffOrganization);

    String getDeptNameByGroupId(Integer groupId);

    List<Integer> findSonIdsByDeptName(String DeptName);

    List<Integer> findSonIdsByDeptId(Integer deptId);

    List<StaffOrganization> getOneSonByParent(int i);
}
