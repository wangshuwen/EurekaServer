package com.cst.xinhe.gateway.config;

import com.cst.xinhe.gateway.utils.RedisUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author wangshuwen
 * @Description:  验证登陆
 * @Date 2019/4/29/13:30
 */
@Component
public class MyFilter extends ZuulFilter {

    @Resource
    private RedisUtils redisUtils;

    private static Logger log= LoggerFactory.getLogger(MyFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }



    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();
        //获取认证名称
        String Authname =request.getHeader("Authorization");
        String token=null;
        if(Authname!=null&&!"".equals(Authname)){
            //用户请求时会在头部 Authorization 传给我之前存储的token, 我用来验证
            Authname= Authname.replace("Bearer ","");
            //获取redis存储的token
            if (redisUtils.hasKey(Authname)){
                //查询redis是否有token
                token= (String) redisUtils.get(Authname);
            }
        }
        //此处判断是否要拦截**************
        //过滤登录方法
        if(url.contains("/login")){
            return null;
        }
       /* //过滤datacenter微服务
        if(url.contains("/gsa/rest/")){
            if(!url.contains("/MenuSystemTree")){
                return null;
            }
        }*/
        //过滤es微服务
        /*if(url.contains("/gsa/tool/")) {
            return null;
        }*/
        //*******************开始拦截****************************
        log.info(String.format("%s  拦截的url: %s",request.getMethod(),url));
        //没有加认证token 就没有访问权限
        if(StringUtils.isBlank(Authname)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"code\":401,\"msg\":\"没有访问权限！\"}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        }else if(token==null){
            //token失效了
            //用户提供的token检测出和redis不一样
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"code\":401,\"msg\":\"令牌失效,请重新登录！\"}");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
        }


        return null;
    }
}