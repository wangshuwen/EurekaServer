package com.cst.xinhe.gateway.controller;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.result.ResultUtil;
import com.cst.xinhe.gateway.service.UserService;
import com.cst.xinhe.gateway.utils.RedisUtils;
import com.cst.xinhe.gateway.vo.LoginVO;
import com.cst.xinhe.persistence.vo.UserLoginVOReq;
import com.cst.xinhe.persistence.vo.UserLoginVOResp;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/5/7/11:37
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${redis.expireTime}")
    private Long expireTime;//token过期时间

    @PostMapping("/login")
    @ResponseBody
    public String login( LoginVO loginVO) {

        UserLoginVOReq user = new UserLoginVOReq();
        user.setAccount(loginVO.getAccount());
        user.setPassWord(loginVO.getPassWord());

        UserLoginVOResp resp = userService.userLogin(user);
        if(resp!=null){
            String token = TokenStorage(user.getAccount());
            resp.setToken(token);
            log.info("登录成功,存储token");
            return ResultUtil.jsonToStringSuccess(resp);
        }

        return  ResultUtil.jsonToStringError(ResultEnum.FAILED);
    }

    /**
     * @param
     * @return java.lang.String
     * @description 用户推出登录
     * @date 10:46 2018/9/7
     * @auther lifeng
     **/
    @GetMapping("/logout")
    public String logout() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //获取认证名称
        String AuthName =request.getHeader("Authorization");
        redisUtils.delete(AuthName);
        return ResultUtil.jsonToStringSuccess();
    }


    private String TokenStorage(String username) {
        //生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //存储redis里
        System.out.println("token-----------------"+token);
        redisUtils.set(token, username,expireTime);
        return token;
    }


    @GetMapping("/hi")
    @ResponseBody
    public String hi( )  {
        return  "hi  shuwen";
    }



}
