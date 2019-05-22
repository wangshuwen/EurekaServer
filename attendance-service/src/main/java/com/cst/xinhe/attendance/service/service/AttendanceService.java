package com.cst.xinhe.attendance.service.service;

import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.vo.req.AttendanceParamsVO;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.github.pagehelper.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/12/17/10:25
 */
public interface AttendanceService {

    int addTimeStandardInfo(TimeStandardVO standardVO) throws ParseException;

    int modifyTimeStandardInfo(TimeStandardVO timeStandard) throws ParseException;

    int deleteTimeStandard(Integer[] ids);

    Page getTimeStandardByParams(Map<String, Object> params);

    Integer addAttendance(Attendance attendance);

    Attendance findAttendanceByStaffIdAndEndTimeIsNull(Integer staffId);

    Integer updateAttendance(Attendance attendance);

    Page findAttendanceInfo(AttendanceParamsVO attendanceParamsVO);

    TimeStandardVO getTimeStandardByStaffId(Integer staffId);

    StaffAttendanceRealRule findRulesTimeByStaffId(Integer timeStandardId);

    List<HashMap<String,Object>> getAttendanceStaff(List<Integer> deptIds, String staffName);

    List<HashMap<String,Object>> getUnAttendanceStaff(List<Integer> deptIds, String staffName);

    List<HashMap<String,Object>> getOverTimeStaff(List<Integer> deptIds, String staffName);

    List<HashMap<String,Object>> getSeriousTimeStaff(List<Integer> deptIds, String staffName);


    Integer getUnAttendanceDept(List<Integer> deptIds);

    Integer getAttendanceDept(List<Integer> deptIds);

    Integer getOverTimeDept(List<Integer> deptIds);

    Integer getSeriousTimeDept(List<Integer> deptIds);

    StaffAttendanceRealRule findStaffAttendanceRealRuleById(Integer staffId);

    void updateStaffAttendanceRealRuleById(StaffAttendanceRealRule realRule);

    List<HashMap<String, Object>> getStaffAttendanceRealRuleMapperAttendanceStaff(List<Integer> deptIds, String staffName);

    Integer getStaffAttendanceRealRuleMapperUnAttendanceDept(Date date, List<Integer> deptIds);

    Long getAttendanceStaffCount(List<Integer> deptIds, String staffName);
}
