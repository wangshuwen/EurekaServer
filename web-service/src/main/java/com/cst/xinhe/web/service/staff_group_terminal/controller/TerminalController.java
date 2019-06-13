package com.cst.xinhe.web.service.staff_group_terminal.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;
import com.cst.xinhe.persistence.model.terminal.StaffTerminal;
import com.cst.xinhe.web.service.staff_group_terminal.service.StaffTerminalRelationService;
import com.cst.xinhe.web.service.staff_group_terminal.service.TerminalService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TerminalController
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/25 11:19
 * @Vserion v0.0.1
 */
@RestController
@Api(value = "TerminalController", tags = {"终端操作接口"})
public class TerminalController {

    @Resource
    private TerminalService terminalService;

    @Resource
    private StaffTerminalRelationService staffTerminalRelationService;

    @GetMapping("terminal/checkExist")
    @ApiOperation(value = "检验终端ID是否存在", notes = "根据终端ID 判断是否已经存在是数据库中，保证terminalId 唯一,不存在返回成功200，存在返回提示信息")
    public String checkTerminalIdExist(@RequestParam(name = "terminalId") Integer terminalId) {
        boolean flag = terminalService.checkTerminalExist(terminalId);
        return flag ? ResultUtil.jsonToStringError(ResultEnum.TERMINAL_IS_EXISTS) : ResultUtil.jsonToStringSuccess();
    }

    @PutMapping("terminal/update")
    @ApiOperation(value = "更新终端信息", notes = "通过终端的ID更新终端信息，ID不可以更新，")
    public String updateTerminalInfo(@RequestBody StaffTerminal staffTerminal) {
        int result = terminalService.updateTerminalByTerminalId(staffTerminal);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    @GetMapping("terminal/sendGetBatteryCmd")
    public String sendGetRtBatteryCmd(@RequestParam(name = "terminalNum") Integer terminalNum) {
        terminalService.sendGetRtBatteryCmd(terminalNum);
        return ResultUtil.jsonToStringSuccess();
    }

    @GetMapping("terminal/getBattery")
    public String getRtBattery(@RequestParam(name = "terminalNum") Integer terminalNum) {
        Integer integer = terminalService.getRtBattery(terminalNum);
        return null != integer ? ResultUtil.jsonToStringSuccess(integer) : ResultUtil.jsonToStringError(ResultEnum.RT_SELECT_TERMINAL_BATTERY_FAIL);
    }

    @Transactional
    @ApiOperation(value = "终端信息删除", notes = "单个删除终端，")
    @DeleteMapping("terminal/delete/{terminalId}")
    public String deleteTerminalById(@PathVariable(name = "terminalId", required = true) Integer terminalId) {
        Integer[] ids = new Integer[1];
        ids[0] = terminalId;
        int result = terminalService.deleteTerminalByTerminalId(ids);
        return result == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @DeleteMapping("terminal/delete")
    @ApiOperation(value = "批量删除终端信息", notes = "参数为拼接数组形式，")
    public String deleteTerminalByIds(@RequestParam(name = "ids") Integer[] ids) {
        int len = ids.length;
        int result = terminalService.deleteTerminalByTerminalId(ids);
        return len == result ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @GetMapping("terminal/getTerminalByParams")
    @ApiOperation(value = "分页获取所有终端信息", notes = "分页获取所有终端信息，若终端ID传入为 null或 '' ,则认为获取所有终端信息，")
    public String findTerminalByParams(@RequestParam(name = "limit", defaultValue = "10", required = false) Integer pageSize
            , @RequestParam(name = "page", defaultValue = "1", required = false) Integer startPage
            , @RequestParam(name = "terminalId", required = false) Integer terminalId) {
        Page result = terminalService.findTerminalInfoByParams(startPage, pageSize, terminalId);
        PageInfo pageInfo = new PageInfo(result);
        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @Transactional
    @ApiOperation(value = "录入终端信息", notes = "录入终端信息")
    @PostMapping("terminal/addTerminal")
    public String insertTerminal(@RequestBody StaffTerminal staffTerminal) {
        staffTerminal.setCreateTime(new Date());
        int i = terminalService.addTerminal(staffTerminal);
        terminalService.insertToUpdataIpByTerminal(staffTerminal.getTerminalId());
        return i == 1 ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    /*
    [{终端ID=12312123, 型号版本编号=123123,
     硬件版本号=123123, 软件版本号=123123,
      一氧化碳描述=123123, 氧气描述=123123,
       甲烷描述=123123, 声音描述=12312,
        wifi描述=123123, 电池描述=123}]
     */

    /**
     * @description: 批量录入终端信息
     * @param: [list]
     * @return: java.lang.String
     * @author: lifeng
     * @date: 2019-04-02
     */
    @ApiOperation(value = "批量录入终端信息", notes = "录入终端信息")
    @PostMapping("terminal/addTerminals")
    public String insertTerminals(@RequestBody List<Map<String, Object>> list) {
        StaffTerminal staffTerminal;
        List<StaffTerminal> staffTerminals = new ArrayList<>();
        try {
            for (Map<String, Object> item : list) {
                staffTerminal = new StaffTerminal();
                Integer terminalId = null;
                if (item.containsKey("终端ID"))
                    terminalId = (Integer) item.get("终端ID");
                String version = "";
                if (item.containsKey("型号版本编号"))
                    version = item.get("型号版本编号").toString();
                String hardware = "";
                if (item.containsKey("硬件版本号"))
                    hardware = item.get("硬件版本号").toString();
                String coDesc = "";
                if (item.containsKey("一氧化碳描述"))
                    coDesc = item.get("一氧化碳描述").toString();
                String o2Desc = "";
                if (item.containsKey("氧气描述"))
                    o2Desc = item.get("氧气描述").toString();
                String ch4Desc = "";
                if (item.containsKey("甲烷描述"))
                    ch4Desc = item.get("甲烷描述").toString();
                String voiceDesc = "";
                if (item.containsKey("声音描述"))
                    voiceDesc = item.get("声音描述").toString();
                String wifiDesc = "";
                if (item.containsKey("wifi描述"))
                    wifiDesc = item.get("wifi描述").toString();
                String powerDesc = "";
                if (item.containsKey("电池描述"))
                    powerDesc = item.get("电池描述").toString();
                if (null != terminalId && terminalId != 0) {
                    staffTerminal.setTerminalId(terminalId);
                }
                staffTerminal.setSoftwareVersion(version);

                if ("".equals(powerDesc) || "".equals(wifiDesc) || "".equals(version) || "".equals(hardware)) {
                    return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
                }
                staffTerminal.setBattery(1);
                staffTerminal.setBatteryDesc(powerDesc);
                staffTerminal.setSoftwareVersion(version);
                staffTerminal.setHardwareVersion(hardware);
                staffTerminal.setWifi(1);
                staffTerminal.setWifiDesc(wifiDesc);

                if (!"".equals(coDesc))
                    staffTerminal.setCoDesc(coDesc);

                if (!"".equals(o2Desc))
                    staffTerminal.setO2(1);
                staffTerminal.setO2Desc(o2Desc);

                if (!"".equals(ch4Desc))
                    staffTerminal.setCh4(1);
                staffTerminal.setCh4Desc(ch4Desc);

                if (!"".equals(voiceDesc))
                    staffTerminal.setVoice(1);
                staffTerminal.setVoiceDesc(voiceDesc);
                staffTerminal.setCreateTime(new Date());

                staffTerminals.add(staffTerminal);
            }
        } catch (Exception e) {
            throw new RuntimeWebException(ErrorCode.DATA_TYPE_IS_ERROR);
        }

        int count = terminalService.addTerminals(staffTerminals);

        return count == list.size() ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    @ApiOperation(value = "获取所有终端", notes = "获取终端")
    @GetMapping("terminal/getAll")
    public String getAllTerminal() {
        List<StaffTerminal> list = terminalService.getAllTerminal();
        return list.size() > 0 ? ResultUtil.jsonToStringSuccess(list) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @ApiOperation(value = "获取所有未绑定终端", notes = "获取所有未绑定终端")
    @GetMapping("terminal/getNotBinDingTerminals")
    public String getNotBinDingTerminals(@RequestParam(name = "page") Integer startPage, @RequestParam(name = "limit") Integer pageSize, Integer terminalId) {

        Page<StaffTerminal> list = terminalService.getNotBinDingTerminals(startPage, pageSize, terminalId);

        PageInfo<StaffTerminal> pageInfo = new PageInfo<>(list);

        return pageInfo.getSize() > 0 ? ResultUtil.jsonToStringSuccess(pageInfo) : ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


    @ApiOperation(value = "解除绑定", notes = "如果staffId为空则认为是接触绑定，若不为空认为是解除绑定同时，绑定新的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "staffTerminalId", value = "终端数据库中的ID", required = true)
    })
    @PutMapping("terminal/unBind/{staffTerminalId}")
    public String unBind(@PathVariable(name = "staffTerminalId", required = true) Integer staffTerminalId) {
        boolean flag = false;

        if (staffTerminalId == null) {
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }
        StaffTerminalRelation staffTerminalRelation = new StaffTerminalRelation();
        //解除绑定
        staffTerminalRelation.setTerminalId(staffTerminalId);
        staffTerminalRelationService.updateRelationToOld(staffTerminalRelation);
        flag = terminalService.unBind(staffTerminalId);
        return flag ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }


    @ApiOperation(value = "绑定新终端", notes = "，，")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "staffId", value = "员工ID", required = true),
            @ApiImplicitParam(dataType = "java.lang.Integer", name = "staffTerminalId", value = "终端数据库中的ID", required = true)
    })
    @PutMapping("terminal/binding/{staffId}")
    public String binding(@PathVariable(name = "staffId", required = false) Integer staffId, @RequestParam(name = "staffTerminalId", required = true) Integer staffTerminalId) {
        boolean flag;
        if (staffId == null || staffTerminalId == null) {
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }
        StaffTerminalRelation staffTerminalRelation = new StaffTerminalRelation();
        staffTerminalRelation.setTerminalId(staffTerminalId);
        staffTerminalRelation.setStaffId(staffId);
        staffTerminalRelation.setType(1);
        flag = terminalService.binding(staffId, staffTerminalId);
        if (flag) {
            staffTerminalRelationService.insert(staffTerminalRelation);
        }
        return flag ? ResultUtil.jsonToStringSuccess() : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

}
