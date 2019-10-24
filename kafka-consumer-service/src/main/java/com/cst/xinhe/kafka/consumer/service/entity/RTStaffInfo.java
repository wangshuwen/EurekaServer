package com.cst.xinhe.kafka.consumer.service.entity;

import lombok.Data;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-27 09:18
 **/
@Data
public class RTStaffInfo {

    private Integer staffId;

    private String staffName;

    private Integer staffSex;

    private String staffBirthday;

    private String staffPhone;

    private String isPerson;

    private String staffIdCard;

    private Integer timeStandardId;

    private Integer attendanceStationId;

    private Integer groupId;

    private String groupName;

    private Integer staffJobId;

    private String staffJobName;

    private String startTime;

    private String endTime;

    private Integer elasticTime;

    private Integer lateTime;

    private Integer seriousLateTime;

    private Integer leaveEarlyTime;

    private Integer seriousLeaveEarlyTime;

    private Integer userId;

    private Integer flag;

    private Integer overTime;
    private Integer seriousTime;

    private String remark;

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

    public Integer getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(Integer staffSex) {
        this.staffSex = staffSex;
    }

    public String getStaffBirthday() {
        return staffBirthday;
    }

    public void setStaffBirthday(String staffBirthday) {
        this.staffBirthday = staffBirthday;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getIsPerson() {
        return isPerson;
    }

    public void setIsPerson(String isPerson) {
        this.isPerson = isPerson;
    }

    public String getStaffIdCard() {
        return staffIdCard;
    }

    public void setStaffIdCard(String staffIdCard) {
        this.staffIdCard = staffIdCard;
    }

    public Integer getTimeStandardId() {
        return timeStandardId;
    }

    public void setTimeStandardId(Integer timeStandardId) {
        this.timeStandardId = timeStandardId;
    }

    public Integer getAttendanceStationId() {
        return attendanceStationId;
    }

    public void setAttendanceStationId(Integer attendanceStationId) {
        this.attendanceStationId = attendanceStationId;
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

    public Integer getStaffJobId() {
        return staffJobId;
    }

    public void setStaffJobId(Integer staffJobId) {
        this.staffJobId = staffJobId;
    }

    public String getStaffJobName() {
        return staffJobName;
    }

    public void setStaffJobName(String staffJobName) {
        this.staffJobName = staffJobName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getElasticTime() {
        return elasticTime;
    }

    public void setElasticTime(Integer elasticTime) {
        this.elasticTime = elasticTime;
    }

    public Integer getLateTime() {
        return lateTime;
    }

    public void setLateTime(Integer lateTime) {
        this.lateTime = lateTime;
    }

    public Integer getSeriousLateTime() {
        return seriousLateTime;
    }

    public void setSeriousLateTime(Integer seriousLateTime) {
        this.seriousLateTime = seriousLateTime;
    }

    public Integer getLeaveEarlyTime() {
        return leaveEarlyTime;
    }

    public void setLeaveEarlyTime(Integer leaveEarlyTime) {
        this.leaveEarlyTime = leaveEarlyTime;
    }

    public Integer getSeriousLeaveEarlyTime() {
        return seriousLeaveEarlyTime;
    }

    public void setSeriousLeaveEarlyTime(Integer seriousLeaveEarlyTime) {
        this.seriousLeaveEarlyTime = seriousLeaveEarlyTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getOverTime() {
        return overTime;
    }

    public void setOverTime(Integer overTime) {
        this.overTime = overTime;
    }

    public Integer getSeriousTime() {
        return seriousTime;
    }

    public void setSeriousTime(Integer seriousTime) {
        this.seriousTime = seriousTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
