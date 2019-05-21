package com.cst.xinhe.system.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.warn_level.LevelData;
import com.cst.xinhe.system.service.service.LevelDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: demo
 * @description: 等级信息接口类
 * @author: lifeng
 * @create: 2019-01-17 09:43
 **/
@RestController
@RequestMapping("/levelData/")
@Api(value = "LevelDataController", tags = {"气体等级管理"})
public class LevelDataController {

    @Resource
    private LevelDataService levelDataService;

    @ApiOperation(value = "根据所有参数获取等级数据", notes = "多条件查询,等级全部获取不做分页展示")
    @GetMapping("getDataByParams")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "id", value = "ID", required = false),
            @ApiImplicitParam(dataType = "java.lang.String", name = "name", value = "名称", required = false)
    })
    public String getAllLevelDataByParams(@RequestParam(name = "id", required = false) Integer id, @RequestParam(name = "name", required = false) String name){
        Map<String, Object> map = new HashMap<>();
        if (null != id && 0 != id){
            map.put("id", id);
        }
        if (null != name && !"".equals(name)){
            map.put("levelName", name);
        }
        List<LevelData> levelDataList = levelDataService.findLevelDataByParams(map);
        return levelDataList.size() > 0 ? ResultUtil.jsonToStringSuccess(levelDataList): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @ApiOperation(value = "删除等级信息根据ID", notes = "")
    @DeleteMapping("delete/{id}")
    public String deleteLevelDataById(@PathVariable Integer id){
        int result = levelDataService.deleteLevelDataById(id);
        return result == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "更新", notes = "")
    @PutMapping("update")
    public String modifyLevelData(@RequestBody LevelData levelData) {
        int result = levelDataService.modifyLevelData(levelData);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "添加", notes = "")
    @PostMapping("add")
    public String insertLevelData(@RequestBody LevelData levelData){
        //新增等级，默认绑定铃声id为9的铃声
        levelData.setRangSettingId(9);
        int result = levelDataService.insertLevelData(levelData);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }



}
