package com.cst.xinhe.gas.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.gas.service.client.WsPushServiceClient;
import com.cst.xinhe.gas.service.elasticsearch.service.GasPositionService;
import com.cst.xinhe.gas.service.service.GasInfoService;
import com.cst.xinhe.persistence.dao.rt_gas.GasPositionMapper;
import com.cst.xinhe.persistence.model.rt_gas.GasPosition;
import com.cst.xinhe.persistence.vo.resp.GasWSRespVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GasController
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/21 15:59
 * @Vserion v0.0.1
 */


@RequestMapping("gas/")
@RestController
@Api(value = "GasController", tags = {"气体信息操作接口"})
public class GasController {

    @Resource
    private GasInfoService gasInfoService;

    @Resource
    private GasPositionService gasPositionService;

    @Resource
    private WsPushServiceClient wsPushServiceClient;

    @ApiOperation(value = "根据 员工姓名 查询环境信息 接口", notes = "根据员工姓名的进行模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "gasFlag", value = "是否是警告气体", required = false),
            @ApiImplicitParam(dataType = "java.lang.String", name = "staffName", value = "员工姓名", required = false),
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "pageSize", value = "每个页面显示的数据行数", required = false),
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "startPage", value = "起始页", required = false)
    })
    @GetMapping("findRtGasInfoByStaffName")
    public String getRtGasInfoByStaffName(@RequestParam(required = false,defaultValue = "0",name = "gasFlag") Integer gasFlag,
            @RequestParam(required = false,name = "staffName",defaultValue = "") String staffName,
            @RequestParam(required = false, defaultValue = "8", name = "limit") Integer pageSize,
            @RequestParam(required = false, defaultValue = "1", name = "page") Integer startPage) {
        Page page = gasInfoService.findGasInfoByStaffName(gasFlag,staffName, startPage, pageSize);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo): ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }





    @ApiOperation(value = "获取最近的气体信息", notes = "解决数据开始空白问题")
    @GetMapping("getRecentlyGasInfo/{num}")
    public String getRecentlyGasInfo(@PathVariable(name = "num", required = false) Integer number) {
        if (null != number && 0 != number) {
            List<GasWSRespVO> list = gasInfoService.findGasInfoLastTenRecords(number);
            if (list != null && !list.isEmpty()){
                return ResultUtil.jsonToStringSuccess(list);
            }
        }
        return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }

    @ApiOperation(value = "根据员工id获取最近的一条气体信息", notes = "根据员工id获取最近的气体信息")
    @GetMapping("findRecentlyGasInfoByStaffId/{staffId}")
    public String findRecentlyGasInfoByStaffId(@PathVariable(name = "staffId") Integer staffId) {
        Map<String,Object> gasInfo= gasInfoService.findRecentlyGasInfoByStaffId(staffId);
       if (gasInfo != null && !gasInfo.isEmpty()){
           return ResultUtil.jsonToStringSuccess(gasInfo);
       }
        return ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @ApiOperation(value = "修改气体筛选条件静态变量zoneId和orgId的值", notes = "")
    @GetMapping("updateOrgIdAndDeptId")
    public String updateOrgIdAndDeptId() {
        System.out.println("修改气体ID");
//        WSServer.orgId = null;
//        WSServer.zoneId = null;
        wsPushServiceClient.setOrgIdIsNull();
//        wsPushServiceClient.setOrgId(null);
//        wsPushServiceClient.setZoneId(null);
        wsPushServiceClient.setZoneIdIsNull();
         return ResultUtil.jsonToStringSuccess();
    }


    //测试气体批量插入
    List<GasPosition> gasPositions = new ArrayList<>();
    @Resource
    private GasPositionMapper gasPositionMapper;
    @PostMapping("add")
    @ApiOperation(value = "录入基站信息", notes = ".0")
    public String addBaseStation(@RequestParam("sequenceId") Integer sequenceId) {
        GasPosition gp = new GasPosition();
        gp.setSequenceId(sequenceId);
        gasPositions.add(gp);
        if (gasPositions.size() > 2){
            gasPositionMapper.insertGasPositions(gasPositions);
            gasPositions.clear();
        }

        return ResultUtil.jsonToStringSuccess();
    }

//    @GetMapping("getWarningGasCount")
//    public String getWarningGasCount(){
//        Long count = gasPositionService.getWarningGasCount();
//        return ResultUtil.jsonToStringSuccess(count);
//    }
    @ApiOperation(value = "获取定位异常的信息，分页查询")
    @GetMapping("getEMsg")
    public String getEMsg(@RequestParam(name = "limit", defaultValue = "12",required = false)Integer pageSize
            , @RequestParam(name="page",defaultValue = "1", required = false)Integer startPage
            , @RequestParam(name = "staffName",required = false)String staffName
            , @RequestParam(name = "staffId",required = false)Integer staffId
            ,@RequestParam(name = "type",required = false,defaultValue = "0")Integer type){
        PageInfo<Map<String, Object>> pageInfo = gasPositionService.getEMsgList(pageSize,startPage,staffName,staffId,type);
        return pageInfo.getSize() > 0 ?ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


}
