package com.cst.xinhe.gas.service.client;


import com.cst.xinhe.gas.service.client.callback.AttendanceServiceClientFallback;
import com.cst.xinhe.gas.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@FeignClient(value = "attendance-service",
        configuration = FeignConfig.class,
        fallback = AttendanceServiceClientFallback.class,
        url = "http://192.168.1.106:8775/")
public interface AttendanceServiceClient {

    @GetMapping("getTimeStandardByStaffId")
    TimeStandardVO getTimeStandardByStaffId(@RequestParam("staffId") Integer staffId);

    @GetMapping("findStaffAttendanceRealRuleById")
    StaffAttendanceRealRule findStaffAttendanceRealRuleById(@RequestParam("staffId") Integer staffId);

    @PutMapping("updateStaffAttendanceRealRuleById")
    void updateStaffAttendanceRealRuleById(@RequestBody StaffAttendanceRealRule realRule);

    @PostMapping("addAttendance")
    void addAttendance(@RequestBody Attendance attendance);

    @GetMapping("findAttendanceByStaffIdAndEndTimeIsNull")
    Attendance findAttendanceByStaffIdAndEndTimeIsNull(@RequestParam("staffId") Integer staffId);

    @PutMapping("updateAttendance")
    void updateAttendance(@RequestBody Attendance attendance);

    @GetMapping("getAttendanceStaff")
    List<HashMap<String, Object>> getAttendanceStaff(@RequestParam("deptIds") List<Integer> deptIds, @RequestParam("staffName") String staffName);

    @PostMapping("getUnAttendanceDept")
    Integer getUnAttendanceDept(@RequestParam("date") Date date, @RequestBody List<Integer> deptIds);
}
