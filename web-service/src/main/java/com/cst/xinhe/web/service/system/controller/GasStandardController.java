package com.cst.xinhe.web.service.system.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.warn_level.GasStandard;
import com.cst.xinhe.web.service.system.service.LevelSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @program: demo
 * @description: 气体标准设置控制层
 * @author: lifeng
 * @create: 2019-01-18 18:08
 **/
@RestController
@Api(value = "GasStandardController", tags = {"气体标准等级管理"})
public class GasStandardController {

    @Resource
    private LevelSettingService levelSettingService;

    @ApiOperation(value = "获取所有的标准")
    @GetMapping("getGasStandardInfo")
    public String getGasStandardInfo() {
        List<GasStandard> standardList = levelSettingService.getStandardInfo();
        return standardList.size() > 0 ? ResultUtil.jsonToStringSuccess(standardList): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @ApiOperation(value = "添加标准")
    @PostMapping("addGasStandardInfo")
    public String addGasStandardInfo(@RequestBody GasStandard standard) {
        standard.setUserId(0);
        standard.setCreateTime(new Date());
        int result = levelSettingService.addStandard(standard);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.ADD_STANDARD_FAIL);
    }

    @ApiOperation(value = "删除标准")
    @DeleteMapping("deleteGasStandardInfo/{standardId}")
    public String deleteGasStandardInfo(@PathVariable(required = true) Integer standardId) {
        int result = levelSettingService.removeStandard(standardId);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.DELETE_STANDARD_FAIL);
    }

    @ApiOperation(value = "更改标准")
    @PutMapping("updateGasStandardInfo")
    public String updateGasStandardInfo(@RequestBody GasStandard gasStandard) {
        gasStandard.setUpdateTime(new Date());
        int result = levelSettingService.modifyStandard(gasStandard);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.UPDATE_STANDARD_FAIL);
    }
}
