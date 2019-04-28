package com.cst.xinhe.persistence.dto.staff;

/**
 * @ClassName StaffGroupDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/27 13:13
 * @Vserion v0.0.1
 */

public class StaffGroupDto {

    private Integer groupId;

    private String groupName;

    private String deptName;

    private Integer deptId;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

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
}
