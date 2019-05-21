package com.cst.xinhe.stationpartition.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.coordinate.Coordinate;
import com.cst.xinhe.persistence.model.warning_area.WarningArea;
import com.cst.xinhe.persistence.model.warning_area.WarningAreaRecord;
import com.cst.xinhe.persistence.vo.resp.WarningAreaVO;
import com.cst.xinhe.stationpartition.service.service.WarningAreaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/3/18/14:00
 */
@RequestMapping("warningArea/")
@RestController
@Api(value = "WarningAreaController", tags = {"区域划分接口"})
public class WarningAreaController {
    @Resource
    private WarningAreaService warningAreaService;


    @GetMapping("findCoordinateByAreaId")
    @ApiOperation(value = "根据区域id查找对应的坐标集合")
    public String findCoordinateByAreaId(
            @RequestParam(name ="areaId") Integer areaId) {
        List<Coordinate> list = warningAreaService.findCoordinateByAreaId(areaId);
        return ResultUtil.jsonToStringSuccess(list);
    }


    @GetMapping("findAreaInfoByType")
    @ApiOperation(value = "根据区域类型查找相应的区域信息(1重点区域，2限制区域）")
    public String findAreaInfoByType(
            @RequestParam(name ="type",required = false) Integer type,
            @RequestParam(name ="name",required = false) String name,
            @RequestParam(name ="areaId",required = false) Integer areaId,
            @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {

        Page page=warningAreaService.findAreaInfoByType(type,pageSize,startPage,name,areaId);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @PostMapping("addAreaInfo")
    @ApiOperation(value = "录入区域信息", notes = ".0")
    public String addAreaInfo(@RequestBody WarningArea warningArea) {

        warningArea.setCreateTime(new Date());
        Integer result=warningAreaService.addAreaInfo(warningArea);

        return result >0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.ADD_STATION_FAIL);
    }


    @PostMapping("addCoordinateList")
    @ApiOperation(value = "录入区域对应具体所有的点坐标信息", notes = ".0")
    public String addCoordinateList(@RequestBody List<Coordinate> list) {
        for (Coordinate coordinate : list) {
            if(coordinate.getWarningAreaId()==null){
                return ResultUtil.jsonToStringError(ResultEnum.ADD_AREA_FAIL);
            }
        }
        Integer result=warningAreaService.addCoordinateList(list);

        return result >0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.ADD_STATION_FAIL);
    }

    @ApiOperation(value = "删除区域信息，同时级联删除所有点坐标", notes = "通过ID删除")
    @DeleteMapping("deleteAreaInfo/{id}")
    public String deleteAreaInfo(@PathVariable("id") Integer id) {
        Integer result=warningAreaService.deleteAreaInfo(id);
        return result >0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.DELETE_STATION_ERROR);
    }


    @ApiOperation(value = "删除点坐标，把区域设置由已设置变为未设置", notes = "通过ID删除")
    @DeleteMapping("deleteCoordinate/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Integer result=warningAreaService.deleteCoordinate(id);
        return  ResultUtil.jsonToStringSuccess();
    }



    @ApiOperation(value = "修改区域信息")
    @PutMapping("updateAreaInfo")
    public String updateAreaInfo(@RequestBody WarningArea  warningArea){
        Integer result = warningAreaService.updateAreaInfo(warningArea);
        return result >0 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.BINDING_STATION_MOUTH_FAIL);
    }

    @ApiOperation(value = "修改某一区域信息的坐标点")
    @PostMapping("updateCoordinateList")
    public String updateCoordinate(@RequestBody List<Coordinate> list, @RequestParam(name ="areaId", required = false) Integer areaId){
            if(areaId==null){
             return   ResultUtil.jsonToStringError(ResultEnum.UPDATE_AREA_FAIL);
            }
        Integer result = warningAreaService.updateCoordinateList(list,areaId);
        return result >0 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.BINDING_STATION_MOUTH_FAIL);
    }


    @ApiOperation(value = "获取所有的区域，当前区域特殊标记")
    @GetMapping("getAllAreaInfo")
    public String getAllAreaInfo( @RequestParam(name ="areaId", required = false) Integer areaId){
        if(areaId==null){
            return   ResultUtil.jsonToStringError(ResultEnum.UPDATE_AREA_FAIL);
        }
        WarningAreaVO warningAreaVO = warningAreaService.getAllAreaInfo(areaId);
        return ResultUtil.jsonToStringSuccess(warningAreaVO);
    }

    @ApiOperation(value = "获取所有的区域")
    @GetMapping("getAllWarningArea")
    public String getAllWarningArea(){
        WarningAreaVO warningAreaVO = warningAreaService.getAll();
        return ResultUtil.jsonToStringSuccess(warningAreaVO);
    }


    //----------------------------------warning_area_record------------------------------------
    @GetMapping("findAreaRecordByAreaId")
    @ApiOperation(value = "根据区域id,查找正在该区域的人员信息")
    public String findAreaRecordByAreaId(
            @RequestParam(name ="areaId",required = false) Integer areaId,
            @RequestParam(name ="type",required = false) Integer type,
            @RequestParam(name ="staffName",required = false) String staffName,
            @RequestParam(name ="deptId",required = false) Integer deptId,
            @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {

        Page page=warningAreaService.findAreaRecordByAreaId(type,areaId,pageSize,startPage,staffName,deptId);
        PageInfo pageInfo = new PageInfo(page);
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }

    @GetMapping("findHistoryAreaRecord")
    @ApiOperation(value = "根据区域id,查找该区域的历史人员信息")
    public String findHistoryAreaRecord(
            @RequestParam(name ="areaId") Integer areaId,
            @RequestParam(name ="staffName",required = false) String staffName,
            @RequestParam(name ="deptId",required = false) Integer deptId,
            @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {

        Page page=warningAreaService.findHistoryAreaRecord(areaId,pageSize,startPage,staffName,deptId);
        PageInfo pageInfo = new PageInfo(page);
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }

    @GetMapping("findAreaRecordByStaffId")
    @ApiOperation(value = "根据员工id,查找该员工进入所有区域的历史信息")
    public String findAreaRecordByStaffId(
            @RequestParam(name ="type",required = false) Integer type ,
            @RequestParam(name ="staffId") Integer staffId,
            @RequestParam(name ="areaId",required = false) Integer areaId,
            @RequestParam(name = "limit", defaultValue = "12", required = false) Integer pageSize,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {

        Page page=warningAreaService.findAreaRecordByStaffId(type,areaId,pageSize,startPage,staffId);
        PageInfo pageInfo = new PageInfo(page);
        return ResultUtil.jsonToStringSuccess(pageInfo);
    }


    @PostMapping("addAreaRecord")
    @ApiOperation(value = "新增员工进入区域详细信息", notes = ".0")
    public String addAreaRecord(@RequestBody WarningAreaRecord warningAreaRecord) {
        warningAreaRecord.setInTime(new Date());
        Integer result=warningAreaService.addAreaRecord(warningAreaRecord);
        return result >0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "插入员工离开区域的时间")
    @PutMapping("updateOutTime")
    public String updateOutTime(@RequestParam(name ="staffId") Integer staffId){
        Integer result = warningAreaService.updateOutTime(staffId);
        return result >0 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @GetMapping("findStaffNumByType")
    @ApiOperation(value = "根据区域类型，查看该区域现在的总人数")
    public String findStaffNumByType(
            @RequestParam(name ="type",required = false) Integer type) {
       Integer count =warningAreaService.findStaffNumByType(type);
        return ResultUtil.jsonToStringSuccess(count);
    }




}
