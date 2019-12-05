package com.cst.xinhe.persistence.dto.staff;

import java.util.Date;

/**
 * @ClassName StaffInfoDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/30 9:20
 * @Vserion v0.0.1
 */

public class StaffInfoDto {
    private String baseStationName;

    public String getBaseStationName() {
        return baseStationName;
    }

    public void setBaseStationName(String baseStationName) {
        this.baseStationName = baseStationName;
    }

    private StaffGroupDto staffGroupDto;

//    private StaffJob staffJob;

    private Integer groupId;

    private String groupName;

    private Integer deptId;

    private String deptName;

    private Date createTime;

    private String staffIdCard;

    private Integer staffId;

    private String staffName;

    private String staffNumber;

    private String staffPhone;

    private Integer staffSex;

    private Integer isPerson;

    private Integer terminalId;

    private Integer jobId;

    private String jobName;

    private String remark;

    //候补
    private Integer partitionId;
    private  String partitionName;

    public Integer getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Integer partitionId) {
        this.partitionId = partitionId;
    }

    public String getPartitionName() {
        return partitionName;
    }

    public void setPartitionName(String partitionName) {
        this.partitionName = partitionName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private Date staffBirthday;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStaffIdCard() {
        return staffIdCard;
    }

    public void setStaffIdCard(String staffIdCard) {
        this.staffIdCard = staffIdCard;
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

    public StaffGroupDto getStaffGroupDto() {
        return staffGroupDto;
    }

    public void setStaffGroupDto(StaffGroupDto staffGroupDto) {
        this.staffGroupDto = staffGroupDto;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setIsPerson(Integer isPerson) {
        this.isPerson = isPerson;
    }

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

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public Integer getIsPerson() {
        return isPerson;
    }

    public void setIsPersion(Integer isPerson) {
        this.isPerson = isPerson;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
