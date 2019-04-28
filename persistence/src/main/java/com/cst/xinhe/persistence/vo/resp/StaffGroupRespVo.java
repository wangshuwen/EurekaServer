package com.cst.xinhe.persistence.vo.resp;


import com.cst.xinhe.persistence.dto.staff.StaffDto;

import java.util.List;

/**
 * @ClassName StaffGroupRespVo
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 14:43
 * @Vserion v0.0.1
 */

public class StaffGroupRespVo {

    private Integer groupId;

    private String groupName;

    private String deptName;

    private Integer deptId;

    private List<StaffDto> staffList;


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<StaffDto> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<StaffDto> staffList) {
        this.staffList = staffList;
    }
}
