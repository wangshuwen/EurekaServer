package com.cst.xinhe.persistence.vo.req;


/**
 * @ClassName StaffGroupReqVo
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 14:19
 * @Vserion v0.0.1
 */
public class StaffGroupReqVo {

    private Integer groupId;

    private String groupName;

    private Integer deptId;


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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
