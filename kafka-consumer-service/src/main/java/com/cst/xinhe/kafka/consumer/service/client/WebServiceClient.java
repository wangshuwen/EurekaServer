package com.cst.xinhe.kafka.consumer.service.client;


import com.cst.xinhe.kafka.consumer.service.client.callback.WebServiceClientFallback;
import com.cst.xinhe.kafka.consumer.service.client.config.FeignConfig;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.StaffAttendanceRealRule;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import com.cst.xinhe.persistence.model.lack_electric.LackElectric;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.rang_setting.RangSetting;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.TerminalUpdateIp;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient(value = "web-service",
        configuration = FeignConfig.class,
        fallback = WebServiceClientFallback.class,
        url = "http://127.0.0.1:8781/")
public interface WebServiceClient {
    @GetMapping("findRangByType")
    List<RangSetting> findRangByType(@RequestParam("i") int i);

    @GetMapping("getWarnLevelSettingByGasLevelId")
    GasLevelVO getWarnLevelSettingByGasLevelId(@RequestParam("standardId") Integer standardId);

    @GetMapping("findRangUrlByLevelDataId")
    String findRangUrlByLevelDataId(@RequestParam("contrastParameter") int contrastParameter);
    @GetMapping("findFrequencyByStationId")
    double findFrequencyByStationId(@RequestParam("stationId") Integer stationId);

    @GetMapping("findBaseStationByNum")
    BaseStation findBaseStationByNum(@RequestParam("basestationid") Integer basestationid);

    @GetMapping("findBaseStationByType")
    Map<String, Object> findBaseStationByType(@RequestParam("i") int i);

    @GetMapping("getSonIdsById")
    List<Integer> getSonIdsById(@RequestParam("zoneId") Integer zoneId);

    @PostMapping("findBaseStationByZoneIds")
    List<BaseStation> findBaseStationByZoneIds(@RequestBody List<Integer> zoneIds);

    @PutMapping("updateByStationNumSelective")
    void updateByStationNumSelective(@RequestBody BaseStation baseStation);

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

    @GetMapping("findNewRelationByTerminalId")
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
    Long getLackElectricList();

    @GetMapping("getCountMalfunction")
    Map<String, Object> getCountMalfunction();

    @GetMapping("findGasInfoByStaffIdAndTerminalId")
    GasWSRespVO findGasInfoByStaffIdAndTerminalId(@RequestParam("terminalId") Integer terminalId);
    @PostMapping("insertChatMsgSelective")
    void insertChatMsgSelective(@RequestBody ChatMsg chatMsg);

    @PutMapping("updateChatMegStatusBySeqId")
    void updateChatMegStatusBySeqId(@RequestBody ChatMsg chatMsg);

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

    @PostMapping("getAttendanceStaffCount")
    Long getAttendanceStaffCount();

    @PostMapping("getUnAttendanceDept")
    Integer getUnAttendanceDept(@RequestParam("date") Date date);
}
