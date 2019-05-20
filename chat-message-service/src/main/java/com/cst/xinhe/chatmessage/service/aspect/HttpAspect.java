package com.cst.xinhe.chatmessage.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName HttpAspect
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/19 12:27
 * @Vserion v0.0.1
 */

@Aspect
@Component
public class HttpAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpAspect.class);


    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间

    @Pointcut("execution(public * com.cst.xinhe.*.controller.*.*(..))")
    public void log() {

    }



    @Before(value = "(@annotation(org.springframework.web.bind.annotation.GetMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.PostMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.PutMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        LOGGER.info("url={}", request.getRequestURL());
        //method
        LOGGER.info("method={}", request.getMethod());
        //ip
        LOGGER.info("id={}", request.getRemoteAddr());
        //class_method
        LOGGER.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        LOGGER.info("args={}", joinPoint.getArgs());
    }

//    @Around(value = "(@annotation(org.springframework.web.bind.annotation.GetMapping)) || " +
//            "(@annotation(org.springframework.web.bind.annotation.PostMapping)) || " +
//            "(@annotation(org.springframework.web.bind.annotation.PutMapping)) || " +
//            "(@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Result result = null;
//        try {
//
//        } catch (Exception e) {
//            return exceptionHandle.handle(e);
//        }
////        if(result == null){
////            return proceedingJoinPoint.proceed();
////        }
//
//            return result;
//
//    }

    @AfterReturning(value = "(@annotation(org.springframework.web.bind.annotation.GetMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.PostMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.PutMapping)) || " +
            "(@annotation(org.springframework.web.bind.annotation.DeleteMapping))", returning = "object")
    public void doAfterReturning(Object object) {
        if (null != object)
        LOGGER.info("response={}", object.toString());
    }
}
