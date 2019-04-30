package com.cst.xinhe.staffgroupterminal.service.server;

import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffOrganizationService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 10:46
 **/
@RestController
@RequestMapping("staff-group-terminal-service")
public class StaffControllerServer {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffJobService staffJobService;

    @Autowired
    private StaffOrganizationService staffOrganizationService;

    @GetMapping("findStaffIdByTerminalId")
    public Map<String, Object> findStaffIdByTerminalId(@RequestParam Integer terminalId){
        Map<String, Object> map = staffService.findStaffIdByTerminalId(terminalId);
        return map != null && !map.isEmpty()? map: null;
    }

    @GetMapping("selectStaffInfoByTerminalId")
    public Map<String, Object> selectStaffInfoByTerminalId(@RequestParam Integer terminalId){
        Map<String, Object> map = staffService.selectStaffInfoByTerminalId(terminalId);
        return map;
    }

    @GetMapping("findStaffById")
    public Staff findStaffById(@RequestParam Integer staffid){
        return staffService.findStaffById(staffid);
    }

    @GetMapping("findStaffByTimeStandardId")
    public List<Staff> findStaffByTimeStandardId(@RequestParam Integer item){
        return staffService.findStaffByTimeStandardId(item);
    }

    @GetMapping("findAllStaffByGroupId")
    public List<Integer> findAllStaffByGroupId(@RequestParam Integer deptId){
        return staffService.findAllStaffByGroupId(deptId);
    }

    @GetMapping("selectStaffListByJobType")
    public List<Staff> selectStaffListByJobType(@RequestParam Integer jobType){
        return staffService.selectStaffListByJobType(jobType);
    }

    @GetMapping("selectStaffByLikeName")
    public List<Staff> selectStaffByLikeName(String staffName){
        return staffService.selectStaffByLikeName(staffName);
    }

    @GetMapping("selectStaffJobByJobId")
    public StaffJob selectStaffJobByJobId(@RequestParam Integer jobId){
        return staffJobService.findJobById(jobId);
    }



}
