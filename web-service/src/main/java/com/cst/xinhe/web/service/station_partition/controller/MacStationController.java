package com.cst.xinhe.web.service.station_partition.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.mac_station.MacStation;
import com.cst.xinhe.web.service.station_partition.service.MacStationService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-17 11:06
 **/
@RestController
@Api(value = "MacStationController", tags = {"获取基站附近的终端数量接口"})
public class MacStationController {

    @Resource
    private MacStationService macStationService;

    @GetMapping("mac/getMacNum")
    @ApiOperation(value = "获取基站附近的终端数量接口")
    public String stationGetMacNum(){
        List<Map<String, Object>> mapList = macStationService.findMacNumber();
        return (mapList != null && !mapList.isEmpty()) ? ResultUtil.jsonToStringSuccess(mapList): ResultUtil.jsonToStringError(ResultEnum.STATION_FAILED_TO_ACQUIRE_NUMBER_OF_NEARBY_TERMINALS);
    }


    @GetMapping("mac/getPersonByStationId/{stationId}")
    public String getPersonDetailInfoByStationId(@PathVariable(name = "stationId") Integer stationId
            ,@RequestParam(name = "startPage") Integer startPage
            ,@RequestParam(name = "pageSize") Integer pageSize){
        PageInfo<MacStation> pageInfo = macStationService.findPersonInfoByStation(stationId,startPage,pageSize);
        return (null != pageInfo && !pageInfo.getList().isEmpty())? ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

}
