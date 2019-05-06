package com.cst.xinhe.attendance.service.server;

import com.cst.xinhe.attendance.service.service.AttendanceService;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-30 10:13
 **/
@RestController
public class AttendanceServiceControllerServer {

    @Resource
    private AttendanceService attendanceService;

    @GetMapping("getTimeStandardByStaffId")
    public TimeStandardVO getTimeStandardByStaffId(@RequestParam Integer staffId){
        return attendanceService.getTimeStandardByStaffId(staffId);
    }

    @GetMapping("findStaffAttendanceRealRuleById")
    public StaffAttendanceRealRule findStaffAttendanceRealRuleById(@RequestParam Integer staffId){
        return attendanceService.findStaffAttendanceRealRuleById(staffId);
    }

    @PutMapping("updateStaffAttendanceRealRuleById")
    public void updateStaffAttendanceRealRuleById(@RequestBody StaffAttendanceRealRule realRule){
        attendanceService.updateStaffAttendanceRealRuleById(realRule);
    }

    @PostMapping("addAttendance")
    public void addAttendance(@RequestBody Attendance attendance){
        attendanceService.addAttendance(attendance);
    }

    @GetMapping("findAttendanceByStaffIdAndEndTimeIsNull")
    public Attendance findAttendanceByStaffIdAndEndTimeIsNull(@RequestParam Integer staffId){
        return attendanceService.findAttendanceByStaffIdAndEndTimeIsNull(staffId);
    }


    @PutMapping("updateAttendance")
    public void updateAttendance(@RequestBody Attendance attendance){
        attendanceService.updateAttendance(attendance);
    }

    @GetMapping("getAttendanceStaff")
    public List<HashMap<String, Object>> getAttendanceStaff(@RequestParam List<Integer> deptIds, @RequestParam String staffName){
        return attendanceService.getAttendanceStaff(deptIds,staffName);
    }

    @PostMapping("getUnAttendanceDept")
    public Integer getUnAttendanceDept(@RequestParam Date date, @RequestBody List<Integer> deptIds){
        return attendanceService.getStaffAttendanceRealRuleMapperUnAttendanceDept(date, deptIds);
    }



}