package com.cst.xinhe.staffgroupterminal.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.staffgroupterminal.service.service.LackElectricService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/2/13/17:38
 */
@RequestMapping("lackElectric/")
@RestController
@Api(value = "LackElectricController", tags = {"电量警报操作接口"})
public class LackElectricController {

    @Resource
    private LackElectricService lackElectricService;

    @GetMapping("findLackElectric")
    @ApiOperation(value = "获取电量警报信息", notes = "可以根据终端id和员工姓名进行筛选")
    public String findLackElectric(@RequestParam(name ="terminalId",required = false) Integer terminalId
                                    ,@RequestParam(name ="staffName",required = false) String staffName
                                    , @RequestParam(name = "limit", defaultValue = "10", required = false) Integer pageSize,
                                   @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage) {
        Page page = lackElectricService.findLackElectric(pageSize,startPage,terminalId,staffName);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @GetMapping("findIsReadCount")
    @ApiOperation(value = "获取缺电的数量", notes = "")
    public String findIsReadCount() {
        Integer count = lackElectricService.findLackElectricCount();
        return ResultUtil.jsonToStringSuccess(count);
    }


    @PutMapping("updateIsRead")
    @ApiOperation(value = "把所有未读更新为已读", notes = "isRead从0改为1")
    public String updateIsRead() {
        Integer result=lackElectricService.updateIsRead();
        return result > 0 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }






}
