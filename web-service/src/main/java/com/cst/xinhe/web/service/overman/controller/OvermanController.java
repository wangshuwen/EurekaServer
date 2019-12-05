package com.cst.xinhe.web.service.overman.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.web.service.overman.service.OvermanService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.cst.xinhe.persistence.model.overman_area.OvermanArea;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-05/9:13
 */
@RequestMapping("overman/")
@RestController
@Api(value = "OvermanController", tags = {"超员区域和超员人员"})
public class OvermanController {
    @Resource
    private OvermanService overmanService;

    @ApiOperation(value = "查询超员的区域信息", notes="")
    @GetMapping("getOvermanAreaInfo")
    public String getOvermanAreaInfo( @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                                      @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                      @RequestParam(name = "starOverTime",  required = false) String starOverTime,
                                      @RequestParam(name = "endOverTime",  required = false) String endOverTime,
                                      @RequestParam(name = "areaName", required = false) String areaName ) {

           Page page = overmanService.getOvermanAreaInfo(startPage,pageSize,starOverTime,endOverTime,areaName);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @ApiOperation(value = "根据超员的区域查询超员的员工信息", notes="")
    @GetMapping("getOvermanStaffInfo")
    public String getOvermanAreaInfo( @RequestParam(name = "startPage", defaultValue = "1", required = false) Integer startPage,
                                      @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                      @RequestParam(name = "staffName", required = false ) String staffName,
                                      @RequestParam(name = "overmanAreaId") Integer overmanAreaId
                                      ) {

        Page page = overmanService.getOvermanStaffInfo(startPage,pageSize,staffName,overmanAreaId);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }



}
