package com.userservice.userservice.controller;

import com.userservice.userservice.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/4/30/16:20
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceDetail   userServiceDetail;
}
