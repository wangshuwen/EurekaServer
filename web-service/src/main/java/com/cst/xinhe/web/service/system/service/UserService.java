package com.cst.xinhe.web.service.system.service;


import com.cst.xinhe.persistence.vo.UserLoginVOReq;
import com.cst.xinhe.persistence.vo.UserLoginVOResp;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/6/17:30
 */
public interface UserService {
     UserLoginVOResp userLogin(UserLoginVOReq userLoginVOReq);

    boolean check(String account, String password);
}
