package com.cst.xinhe.kafka.consumer.service.client;

import com.cst.xinhe.kafka.consumer.service.client.callback.StaffGroupTerminalServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(value = "staff-group-terminal-service",
        configuration = FeignConfig.class,
        fallback = StaffGroupTerminalServiceClientFallback.class)
//@RequestMapping(value = "staff-group-terminal-service")
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

    @GetMapping("findStaffByTimeStandardIds")
    Map<Integer, List<Staff>> findStaffByTimeStandardIds(@RequestParam Integer[] ids);

    @GetMapping("findStaffNameByTerminalId")
    GasWSRespVO findStaffNameByTerminalId(@RequestParam Integer terminalId);

    @GetMapping("findStaffNameByTerminalId")
    StaffTerminalRelation findNewRelationByTerminalId(@RequestParam Integer uploadId);

    @GetMapping("findStaffGroupAndDeptByStaffId")
    Map<String, Object> findStaffGroupAndDeptByStaffId(@RequestParam Integer staffId);

    @PutMapping("updateIpInfoByTerminalId")
    void updateIpInfoByTerminalId(@RequestBody TerminalUpdateIp terminalUpdateIp);

    @PutMapping("updateIpInfoByStationId")
    void updateIpInfoByStationId(@RequestBody TerminalUpdateIp terminalUpdateIp);

    @GetMapping("findNewRelationByStaffId")
    StaffTerminalRelation findNewRelationByStaffId(@RequestParam Integer staffId);

    @DeleteMapping("deleteLeLackElectricByLackElectric")
    void deleteLeLackElectricByLackElectric(@RequestBody LackElectric lackElectric);

    @PostMapping("addMalfunction")
    void addMalfunction(@RequestBody Malfunction malfunction);

    @PutMapping("updateLackElectric")
    void updateLackElectric(@RequestBody LackElectric lackElectric);

    @GetMapping("selectLackElectric")
    List<LackElectric> getLackElectricList();

    @GetMapping("getCountMalfunction")
    Map<String, Object> getCountMalfunction();
}