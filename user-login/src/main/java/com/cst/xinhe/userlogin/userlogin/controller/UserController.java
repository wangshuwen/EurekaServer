package com.cst.xinhe.userlogin.userlogin.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.userlogin.userlogin.config.WebSecurityConfig;
import com.cst.xinhe.userlogin.userlogin.service.UserService;
import com.cst.xinhe.userlogin.userlogin.vo.UserLoginVOReq;
import com.cst.xinhe.userlogin.userlogin.vo.UserLoginVOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/6/15:26
 */
@RestController
@Api(value = "LoginController", tags = {"系统用户登录操作接口"})
public class UserController {

    /*@GetMapping("/")
    public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String account, Model model) {
        model.addAttribute("name", account);
        return "index";
    }

   *//* @GetMapping("/login")
    public String login() {
        return "login";
    }*//*

    @PostMapping("/login")
    @ResponseBody
    public String loginPost(@RequestBody UserLoginVOReq user, HttpSession session) {
        String account = user.getAccount();
        String passWord = user.getPassWord();

        Map<String, Object> map = new HashMap<>();
        user.getPassWord();
        if (!"123456".equals(passWord)) {
            map.put("success", false);
            map.put("message", "密码错误");
            return  ResultUtil.jsonToStringError(ResultEnum.DATA_NOT_FOUND);
        }

        // 设置session
        session.setAttribute(WebSecurityConfig.SESSION_KEY, account);

        map.put("success", true);
        map.put("message", "登录成功");
        return ResultUtil.jsonToStringSuccess(map);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }*/



    //-----------------------------------------------------------------------
    //注入UserService
    @Resource
    UserService userService;


    /**
     * @description 用户登录接口
     * @date 10:02 2018/9/7
     * @param [user]
     * @auther lifeng
     * @return java.lang.String
     **/
    @ApiOperation(value = "系统用户登录方法接口", notes = "根据User对象的account ，passWord 参数进行登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "java.lang.String", name = "account", value = "账号信息", required = true),
            @ApiImplicitParam(dataType = "java.lang.String", name = "passWord", value = "密码信息", required = true)
    })
    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginVOReq user,HttpSession session) {
        UserLoginVOResp resp = userService.userLogin(user,session);

        return resp != null ? ResultUtil.jsonToStringSuccess(resp) : ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    /**
     * @param []
     * @return java.lang.String
     * @description 用户推出登录
     * @date 10:46 2018/9/7
     * @auther lifeng
     **/
    @ApiOperation(value = "系统用户退出登录接口")
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return ResultUtil.jsonToStringSuccess();
    }




}
