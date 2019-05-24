package com.cst.xinhe.chatmessage.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.chatmessage.service.service.CallService;
import com.cst.xinhe.persistence.model.chat.ChatMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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


}
