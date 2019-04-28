package com.cst.xinhe.persistence.vo.req;


import java.util.Date;

/**
 * @ClassName StaffInfoVO
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/30 15:28
 * @Vserion v0.0.1
 */
public class StaffInfoVO {

    private Integer deptId;
    private Integer staffId;
    private Integer terminalId;

    private String staffName;

    private Integer staffSex;

    private Date staffBirthday;

    private String staffIdCard;

    private String staffPhone;

    private Integer jobId;

    private Integer isPerson;

    private Date createTime;

    private Integer groupId;

    private String groupName;

    private String deptName;

    private String jobName;


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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(Integer staffSex) {
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
        this.staffIdCard = staffIdCard;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getIsPerson() {
        return isPerson;
    }

    public void setIsPerson(Integer isPerson) {
        this.isPerson = isPerson;
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
