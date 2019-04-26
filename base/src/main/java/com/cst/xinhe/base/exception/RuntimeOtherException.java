package com.cst.xinhe.base.exception;


import com.cst.xinhe.base.enums.ResultEnum;

@SuppressWarnings("serial")
public class RuntimeOtherException extends RuntimeException {

    private Integer code;

    /**
     * 自定义异常代码
     *
     * @param code
     * @param msg
     */
    public RuntimeOtherException(Integer code, String msg) {
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
    public RuntimeOtherException(ResultEnum resultEnum) {
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
