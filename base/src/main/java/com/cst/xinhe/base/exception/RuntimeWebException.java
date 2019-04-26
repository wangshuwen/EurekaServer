package com.cst.xinhe.base.exception;


@SuppressWarnings("serial")
public class RuntimeWebException extends RuntimeException {

    private Integer code;


    /**
     * 自定义异常代码
     *
     * @param code
     * @param msg
     */
    public RuntimeWebException(Integer code, String msg) {
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
    public RuntimeWebException(ErrorCode resultEnum) {
        super(resultEnum.getInfo());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
