package com.cst.xinhe.web.service.system.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.system.service.service.BackUpDatabasesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/6/11/16:44
 */

@RestController
@Api(value = "BackUpDatabasesController", tags = {"数据库备份和恢复"})
public class BackUpDatabasesController {

    @Autowired
    private BackUpDatabasesService backUpDatabasesService;

    @ApiOperation(value = "数据库备份")
    @GetMapping("backUp")
    public String backUp() {
        boolean backup = backUpDatabasesService.backup("192.168.1.182", "root", "123456", "d:\\", "2019-6-11", "zkxh_test1");
        if(backup){
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    @ApiOperation(value = "数据库恢复")
    @GetMapping("recover")
    public String recover() {
        boolean recover = backUpDatabasesService.recover("d:/2019-6-11.sql", "192.168.1.182", "zkxh_test1", "root", "123456");
        if(recover){
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.FAILED);

    }



}
