package com.cst.xinhe.staffgroupterminal.service.server;

import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffOrganizationService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffTerminalRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 10:46
 **/
@RestController
//@RequestMapping("staff-group-terminal-service")
public class StaffControllerServer {

    @Resource
    private StaffService staffService;

    @Resource
    private StaffJobService staffJobService;

    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Resource
    private StaffTerminalRelationService staffTerminalRelationService;

    @GetMapping("findStaffIdByTerminalId")
    public Map<String, Object> findStaffIdByTerminalId(@RequestParam("terminalId") Integer terminalId){
        Map<String, Object> map = staffService.findStaffIdByTerminalId(terminalId);
        return map != null && !map.isEmpty()? map: null;
    }

    @GetMapping("selectStaffInfoByTerminalId")
    public Map<String, Object> selectStaffInfoByTerminalId(@RequestParam("terminalId") Integer terminalId){
        Map<String, Object> map = staffService.selectStaffInfoByTerminalId(terminalId);
        return map;
    }

    @GetMapping("findStaffById")
    public Staff findStaffById(@RequestParam("staffid") Integer staffid){
        return staffService.findStaffById(staffid);
    }

    @GetMapping("findStaffByIds")
    public List<Map<String,Object>> findStaffByIds(@RequestParam("staffIds") List<Integer> staffIds){
        return staffService.findStaffByIds(staffIds);
    }

    @GetMapping("getDeptAndGroupNameByStaffId")
    public HashMap<String, Object> getDeptAndGroupNameByStaffId(@RequestParam("staffId") Integer staffId){
        return staffService.getDeptAndGroupNameByStaffId(staffId);
    }


    @GetMapping("findStaffByTimeStandardId")
    public List<Staff> findStaffByTimeStandardId(@RequestParam("item") Integer item){
        return staffService.findStaffByTimeStandardId(item);
    }
    @GetMapping("findStaffByTimeStandardIds")
    public Map<Integer, List<Staff>> findStaffByTimeStandardIds(@RequestParam("ids") Integer[] ids){
        return staffService.findStaffByTimeStandardIds(ids);
    }

    @GetMapping("findAllStaffByGroupId")
    public List<Integer> findAllStaffByGroupId(@RequestParam("deptId") Integer deptId){
        return staffService.findAllStaffByGroupId(deptId);
    }

    @GetMapping("findAllStaffByGroupIds")
    public List<Integer> findAllStaffByGroupIds(@RequestParam("deptIds") List<Integer> deptIds){
        return staffService.findAllStaffByGroupIds(deptIds);
    }

    @GetMapping("selectStaffListByJobType")
    public List<Staff> selectStaffListByJobType(@RequestParam("jobType") Integer jobType){
        return staffService.selectStaffListByJobType(jobType);
    }

    @GetMapping("selectStaffByLikeName")
    public List<Staff> selectStaffByLikeName(String staffName){
        return staffService.selectStaffByLikeName(staffName);
    }

    @GetMapping("selectStaffJobByJobId")
    public StaffJob selectStaffJobByJobId(@RequestParam("jobId") Integer jobId){
        return staffJobService.findJobById(jobId);
    }

    @GetMapping("findStaffNameByTerminalId")
    public GasWSRespVO findStaffNameByTerminalId(@RequestParam("terminalId") Integer terminalId){
        return staffService.findStaffNameByTerminalId(terminalId);
    }

    @GetMapping("findNewRelationByTerminalId")
    public StaffTerminalRelation findNewRelationByTerminalId(@RequestParam("uploadId") Integer uploadId){
        return staffTerminalRelationService.findNewRelationByTerminalId(uploadId);
    }

    @GetMapping("findStaffGroupAndDeptByStaffId")
    public Map<String, Object> findStaffGroupAndDeptByStaffId(@RequestParam("staffId") Integer staffId){
        return staffService.findStaffGroupAndDeptByStaffId(staffId);
    }

    @GetMapping("findNewRelationByStaffId")
    public StaffTerminalRelation findNewRelationByStaffId(@RequestParam("staffId") Integer staffId){
        return staffTerminalRelationService.findNewRelationByStaffId(staffId);
    }

    @GetMapping("findStaffNameAndGroupNameByStaffIds")
    public Map<Integer, List<Map<String, Object>>> findStaffNameAndGroupNameByStaffIds(@RequestParam List<Integer> list1){
        return staffService.findStaffNameAndGroupNameByStaffIds(list1);
    }

    @GetMapping("findGroupNameByStaffId")
    public Map<Integer,Map<String,Object>> findGroupNameByStaffId(@RequestParam Set<Integer> list1){
        return staffService.findGroupNameByIds(list1);
    }

    @GetMapping("findStaffIdByStaffType")
    public List<Integer> findStaffIdByStaffType(@RequestParam("staffType") Integer staffType){
        return staffService.findStaffIdByStaffType(staffType);
    }



}
