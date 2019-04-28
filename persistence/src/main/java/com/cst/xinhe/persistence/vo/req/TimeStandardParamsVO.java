package com.cst.xinhe.persistence.vo.req;


import java.sql.Time;

/**
 * @program: demo
 * @description: 查询条件类
 * @author: lifeng
 * @create: 2019-01-24 11:37
 **/
public class TimeStandardParamsVO {
    private Integer pageSize;

    private Integer startPage;

    private Integer timeStandardName;

    private Time startTime;

    private Time endTime;

    public TimeStandardParamsVO() {
    }

    public TimeStandardParamsVO(Integer pageSize, Integer startPage, Integer timeStandardName, Time startTime, Time endTime) {
        this.pageSize = pageSize;
        this.startPage = startPage;
        this.timeStandardName = timeStandardName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getTimeStandardName() {
        return timeStandardName;
    }

    public void setTimeStandardName(Integer timeStandardName) {
        this.timeStandardName = timeStandardName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeStandardParamsVO{" +
                "pageSize=" + pageSize +
                ", startPage=" + startPage +
                ", timeStandardName=" + timeStandardName +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
