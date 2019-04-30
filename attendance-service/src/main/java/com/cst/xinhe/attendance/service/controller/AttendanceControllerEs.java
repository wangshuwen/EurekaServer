package com.cst.xinhe.attendance.service.controller;

import com.cst.xinhe.attendance.service.elasticsrearch.entity.EsAttendanceEntity;
import com.cst.xinhe.attendance.service.elasticsrearch.repository.AttendanceRepository;
import com.cst.xinhe.base.result.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/10/14:11
 */
@Controller
public class AttendanceControllerEs {
    @Resource
    AttendanceRepository attendanceRepository;

    @GetMapping("/getInfoByParams1111")
    @ApiOperation("多条件查询考勤信息")
    public String getAttendanceInfoByParams(
            @RequestParam(name = "page", defaultValue = "1", required = false)Integer startPage,
            @RequestParam(name = "limit", defaultValue = "10", required = false)Integer pageSize,
            @RequestParam(name = "staffName", required = false) String staffName,
            @RequestParam(name = "orgId", required = false) Integer orgId,
            @RequestParam(name = "timeStandardId", required = false) Integer timeStandardId,
            @RequestParam(name = "startTime", required = false) String startTime,
            @RequestParam(name = "endTime", required = false) String endTime,
            @RequestParam(name = "currentDate", required = false) String currentDate,
            @RequestParam(name = "jobType", required = false)Integer jobType
                                                                             ){
        Pageable pageable = new PageRequest(startPage, pageSize);
      //  Page<AttendanceEntity> all = attendanceRepository.findAll(pageable);

        Iterable<EsAttendanceEntity> repositoryAll = attendanceRepository.findAll();

        return  ResultUtil.jsonToStringSuccess(repositoryAll);
    }


}
