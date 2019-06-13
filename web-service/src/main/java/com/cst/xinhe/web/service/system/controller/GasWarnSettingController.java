package com.cst.xinhe.web.service.system.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.warn_level.GasWarnSetting;
import com.cst.xinhe.persistence.vo.resp.GasLevelVO;
import com.cst.xinhe.web.service.system.service.LevelSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: demo
 * @description: 气体等级信息设置
 * @author: lifeng
 * @create: 2019-01-19 08:55
 **/
@RestController
@Api(value = "GasWarnSettingController", tags = {"气体警报等级设置"})
public class GasWarnSettingController {

    @Resource
    LevelSettingService levelSettingService;

    @ApiOperation(value = "添加气体设置等级")
    @PostMapping("addWarnSetting")
    public String addWarnSetting(@RequestBody GasWarnSetting gasWarnSetting) {
        int result = levelSettingService.addWarnLevelSetting(gasWarnSetting);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "修改气体设置的等级")
    @PutMapping("updateWarnSetting")
    public String updateWarnSetting(@RequestBody GasWarnSetting gasWarnSetting) {
        int result = levelSettingService.modifyWarnLevelSetting(gasWarnSetting);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "删除气体设置的等级")
    @DeleteMapping("deleteWarnSetting/{gasLevelId}")
    public String deleteWarnSetting(@PathVariable Integer gasLevelId) {
        int result = levelSettingService.deleteWarnLevelSettingByGasLevelId(gasLevelId);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "获取当前标准的气体设置信息")
    @GetMapping("getWarnSettingByStandardId/{standardId}")
    public String getWarnSettingByStandardId(@PathVariable Integer standardId) {
        GasLevelVO gasLevelVO = levelSettingService.getWarnLevelSettingByGasLevelId(standardId);
        return null != gasLevelVO ? ResultUtil.jsonToStringSuccess(gasLevelVO): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

}
