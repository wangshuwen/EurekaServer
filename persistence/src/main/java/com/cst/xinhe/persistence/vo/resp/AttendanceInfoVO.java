package com.cst.xinhe.persistence.vo.resp;

import java.util.Date;

/**
 * @program: demo
 * @description: 考勤信息类
 * @author: lifeng
 * @create: 2019-01-26 10:35
 **/
public class AttendanceInfoVO {

    private String attendanceId;

    private String timeStandardName;

    private Integer staffId;

    private String staffName;

    private String stationName;

    private Date startTime;

    private Date endTime;

    private Date inOre;

    private Date outOre;

    private String jobName;

    private String deptName;

    private String backUp1;

    private String backUp2;

    private Integer orgId;

    private String  attendanceRecord;

    public AttendanceInfoVO() {
    }

    public AttendanceInfoVO(String attendanceId, String timeStandardName, Integer staffId, String staffName, String stationName, Date startTime, Date endTime, Date inOre, Date outOre, String jobName, String deptName, String backUp1, String backUp2, Integer orgId, String attendanceRecord) {
        this.attendanceId = attendanceId;
        this.timeStandardName = timeStandardName;
        this.staffId = staffId;
        this.staffName = staffName;
        this.stationName = stationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.inOre = inOre;
        this.outOre = outOre;
        this.jobName = jobName;
        this.deptName = deptName;
        this.backUp1 = backUp1;
        this.backUp2 = backUp2;
        this.orgId = orgId;
        this.attendanceRecord = attendanceRecord;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getTimeStandardName() {
        return timeStandardName;
    }

    public void setTimeStandardName(String timeStandardName) {
        this.timeStandardName = timeStandardName;
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

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getInOre() {
        return inOre;
    }

    public void setInOre(Date inOre) {
        this.inOre = inOre;
    }

    public Date getOutOre() {
        return outOre;
    }

    public void setOutOre(Date outOre) {
        this.outOre = outOre;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBackUp1() {
        return backUp1;
    }

    public void setBackUp1(String backUp1) {
        this.backUp1 = backUp1;
    }

    public String getBackUp2() {
        return backUp2;
    }

    public void setBackUp2(String backUp2) {
        this.backUp2 = backUp2;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getAttendanceRecord() {
        return attendanceRecord;
    }

    public void setAttendanceRecord(String attendanceRecord) {
        this.attendanceRecord = attendanceRecord;
    }

    @Override
    public String toString() {
        return "AttendanceInfoVO{" +
                "attendanceId=" + attendanceId +
                ", timeStandardName='" + timeStandardName + '\'' +
                ", staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", stationName='" + stationName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", inOre=" + inOre +
                ", outOre=" + outOre +
                ", jobName='" + jobName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", backUp1='" + backUp1 + '\'' +
                ", backUp2='" + backUp2 + '\'' +
                ", orgId=" + orgId +
                ", attendanceRecord='" + attendanceRecord + '\'' +
                '}';
    }
}
