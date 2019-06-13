package com.cst.xinhe.web.service.staff_group_terminal.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.malfunction.Malfunction;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.web.service.staff_group_terminal.service.MalfunctionService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffOrganizationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffService;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffTerminalRelationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2018/11/30/9:27
 */
@RestController
@Api(value = "MalfunctionController", tags = "终端故障")
@RequestMapping("malfunction/")
public class MalfunctionController {
    @Resource
    private MalfunctionService malfunctionService;
    @Resource
    private StaffService staffService;

    @Resource
    private StaffOrganizationService staffOrganizationService;

    @Resource
    private StaffTerminalRelationService staffTerminalRelationService;

    @GetMapping("findMalfunctionInfoIsRead")
    @ApiOperation(value = "获取所有故障信息", notes = "根据终端编号查找故障信息，没有终端编号则查找全部，做到分页查询")
    public String getMalfunctionInfoByStatus(@RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage,
                               @RequestParam(name = "limit", defaultValue = "10", required = false) Integer pageSize,
                               @RequestParam(name = "terminalId", defaultValue = "", required = false) Integer terminalId,
                               @RequestParam(name = "status", defaultValue = "", required = false) Integer status) {

        PageHelper.startPage(startPage, pageSize);
        List<Malfunction> list = malfunctionService.findMalfunctionInfoByStatus1();
        for (Malfunction malfunction : list) {
            //根据终端id获取员工id
            //去查找历史关联表
            StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findHistoryRelationById(malfunction.getTerminalId());
            if (staffTerminalRelation != null) {
                Integer staffId = staffTerminalRelation.getStaffId();
                //根据员工id获取员工姓名，分组名称和部门名称
                HashMap<String, Object> nameMap = staffService.getDeptAndGroupNameByStaffId(staffId);
                if (nameMap != null) {
                    malfunction.setStaffName((String) nameMap.get("staffName"));
                    // malfunction.setGroupName((String)nameMap.get("deptName"));
                    String deptName = staffOrganizationService.getDeptNameByGroupId((Integer) nameMap.get("groupId"));
                    malfunction.setDeptName(deptName);
                }
            }
        }
        PageInfo<Malfunction> info = new PageInfo<>(list);
        return info.getSize() > 0 ? ResultUtil.jsonToStringSuccess(info) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @GetMapping("findMalfunctionInfo")
    @ApiOperation(value = "获取所有故障信息", notes = "根据终端编号查找故障信息，没有终端编号则查找全部，做到分页查询")
    public String getMalfunctionInfo(@RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage,
                               @RequestParam(name = "limit", defaultValue = "10", required = false) Integer pageSize,
                               @RequestParam(name = "terminalId", defaultValue = "", required = false) Integer terminalId,
                               @RequestParam(name = "status", defaultValue = "", required = false) Integer status) {


        PageHelper.startPage(startPage, pageSize);
        List<Malfunction> list = malfunctionService.findMalfunctionInfo(status, terminalId);
        for (Malfunction malfunction : list) {
            //根据终端id获取员工id
            //去查找历史关联表
            StaffTerminalRelation staffTerminalRelation = staffTerminalRelationService.findHistoryRelationById(malfunction.getTerminalId());
            if (staffTerminalRelation != null) {
                Integer staffId = staffTerminalRelation.getStaffId();
                //根据员工id获取员工姓名，分组名称和部门名称
                HashMap<String, Object> nameMap = staffService.getDeptAndGroupNameByStaffId(staffId);
                if (nameMap != null) {
                    malfunction.setStaffName((String) nameMap.get("staffName"));
                    // malfunction.setGroupName((String)nameMap.get("deptName"));
                    String deptName = staffOrganizationService.getDeptNameByGroupId((Integer) nameMap.get("groupId"));
                    malfunction.setDeptName(deptName);
                }
            }
        }
        PageInfo<Malfunction> info = new PageInfo<>(list);
        return info.getSize() > 0 ? ResultUtil.jsonToStringSuccess(info) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @PostMapping("updateStatusById")
    @ApiOperation(value = "根据终端id修改终端故障状态")
    public String updateStatusById(@RequestParam("selfCheckId") Integer selfCheckId) {
        int result = malfunctionService.updateStatusById(selfCheckId);
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "获取自检未读，未处理的数量")
    @GetMapping("getMalfunctionCount")
    public String getCount() {
        Integer result = malfunctionService.getCount();
        return result != null ? ResultUtil.jsonToStringSuccess(result) : ResultUtil.jsonToStringError(ResultEnum.GET_MALFUNCTION_COUNT_FAIL);
    }


    @Transactional
    @ApiOperation(value = "删除终端故障信息", notes = "接口可以批量删除终端故障信息，也可单一删除，参数为List")
    @DeleteMapping("deleteMalfunctions")
    public String deleteStaffDeptByIds(@RequestParam(name = "ids") Integer[] ids) {
        int len = ids.length;
        if (ids.length > 0) {
            Integer res = malfunctionService.deleteMalfunctionByIds(ids);
            if (res == len) {
                return ResultUtil.jsonToStringSuccess();
            } else {
                return ResultUtil.jsonToStringError(ResultEnum.FAILED);
            }
        } else {
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }

    }
}
