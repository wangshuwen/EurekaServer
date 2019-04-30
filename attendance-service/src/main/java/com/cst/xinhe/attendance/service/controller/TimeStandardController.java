package com.cst.xinhe.attendance.service.controller;

import com.cst.xinhe.attendance.service.client.StaffGroupTerminalServiceClient;
import com.cst.xinhe.attendance.service.service.AttendanceService;
import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.model.staff.Staff;
import com.cst.xinhe.persistence.vo.req.TimeStandardVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 时间标准web类
 * @author: lifeng
 * @create: 2019-01-24 09:37
 **/
@RestController
@RequestMapping("timeStandard")
@Api(value = "TimeStandardController", tags = "考勤时间规则类")
public class TimeStandardController {

    @Resource
    private AttendanceService attendanceService;

//    @Resource
//    private StaffService staffService;

    @Resource
    private StaffGroupTerminalServiceClient staffGroupTerminalServiceClient;

    @GetMapping("getInfo")
    @ApiOperation(value = "获取所有的时间标准", notes = "实现多参数多条件查询，可以分页查询")
    public String getAllTimeStandardInfoByParams(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer pageSize,
                                                 @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage,
                                                 @RequestParam(required = false) String timeStandardName,
                                                 @RequestParam(required = false) String startTime
                                                ) throws ParseException {

        Map<String, Object> params = new HashMap<>();
        if (null != startTime && !"".equals(startTime) && !"0".equals(startTime)) {
            Date date = DateConvert.convertStringToDate(startTime, 8);
            java.sql.Time date1 = new java.sql.Time(date.getTime());
            params.put("startTime",date1);
        }
        params.put("pageSize",pageSize);
        params.put("startPage", startPage);
        params.put("timeStandardName", timeStandardName);
        Page page = attendanceService.getTimeStandardByParams(params);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);

    }
    @PostMapping("addTimeStandard")
    public String addTimeStandard(@RequestBody TimeStandardVO standardVO) throws ParseException {

        standardVO.setUserId(0);
        standardVO.setCreateTime(new Date());
        int result = attendanceService.addTimeStandardInfo(standardVO);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.INSERT_TIME_STANDARD_FAIL);
    }

    @PutMapping("updateTimeStandard")
    public String modifyTimeStandard(@RequestBody TimeStandardVO timeStandard) throws ParseException {
        timeStandard.setUpdateTime(new Date());
        int result = attendanceService.modifyTimeStandardInfo(timeStandard);
       return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.UPDATE_TIME_STANDARD_FAIL);
    }

    @DeleteMapping("deleteTimeStandard")
    public String deleteTimeStandard(@RequestParam Integer[] ids){
        int len = ids.length;
        if (len > 0){
            for (Integer item: ids){
//                List<Staff> staffList = staffService.findStaffByTimeStandardId(item);
                List<Staff> staffList = staffGroupTerminalServiceClient.findStaffByTimeStandardId(item);
                if (staffList != null && !staffList.isEmpty()){
                    return ResultUtil.jsonToStringError(ResultEnum.DEL_TIME_STANDARD_FAIL);
                }
            }
            int result = attendanceService.deleteTimeStandard(ids);
            return len == result ?ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.DELETE_TIME_STANDARD_FAIL);
        }
       return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
    }


}
