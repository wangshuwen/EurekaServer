package com.cst.xinhe.persistence.dto.staff;

/**
 * @ClassName StaffDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/8 17:13
 * @Vserion v0.0.1
 */

public class StaffDto {

    private Integer staffId;

    private String staffName;

    private String staffNumber;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    @Override
    public String toString() {
        return "StaffDto{" +
                "staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", staffNumber='" + staffNumber + '\'' +
                '}';
    }
}
