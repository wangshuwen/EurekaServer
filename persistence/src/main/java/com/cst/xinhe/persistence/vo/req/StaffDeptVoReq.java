package com.cst.xinhe.persistence.vo.req;

/**
 * @ClassName StaffDeptVoReq
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 14:04
 * @Vserion v0.0.1
 */
public class StaffDeptVoReq {

    private Integer deptId;

    private String deptName;

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
}
