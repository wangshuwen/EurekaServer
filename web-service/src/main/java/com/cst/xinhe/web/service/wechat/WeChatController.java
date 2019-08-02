package com.cst.xinhe.web.service.wechat;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.common.utils.string.StringUtils;
import com.cst.xinhe.persistence.model.wechat.WxUser;
import com.cst.xinhe.persistence.vo.wechat.GetPwd;
import com.cst.xinhe.web.service.wechat.service.WeChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-07-04 13:35
 **/
@RequestMapping("wx")
@RestController
@Api(value = "WeChatController", tags = {"微信端操作接口"})
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册")
    public String weChatRegister(@RequestBody WxUser wxUser){
        if (null == wxUser){
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }
        String result = weChatService.register(wxUser);
        return result;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public String weChatRLogin(@RequestBody WxUser wxUser){
        if (StringUtils.isEmpty(wxUser.getWxUserPassword()) && StringUtils.isEmpty(wxUser.getWxUserAccount())){
            return ResultUtil.jsonToStringError(ResultEnum.REQUEST_DATA_IS_NULL);
        }
        String res =  weChatService.login(wxUser);
        return res;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public String weChatRLogout(@RequestParam String key){
        weChatService.logout(key);
        return ResultUtil.jsonToStringSuccess();
    }


    /**
     * 更新发送短信标记
     */
    @GetMapping("/updateSendMsgFlag")
    @ApiOperation("更新发送短信标记 0 不发，1 发")
    public String weChatUpdateSendMsgFlag(@RequestParam(name = "account") String key,@RequestParam Integer flag){
        Integer res = weChatService.updateSendMsgFlag(key,flag);
        return res == 1 ? ResultUtil.jsonToStringSuccess(): ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    /**
     * 获取个人信息
     * @param key
     * @return
     */
    @GetMapping("/getPersonInfo")
    @ApiOperation("获取个人信息")
    public String getPersonInfo(@RequestParam(name = "account")String key){
        String result = weChatService.getPersonInfo(key);
        return result;
    }

    @PostMapping("/forgetPassword")
    @ApiOperation("忘记个人密码")
    public String forgetPassword(@RequestBody GetPwd getPwd){
        String result = weChatService.forgetPassword(getPwd);
        return result;
    }


}
