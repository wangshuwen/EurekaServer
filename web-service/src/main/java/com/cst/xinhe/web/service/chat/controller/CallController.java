package com.cst.xinhe.web.service.chat.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;

import com.cst.xinhe.persistence.model.chat.ChatMsg;

import com.cst.xinhe.web.service.chat.service.CallService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/10 17:43
 * @Vserion v0.0.1
 */
@RestController
public class CallController {

    @Resource
    private CallService callService;

    /**
     * @param [wavFile, staffId]
     * @return java.lang.String
     * @description 呼叫 对讲呼叫
     * @date 2018/10/24
     * @auther lifeng
     **/
    @PostMapping("staff/call")
    @ApiOperation(value = "对终端进行语音对讲", notes = "通过语音数据和矿下员工ID 进行语音对讲")
    public String callStaff(MultipartFile wavFile, Integer staffId) {
        if (null != wavFile && null != staffId) {
            try {
                ChatMsg res = callService.callStaffByStaffId(wavFile, staffId, 0);
                if (null != res){
                    return ResultUtil.jsonToStringSuccess(res);
                }
            }catch (Exception e){
                throw new RuntimeWebException(ErrorCode.SEND_VOICE_FAIL);
            }
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.SEND_VOICE_ERROR);
    }


    @ApiOperation(value = "根据人员Id对终端进行在线监检查", notes = "确定终端是否连接网络")
    @GetMapping("checkTerminalOnline")
    public String checkTerminalIsNotOnline(@RequestParam("staffId")Integer staffId) {
        boolean flag = callService.pingTerminal(staffId);
        if (flag) {
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.CHECK_ONLINE_FAIL);
    }

    @ApiOperation(value = "根据终端的ID对终端进行在线监检查", notes = "确定终端是否连接网络")
    @GetMapping("checkTerminalOnlineByTerminalNum")
    public String checkTerminalOnlineByTerminalNum(@RequestParam("terminalNum") Integer terminalNum) {
        boolean flag = callService.pingTerminalByTerminalNum(terminalNum);
        if (flag) {
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.CHECK_ONLINE_FAIL);
    }

    @ApiOperation(value = "获取紧急呼叫的信息，分页查询")
    @GetMapping("getECallList")
    public String getECallList(@RequestParam(name = "limit", defaultValue = "12",required = false)Integer pageSize
                                , @RequestParam(name="page",defaultValue = "1", required = false)Integer startPage
                                , @RequestParam(name = "staffName",required = false)String staffName
                                , @RequestParam(name = "staffId",required = false)Integer staffId){
        PageInfo<Map<String, Object>> pageInfo = callService.getECallList(pageSize,startPage,staffName,staffId);
        return pageInfo.getSize() > 0 ?ResultUtil.jsonToStringSuccess(pageInfo):ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
    }


}
