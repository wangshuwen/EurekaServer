package com.cst.xinhe.persistence.vo.req;

import java.util.Date;

/**
 * @ClassName StaffInsert
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 14:01
 * @Vserion v0.0.1
 */

public class StaffInsert {

    private String staffNumber;

    private boolean staffSex;

    private String staffIdCard;

    private Date staffBirthday;

    private Integer groupId;

    private String staffName;

    private String staffPhone;

    private Integer terminalId;

    private Integer isPerson;

    private Integer staffJobId;

    public boolean isStaffSex() {
        return staffSex;
    }

    public void setStaffSex(boolean staffSex) {
        this.staffSex = staffSex;
    }

    public String getStaffIdCard() {
        return staffIdCard;
    }

    public void setStaffIdCard(String staffIdCard) {
        this.staffIdCard = staffIdCard;
    }

    public Date getStaffBirthday() {
        return staffBirthday;
    }

    public void setStaffBirthday(Date staffBirthday) {
        this.staffBirthday = staffBirthday;
    }

    public Integer getStaffJobId() {
        return staffJobId;
    }

    public void setStaffJobId(Integer staffJobId) {
        this.staffJobId = staffJobId;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public Integer getIsPerson() {
        return isPerson;
    }

    public void setIsPerson(Integer isPerson) {
        this.isPerson = isPerson;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    @Override
    public String toString() {
        return "StaffInsert{" +
                "staffNumber='" + staffNumber + '\'' +
                ", staffSex=" + staffSex +
                ", staffIdCard='" + staffIdCard + '\'' +
                ", staffBirthday=" + staffBirthday +
                ", groupId=" + groupId +
                ", staffName='" + staffName + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", terminalId=" + terminalId +
                ", isPerson=" + isPerson +
                ", staffJobId=" + staffJobId +
                '}';
    }
}
