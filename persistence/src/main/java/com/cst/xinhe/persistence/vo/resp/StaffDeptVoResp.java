package com.cst.xinhe.persistence.vo.resp;


import com.cst.xinhe.persistence.model.staff.Staff;

import java.util.List;

/**
 * @ClassName StaffDeptVoResp
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 13:37
 * @Vserion v0.0.1
 */
public class StaffDeptVoResp {


    private Integer deptId;
    private String deptName;

    private List<Staff> staffList;


    public StaffDeptVoResp() {
    }

    public StaffDeptVoResp(Integer deptId, String deptName) {
        this.deptId = deptId;
        this.deptName = deptName;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

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
