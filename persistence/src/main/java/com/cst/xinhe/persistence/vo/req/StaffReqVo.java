package com.cst.xinhe.persistence.vo.req;

import java.util.Date;

/**
 * @ClassName StaffReqVo
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 10:37
 * @Vserion v0.0.1
 */
public class StaffReqVo {

    //打卡绑定基站名称
    private String stationName;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    private Integer terminalId;

    private Integer staffId;

    private String staffNumber;

    private String staffName;

    private Boolean staffSex;

    private Date staffBirthday;

    private String staffIdCard;

    private String staffWedlock;

    private String staffEmail;

    private String staffAddress;

    private String staffPhone;

    private Integer staffJobId;

    private String staffNativePlace;

    private Integer staffTypeId;


    private Integer isPerson;

    private Date createTime;


    private Integer groupId;

    private String groupName;

    private String deptName;

    private Integer deptId;

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public void setIsPerson(Integer isPerson) {
        this.isPerson = isPerson;
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

    public Integer getStaffId() {
        return staffId;
    }


    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }


    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber == null ? null : staffNumber.trim();
    }


    public String getStaffName() {
        return staffName;
    }


    public void setStaffName(String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }


    public Boolean getStaffSex() {
        return staffSex;
    }


    public void setStaffSex(Boolean staffSex) {
        this.staffSex = staffSex;
    }


    public Date getStaffBirthday() {
        return staffBirthday;
    }


    public void setStaffBirthday(Date staffBirthday) {
        this.staffBirthday = staffBirthday;
    }


    public String getStaffIdCard() {
        return staffIdCard;
    }


    public void setStaffIdCard(String staffIdCard) {
        this.staffIdCard = staffIdCard == null ? null : staffIdCard.trim();
    }

    public String getStaffWedlock() {
        return staffWedlock;
    }


    public void setStaffWedlock(String staffWedlock) {
        this.staffWedlock = staffWedlock == null ? null : staffWedlock.trim();
    }

    public String getStaffEmail() {
        return staffEmail;
    }


    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail == null ? null : staffEmail.trim();
    }

    public String getStaffAddress() {
        return staffAddress;
    }


    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress == null ? null : staffAddress.trim();
    }

    public String getStaffPhone() {
        return staffPhone;
    }


    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone == null ? null : staffPhone.trim();
    }

    public Integer getStaffJobId() {
        return staffJobId;
    }

    public void setStaffJobId(Integer staffJobId) {
        this.staffJobId = staffJobId;
    }


    public String getStaffNativePlace() {
        return staffNativePlace;
    }


    public void setStaffNativePlace(String staffNativePlace) {
        this.staffNativePlace = staffNativePlace == null ? null : staffNativePlace.trim();
    }

    public Integer getStaffTypeId() {
        return staffTypeId;
    }


    public void setStaffTypeId(Integer staffTypeId) {
        this.staffTypeId = staffTypeId;
    }


    public Integer getIsPerson() {
        return isPerson;
    }

    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getGroupId() {
        return groupId;
    }


    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }


}
