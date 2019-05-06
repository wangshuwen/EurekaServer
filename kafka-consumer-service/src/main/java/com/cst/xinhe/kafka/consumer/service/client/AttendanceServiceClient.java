package com.cst.xinhe.kafka.consumer.service.client;


import com.cst.xinhe.kafka.consumer.service.client.callback.AttendanceServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
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
        fallback = AttendanceServiceClientFallback.class)
public interface AttendanceServiceClient {

    @GetMapping("getTimeStandardByStaffId")
    TimeStandardVO getTimeStandardByStaffId(@RequestParam Integer staffId);

    @GetMapping("findStaffAttendanceRealRuleById")
    StaffAttendanceRealRule findStaffAttendanceRealRuleById(@RequestParam Integer staffId);

    @PutMapping("updateStaffAttendanceRealRuleById")
    void updateStaffAttendanceRealRuleById(@RequestBody StaffAttendanceRealRule realRule);

    @PostMapping("addAttendance")
    void addAttendance(@RequestBody Attendance attendance);

    @GetMapping("findAttendanceByStaffIdAndEndTimeIsNull")
    Attendance findAttendanceByStaffIdAndEndTimeIsNull(@RequestParam Integer staffId);

    @PutMapping("updateAttendance")
    void updateAttendance(@RequestBody Attendance attendance);

    @GetMapping("getAttendanceStaff")
    List<HashMap<String, Object>> getAttendanceStaff(@RequestParam List<Integer> deptIds, @RequestParam String staffName);

    @PostMapping("getUnAttendanceDept")
    Integer getUnAttendanceDept(@RequestParam Date date, @RequestBody List<Integer> deptIds);
}