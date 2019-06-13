package com.cst.xinhe.web.service.server;

import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.web.service.attendance.service.AttendanceService;
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

    @PostMapping("getAttendanceStaff")
    public List<HashMap<String, Object>> getAttendanceStaff(){
        return attendanceService.getAttendanceStaff(null,null);
    }
    @PostMapping("getAttendanceStaffCount")
    public Long getAttendanceStaffCount(){
        return attendanceService.getAttendanceStaffCount(null,null);
    }

    @PostMapping("getUnAttendanceDept")
    public Integer getUnAttendanceDept(@RequestParam Date date){
        return attendanceService.getStaffAttendanceRealRuleMapperUnAttendanceDept(date, null);
    }



}
