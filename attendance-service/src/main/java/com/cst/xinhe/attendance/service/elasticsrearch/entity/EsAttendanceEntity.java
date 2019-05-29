package com.cst.xinhe.attendance.service.elasticsrearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "attendance", type = "doc")
public class EsAttendanceEntity {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column attendance.attendance_id
     *
     * @mbg.generated
     */
    @Id
    private Long attendanceid;

    //后补
    private String staffname;
    private String stationname;
    private String jobname;
    private String deptname;
    private String timestandardname;
    //下井时长
    private String timeLong;
    //每月下井总数
    private Integer inOreSum;

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
