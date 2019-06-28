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



}
