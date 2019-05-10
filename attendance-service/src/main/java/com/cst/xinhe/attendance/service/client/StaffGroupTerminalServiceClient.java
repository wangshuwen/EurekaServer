package com.cst.xinhe.attendance.service.client;

import com.cst.xinhe.attendance.service.client.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.attendance.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(value = "staff-group-terminal-service",
        configuration = FeignConfig.class,
        fallback = StaffGroupTerminalServiceClientFallback.class,
        url = "http://127.0.0.1:8772/")
//@RequestMapping(value = "staff-group-terminal-service")
public interface StaffGroupTerminalServiceClient {

    @GetMapping("findStaffIdByTerminalId")
    Map<String, Object> findStaffIdByTerminalId(@RequestParam("terminalId") int terminalId);

    @GetMapping("findTerminalIdByIpAndPort")
    TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam("terminalIp") String terminalIp, @RequestParam("port") int port);

    @GetMapping("selectStaffInfoByTerminalId")
    Map<String, Object> selectStaffInfoByTerminalId(@RequestParam("terminalId") Integer terminalId);

    @GetMapping("getDeptAndGroupNameByStaffId")
    HashMap<String, Object> getDeptAndGroupNameByStaffId(@RequestParam("staffId") Integer staffId);

    @GetMapping("findStaffById")
    Staff findStaffById(@RequestParam("staffid") Integer staffid);

    @GetMapping("getDeptNameByGroupId")
    String getDeptNameByGroupId(@RequestParam("group_id") Integer group_id);

    @GetMapping("getOneSonByParent")
    List<StaffOrganization> getOneSonByParent(@RequestParam("i") int i);

    @GetMapping("findSonIdsByDeptId")
    List<Integer> findSonIdsByDeptId(@RequestParam("id") Integer id);

    @GetMapping("findStaffByTimeStandardId")
    List<Staff> findStaffByTimeStandardId(@RequestParam("item") Integer item);

    @GetMapping("findAllStaffByGroupId")
    List<Integer> findAllStaffByGroupId(@RequestParam("deptId") Integer deptId);

    @GetMapping("selectStaffListByJobType")
    List<Staff> selectStaffListByJobType(@RequestParam("jobType") Integer jobType);

    @GetMapping("selectStaffByLikeName")
    List<Staff> selectStaffByLikeName(@RequestParam("staffName") String staffName);

    @GetMapping("selectStaffJobById")
    StaffJob selectStaffJobByJobId(@RequestParam("jobId") Integer jobId);

    @GetMapping("findStaffByTimeStandardIds")
    Map<Integer, List<Staff>> findStaffByTimeStandardIds(@RequestParam("ids") Integer[] ids);

    @GetMapping("findStaffNameAndGroupNameByStaffIds")
    Map<Integer, List<Map<String, Object>>> findStaffNameAndGroupNameByStaffIds(@RequestParam("list1") List<Integer> list1);

    @GetMapping("findGroupNameByStaffId")
    Map<Integer,Map<String,Object>> findGroupNameByStaffId(@RequestParam("list1") List<Integer> list1);
}
