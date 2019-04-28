package com.cst.xinhe.persistence.vo.req;

import java.util.Date;
import java.util.List;

/**
 * @program: demo
 * @description: 考勤信息多条件封装
 * @author: lifeng
 * @create: 2019-01-26 10:17
 **/
public class AttendanceParamsVO {

    private Integer startPage;

    private Integer pageSize;

    private String staffName;   //  员工姓名

    private Integer orgId;  // 部门

    private List<Integer> staffIdOfList;

    private Integer timeStandardId; // 考勤标准

    private Date startTime; // 开始范围
    private String startTime1; // 开始范围

    private Date endTime;   //  结束范围
    private String endTime1;   //  结束范围

    private Date currentDate;   // 当前天
    private String currentDate1;   // 当前天

    private Integer jobType;    //工种

    public AttendanceParamsVO() {
    }

    public AttendanceParamsVO(Integer startPage, Integer pageSize, String staffName, Integer orgId, List<Integer> staffIdOfList, Integer timeStandardId, Date startTime, Date endTime, Date currentDate, Integer jobType) {
        this.startPage = startPage;
        this.pageSize = pageSize;
        this.staffName = staffName;
        this.orgId = orgId;
        this.staffIdOfList = staffIdOfList;
        this.timeStandardId = timeStandardId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentDate = currentDate;
        this.jobType = jobType;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getCurrentDate1() {
        return currentDate1;
    }

    public void setCurrentDate1(String currentDate1) {
        this.currentDate1 = currentDate1;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public List<Integer> getStaffIdOfList() {
        return staffIdOfList;
    }

    public void setStaffIdOfList(List<Integer> staffIdOfList) {
        this.staffIdOfList = staffIdOfList;
    }

    public Integer getTimeStandardId() {
        return timeStandardId;
    }

    public void setTimeStandardId(Integer timeStandardId) {
        this.timeStandardId = timeStandardId;
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

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    @Override
    public String toString() {
        return "AttendanceParamsVO{" +
                "startPage=" + startPage +
                ", pageSize=" + pageSize +
                ", staffName='" + staffName + '\'' +
                ", orgId=" + orgId +
                ", staffIdOfList=" + staffIdOfList +
                ", timeStandardId=" + timeStandardId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", currentDate=" + currentDate +
                ", jobType=" + jobType +
                '}';
    }
}
