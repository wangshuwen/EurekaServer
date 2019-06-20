package com.cst.xinhe.gateway.config;

import com.cst.xinhe.base.enums.ResultEnum;
import com.cst.xinhe.base.exception.*;
import com.cst.xinhe.base.log.BaseLog;
import com.cst.xinhe.base.result.Result;
import com.cst.xinhe.base.result.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-20 17:14
 **/
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
        else {
            logger.error("[system error] " + exception);
            exception.printStackTrace();
            //  return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(),ResultEnum.UNKNOW_ERROR.getMsg());
            return ResultUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), exception.toString());
        }
    }

}
