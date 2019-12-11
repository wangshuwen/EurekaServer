package com.cst.xinhe.persistence.model.elasticsearch;

import lombok.Data;

import java.util.Date;

@Data
public class EsAttendanceEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.attendance_id
     *
     * @mbg.generated
     */

    private String attendanceid;

    //后补
    private String staffname;
    private String stationname;
    private String jobname;
    private String deptname;
    private String timestandardname;
    private  Integer groupid;
    //下井时长
    private String timeLong;
    //每月下井总数
    private Integer inOreSum;
    //领导总人数
    private Integer leaderSum;



    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.base_station_id
     *
     * @mbg.generated
     */
    private Integer basestationid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.staff_id
     *
     * @mbg.generated
     */
    private Integer staffid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.start_time
     *
     * @mbg.generated
     */
    private Date starttime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.end_time
     *
     * @mbg.generated
     */
    private Date endtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.rule_id
     *
     * @mbg.generated
     */
    private Integer ruleid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.backup1
     *
     * @mbg.generated
     */
    private String backup1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.backup2
     *
     * @mbg.generated
     */
    private String backup2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.in_ore
     *
     * @mbg.generated
     */
    private Date inore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.out_ore
     *
     * @mbg.generated
     */
    private Date outore;
}
