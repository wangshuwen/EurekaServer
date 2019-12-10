package com.cst.xinhe.web.service.operation_log.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.operation_log.OperationLog;
import com.cst.xinhe.persistence.vo.req.StaffInfoVO;
import com.cst.xinhe.web.service.operation_log.service.OperationLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-10/9:34
 */
@RestController
@RequestMapping("operationLog/")
@Api(value = "OperationLogController", tags = {"操作记录"})
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    @ApiOperation(value = "查询操作记录", notes="")
    @GetMapping("getOperationLog")
    public String getOperationLog( @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                                      @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                      @RequestParam(name = "starTime",  required = false) String starTime,
                                      @RequestParam(name = "endTime",  required = false) String endTime) {

        Page page = operationLogService.getOperationLog(startPage,pageSize,starTime,endTime);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @PostMapping("addOperationLog")
    @ApiOperation(value = "操作记录录入", notes = "新增操作记录")
    public String addOperationLog(@RequestBody OperationLog operationLog) {
        if(operationLog==null)
            return ResultUtil.jsonToStringError(ResultEnum.FAILED);

        operationLog.setCreateTime(new Date());

        int res = operationLogService.addOperationLog(operationLog);

        return res == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);

    }


}
