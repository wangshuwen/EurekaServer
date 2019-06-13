package com.cst.xinhe.web.service.server;

import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffJobService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
//@RequestMapping("staff-group-terminal-service/")
public class GroupControllerServer {

    @Autowired
    private StaffService staffService;

    @Resource
    private StaffJobService staffJobService;

    @Autowired
    private StaffOrganizationService staffOrganizationService;

    @GetMapping("getDeptNameByGroupId")
    public String getDeptNameByGroupId(@RequestParam Integer group_id){
        return staffOrganizationService.getDeptNameByGroupId(group_id);
    }

    @GetMapping("getDeptNameByGroupIds")
    public List<Map<String,Object>> getDeptNameByGroupIds(@RequestParam("group_ids") List<Integer> group_ids){
        return staffOrganizationService.getDeptNameByGroupIds(group_ids);
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
    public List<Integer> findSonIdsByDeptName(@RequestParam("keyWord") String keyWord){
        return staffOrganizationService.findSonIdsByDeptName(keyWord);
    }

//    String groupName = staffOrganizationService.getDeptNameByGroupId(groupId);
//    String groupName = groupNames.get(groupId);
//                String jobName = staffJobService.findJobNameById(staffJobId);

    @GetMapping("findGroupNameByGroupIds")
    public Map<Integer, String> findGroupNameByGroupIds(@RequestParam("setOfGroupId") Set<Integer> setOfGroupId){
        Map<Integer, String > map = new HashMap<>();
        for (Integer item: setOfGroupId){
            String name = staffOrganizationService.getDeptNameByGroupId(item);
            map.put(item,name);
        }
        return map;
    }

    @GetMapping("findJobByJobId")
    public Map<Integer, String> findJobByJobId(@RequestParam("setOfJobId")Set<Integer> setOfJobId){
        Map<Integer, String > map = new HashMap<>();
        for (Integer item: setOfJobId){
            String name = staffJobService.findJobNameById(item);
            map.put(item,name);
        }
        return map;
    }

}
