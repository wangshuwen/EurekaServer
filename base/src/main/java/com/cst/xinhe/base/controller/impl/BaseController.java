package com.cst.xinhe.base.controller.impl;

import com.cst.xinhe.base.controller.IBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @auther li
 * @date 2018/1/2-15:02
 */
@RestController
public abstract class BaseController implements IBaseController {

    protected Logger logger = null;

    public BaseController() {
        logger = LoggerFactory.getLogger(getClass().getName());
    }
    //降低代码耦合，防止异常信息进入客户显示
//    @ExceptionHandler(Exception.class)
//    public Result handleException(Exception e) {
//        e.printStackTrace();
//        return new Result(ResultEnum.FAILED.getCode(),ResultEnum.FAILED.getMsg());
//    }

}
