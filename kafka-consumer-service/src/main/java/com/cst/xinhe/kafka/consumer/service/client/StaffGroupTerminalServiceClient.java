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

    @GetMapping("findStaffNameByTerminalId")
    GasWSRespVO findStaffNameByTerminalId(@RequestParam("terminalId") Integer terminalId);

    @GetMapping("findStaffNameByTerminalId")
    StaffTerminalRelation findNewRelationByTerminalId(@RequestParam("uploadId") Integer uploadId);

    @GetMapping("findStaffGroupAndDeptByStaffId")
    Map<String, Object> findStaffGroupAndDeptByStaffId(@RequestParam("staffId") Integer staffId);

    @PutMapping("updateIpInfoByTerminalId")
    void updateIpInfoByTerminalId(@RequestBody TerminalUpdateIp terminalUpdateIp);

    @PutMapping("updateIpInfoByStationId")
    void updateIpInfoByStationId(@RequestBody TerminalUpdateIp terminalUpdateIp);

    @GetMapping("findNewRelationByStaffId")
    StaffTerminalRelation findNewRelationByStaffId(@RequestParam("staffId") Integer staffId);

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
