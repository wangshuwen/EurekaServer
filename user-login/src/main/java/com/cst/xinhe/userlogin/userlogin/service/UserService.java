package com.cst.xinhe.userlogin.userlogin.service;

import com.cst.xinhe.userlogin.userlogin.vo.UserLoginVOReq;
import com.cst.xinhe.userlogin.userlogin.vo.UserLoginVOResp;

import javax.servlet.http.HttpSession;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/6/17:30
 */
public interface UserService {
     UserLoginVOResp userLogin(UserLoginVOReq userLoginVOReq, HttpSession session);
}
