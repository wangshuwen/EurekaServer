package com.cst.xinhe.web.service.feign.client;

import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.web.service.feign.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.web.service.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(value = "staff-group-terminal-service",
        configuration = FeignConfig.class,
        fallback = StaffGroupTerminalServiceClientFallback.class,
        url = "http://127.0.0.1:8772/")
//@RequestMapping("staff-group-terminal-service")
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

    @GetMapping("getDeptNameByGroupIds")
    List<Map<String,Object>> getDeptNameByGroupIds(@RequestParam("group_ids") List<Integer> group_ids);

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

    @GetMapping("selectStationIpByStationId")
    Map<String, Object> selectStationIpByStationId(@RequestParam("stationId") Integer stationId);

    @GetMapping("findGroupNameByGroupIds")
    Map<Integer, String> findGroupNameByGroupIds(@RequestParam("setOfGroupId") Set<Integer> setOfGroupId);

    @GetMapping("findJobByJobId")
    Map<Integer, String> findJobByJobId(@RequestParam("setOfJobId") Set<Integer> setOfJobId);
}
