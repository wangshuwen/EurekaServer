package com.cst.xinhe.web.service.system.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.web.service.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springboot_demo
 * @description: 登录用户的参数设置的权限校验
 * @author: lifeng
 * @create: 2019-03-15 16:03
 **/
@RestController
@Api(value = "PermissionCheckController", tags = {"员工权限校验"})
public class PermissionCheckController {


    @Resource
    private UserService userService;

    @GetMapping("permissionCheck")
    @ApiOperation(value = "根据用户输入的的密码与原密码对比", notes = "")
    public String permissionCheck(@RequestParam("account") String account, @RequestParam("password") String password){
        boolean flag = userService.check(account,password);
        return flag ? ResultUtil.jsonToStringSuccess():ResultUtil.jsonToStringError(ResultEnum.PERMISSION_CHECK_FAIL);
    }


}
