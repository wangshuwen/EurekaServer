package com.cst.xinhe.staffgroupterminal.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.staff.StaffJob;
import com.cst.xinhe.staffgroupterminal.service.service.StaffJobService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "通过参数jobName查询获取工作", notes = "获取所有工作类别")
    @GetMapping("staff/getStaffJobsByParams")
    public String getStaffJobsByParams(@RequestParam(name = "limit", defaultValue = "12",required = false)Integer pageSize
            , @RequestParam(name="page",defaultValue = "1", required = false)Integer startPage
            , @RequestParam(name = "jobName",required = false)String jobName) {
        PageInfo list = staffJobService.getStaffJobsByParams(pageSize,startPage,jobName);
        return list.getSize() > 0 ?ResultUtil.jsonToStringSuccess(list): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @ApiOperation(value = "添加工作类型", notes = "添加工作类型")
    @PostMapping("staff/addStaffJob")
    public String addStaffJob(@RequestBody StaffJob staffJob) {
        Integer i = staffJobService.addStaffJobs(staffJob);
        return i == 1?ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "删除工作类型", notes = "删除工作类型")
    @DeleteMapping("staff/delStaffJob/{jobId}")
    public String delStaffJob(@PathVariable Integer jobId) {
        Integer i = staffJobService.delStaffJob(jobId);
        return i == 1?ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "修改工作类型", notes = "修改工作类型")
    @PutMapping("staff/updateStaffJob")
    public String delStaffJob(@RequestBody StaffJob staffJob) {
        Integer i = staffJobService.updateJob(staffJob);
        return i == 1?ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }
}
