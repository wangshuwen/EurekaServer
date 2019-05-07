package com.cst.xinhe.staffgroupterminal.service.server;

import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.staffgroupterminal.service.service.StaffOrganizationService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 10:46
 **/

@RestController
//@RequestMapping("staff-group-terminal-service/")
public class GroupControllerServer {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffOrganizationService staffOrganizationService;

    @GetMapping("getDeptNameByGroupId")
    public String getDeptNameByGroupId(@RequestParam Integer group_id){
        return staffOrganizationService.getDeptNameByGroupId(group_id);
    }

    @GetMapping("findSonIdsByDeptId")
    public List<Integer> findSonIdsByDeptId(@RequestParam Integer id){
        return staffOrganizationService.findSonIdsByDeptId(id);
    }

    @GetMapping("getOneSonByParent")
    public List<StaffOrganization> getOneSonByParent(@RequestParam int i){
        return staffOrganizationService.getOneSonByParent(i);
    }

    @GetMapping("findSonIdsByDeptName")
    public List<Integer> findSonIdsByDeptName(@RequestParam String keyWord){
        return staffOrganizationService.findSonIdsByDeptName(keyWord);
    }


}
