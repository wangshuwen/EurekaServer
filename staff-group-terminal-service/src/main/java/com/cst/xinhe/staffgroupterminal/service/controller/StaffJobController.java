package com.cst.xinhe.staffgroupterminal.service.controller;

import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName StaffJobController
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/12 15:00
 * @Vserion v0.0.1
 */
@RestController
@Api(value = "StaffJobController", tags = {"工作类型操作接口"})
public class StaffJobController {


    @Resource
    private StaffJobService staffJobService;

    @ApiOperation(value = "获取所有工作类别", notes = "获取所有工作类别")
    @GetMapping("staff/getAllStaffJobs")
    public String getAllStaffJobs() {
        List<StaffJob> list = staffJobService.getAllJobs();
        return ResultUtil.jsonToStringSuccess(list);
    }
}
