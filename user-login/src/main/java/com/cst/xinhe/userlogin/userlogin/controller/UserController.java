package com.cst.xinhe.userlogin.userlogin.controller;


import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/6/15:26
 */
@RestController
@Api(value = "LoginController", tags = {"系统用户登录操作接口"})
public class UserController {

    @GetMapping("/hi")
    public String hi(){
        return "hello shuwen";
    }

}
