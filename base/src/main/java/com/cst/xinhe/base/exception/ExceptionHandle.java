package com.cst.xinhe.base.exception;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.base.result.Result;
import com.cst.xinhe.base.result.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName ExceptionHandle
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/19 12:23
 * @Vserion v0.0.1
 */

@RestControllerAdvice
public class ExceptionHandle extends BaseLog {

    /**
     * 异常捕获
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception exception) {
        if (exception instanceof SystemException) {
            SystemException systemException = (SystemException) exception;
            systemException.printStackTrace();
            return ResultUtil.error(systemException.getCode(), systemException.getMessage());
        }
        if (exception instanceof RuntimeFunctionException) {
            RuntimeFunctionException systemException = (RuntimeFunctionException) exception;
            systemException.printStackTrace();
            return ResultUtil.error(systemException.getCode(), systemException.getMessage());
        }
        if (exception instanceof RuntimeOtherException) {
            RuntimeOtherException systemException = (RuntimeOtherException) exception;
            systemException.printStackTrace();
            return ResultUtil.error(systemException.getCode(), systemException.getMessage());
        }
        if (exception instanceof RuntimeServiceException) {
            RuntimeServiceException systemException = (RuntimeServiceException) exception;
            systemException.printStackTrace();
            return ResultUtil.error(systemException.getCode(), systemException.getMessage());
        }
        if (exception instanceof RuntimeWebException) {
            RuntimeWebException systemException = (RuntimeWebException) exception;
            systemException.printStackTrace();
            return ResultUtil.error(systemException.getCode(), systemException.getMessage());
        }
//        if (exception instanceof UnauthenticatedException) {
//            UnauthenticatedException systemException = (UnauthenticatedException) exception;
//
//            logger.error("" + systemException.getMessage());
//            return ResultUtil.error(ResultEnum.TOKEN_ERROR);
//        }
//        if (exception instanceof UnauthorizedException) {
//            UnauthorizedException systemException = (UnauthorizedException) exception;
//            systemException.printStackTrace();
//            return ResultUtil.error(ResultEnum.UN_ANTUORIZED);
//        }
//        if (exception instanceof LockedAccountException) {
//            LockedAccountException systemException = (LockedAccountException) exception;
//            systemException.printStackTrace();
//            return ResultUtil.error(ResultEnum.USER_IS_LOCKED);
//        }
//        if (exception instanceof IncorrectCredentialsException) {
//            IncorrectCredentialsException systemException = (IncorrectCredentialsException) exception;
//            //systemException.printStackTrace();
//            return ResultUtil.error(ResultEnum.PWD_IS_ERROR);
//        }
//
//        if (exception instanceof AuthenticationException) {
//            AuthenticationException systemException = (AuthenticationException) exception;
//            systemException.printStackTrace();
//            return ResultUtil.error(ResultEnum.USER_NOT_EXIST);
//        }
        else {
            logger.error("[system error] " + exception);
            exception.printStackTrace();
            //  return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(),ResultEnum.UNKNOW_ERROR.getMsg());
            return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), exception.toString());
        }
    }

}
