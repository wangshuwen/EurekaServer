package com.cst.xinhe.chatmessage.service.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.ErrorCode;
import com.cst.xinhe.base.exception.RuntimeWebException;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.chatmessage.service.service.CallService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
//        Object sysUser = SecurityUtils.getSubject().getPrincipal();
//        ClazzUtil clazzUtil = new ClazzUtil();
//        Integer userId = (Integer) clazzUtil.getFieldValueByName("sysUserid", sysUser);
        if (wavFile != null && staffId != null) {
//           result = callService.callStaffByStaffId(wavFile,staffId,userId);
//           return result ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.SEND_VOICE_ERROR);
//            callService.callStaffByStaffId(wavFile, staffId, userId);
//            默认是0;
            try {
                String res = callService.callStaffByStaffId(wavFile, staffId, 0);
                if (res != null){
                    return ResultUtil.jsonToStringSuccess(res);
                }
            }catch (Exception e){
                throw new RuntimeWebException(ErrorCode.SEND_VOICE_FAIL);
            }
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.SEND_VOICE_ERROR);
    }


    @ApiOperation(value = "对终端进行在线监检查", notes = "确定终端是否连接网络")
    @GetMapping("checkTerminalOnline")
    public String checkTerminalIsNotOnline(Integer staffId) {
        boolean flag = callService.pingTerminal(staffId);
        if (flag) {
            return ResultUtil.jsonToStringSuccess();
        }
        return ResultUtil.jsonToStringError(ResultEnum.CHECK_ONLINE_FAIL);
    }


}
