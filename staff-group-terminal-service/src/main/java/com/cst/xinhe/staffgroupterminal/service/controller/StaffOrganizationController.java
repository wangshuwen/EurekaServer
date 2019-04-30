package com.cst.xinhe.staffgroupterminal.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.staff.StaffOrganization;
import com.cst.xinhe.staffgroupterminal.service.service.StaffOrganizationService;
import com.cst.xinhe.staffgroupterminal.service.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:组织结构表
 * @Date 2019/1/9/10:16
 */
@RestController
@RequestMapping("staff/")
@Api(value = "StaffOrganizationController", tags = {"组织结构关系"})
public class StaffOrganizationController {
    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Resource
    private StaffService staffService;

    @GetMapping("findStaffOrganization")
    @ApiOperation(value = "获取员工组织结构", notes = "获取员工的所有组织结构z-tree")
    public String findStaffOrganization() {
        List<StaffOrganization> list= staffOrganizationService.findStaffOrganization();
        return ResultUtil.jsonToStringSuccess(list);
    }

    @PostMapping("addStaffOrganization")
    @ApiOperation(value = "新增组织结构子节点", notes = "新增树节点")
    public String addStaffOrganization(@RequestBody StaffOrganization staffOrganization) {
        Integer result=staffOrganizationService.add(staffOrganization);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @DeleteMapping("deleteStaffOrganization")
    @ApiOperation(value = "删除组织结构子节点", notes = "删除树节点")
    public String deleteStaffOrganization(@RequestParam Integer id) {
        List<Integer> orgIds = staffOrganizationService.findSonIdsByDeptId(id);
        if (orgIds != null && !orgIds.isEmpty()){
            int flag = 0;
            for (Integer item: orgIds){
                List<Integer> staffIds = staffService.findAllStaffByDeptId(item);
                if (staffIds != null && !staffIds.isEmpty()){
                    flag += staffIds.size();
                }
            }
            if (flag > 0){
                return ResultUtil.jsonToStringError(ResultEnum.DEL_ORG_FAIL);
            }
        }
        Integer result = staffOrganizationService.delete(id);
        return result >0 ? ResultUtil.jsonToStringSuccess(result) : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @PutMapping("updateStaffOrganization")
    @ApiOperation(value = "更新组织结构子节点", notes = "更新树节点")
    public String updateStaffOrganization(@RequestBody StaffOrganization staffOrganization) {
        Integer result=staffOrganizationService.update(staffOrganization);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

}
