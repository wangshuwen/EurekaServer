package com.cst.xinhe.base.exception;


/**
 * @ClassName SystemException
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/19 12:21
 * @Vserion v0.0.1
 */

import com.cst.xinhe.base.enums.ResultEnum;

/**
 * 注意要继承自RuntimeException，底层RuntimeException继承了Exception,
 * spring框架只对抛出的异常是RuntimeException才会进行事务回滚，
 * 如果是抛出的是Exception，是不会进行事物回滚的
 */
public class SystemException extends RuntimeException {

    private Integer code;


    /**
     * 自定义异常代码
     *
     * @param code
     * @param msg
     */
    public SystemException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 事先定义好的，
     * 根据 ResultEnum定义
     *
     * @param resultEnum
     * @see ResultEnum
     */
    public SystemException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
