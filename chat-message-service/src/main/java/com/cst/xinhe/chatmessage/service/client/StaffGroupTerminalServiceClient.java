package com.cst.xinhe.chatmessage.service.client;

import com.cst.xinhe.chatmessage.service.client.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.chatmessage.service.client.config.FeignConfig;
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
        fallback = StaffGroupTerminalServiceClientFallback.class)
//@RequestMapping("staff-group-terminal-service/")
public interface StaffGroupTerminalServiceClient {

    @GetMapping("findStaffIdByTerminalId")
    Map<String, Object> findStaffIdByTerminalId(@RequestParam int terminalId);


    @GetMapping("findTerminalIdByIpAndPort")
    TerminalUpdateIp findTerminalIdByIpAndPort(@RequestParam String terminalIp, @RequestParam int port);

    @GetMapping("selectStaffInfoByTerminalId")
    Map<String, Object> selectStaffInfoByTerminalId(@RequestParam Integer terminalId);

    @GetMapping("getDeptAndGroupNameByStaffId")
    HashMap<String, Object> getDeptAndGroupNameByStaffId(@RequestParam Integer staffId);

    @GetMapping("findStaffById")
    Staff findStaffById(@RequestParam Integer staffid);

    @GetMapping("getDeptNameByGroupId")
    String getDeptNameByGroupId(@RequestParam Integer group_id);

    @GetMapping("getOneSonByParent")
    List<StaffOrganization> getOneSonByParent(@RequestParam int i);

    @GetMapping("findSonIdsByDeptId")
    List<Integer> findSonIdsByDeptId(@RequestParam Integer id);

    @GetMapping("findStaffByTimeStandardId")
    List<Staff> findStaffByTimeStandardId(@RequestParam Integer item);

    @GetMapping("findAllStaffByGroupId")
    List<Integer> findAllStaffByGroupId(@RequestParam Integer deptId);

    @GetMapping("selectStaffListByJobType")
    List<Staff> selectStaffListByJobType(@RequestParam Integer jobType);

    @GetMapping("selectStaffByLikeName")
    List<Staff> selectStaffByLikeName(@RequestParam String staffName);

    @GetMapping("selectStaffJobById")
    StaffJob selectStaffJobByJobId(@RequestParam Integer jobId);

    @GetMapping("selectStaffJobByIds")
    Map<Integer, List<Staff>> findStaffByTimeStandardIds(@RequestParam Integer[] ids);

    @GetMapping("selectStaffJobByIds")
    List<Integer> findSonIdsByDeptName(@RequestParam String keyWord);

    @GetMapping("findTerminalInfoByStaffId")
    Integer findTerminalInfoByStaffId(int parseInt);

    @GetMapping("selectTerminalIpInfoByTerminalId")
    Map<String, Object> selectTerminalIpInfoByTerminalId(Integer terminalId);
}
