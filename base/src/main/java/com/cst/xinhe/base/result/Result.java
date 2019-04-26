package com.cst.xinhe.base.result;


import java.io.Serializable;

//@ApiModel(value = "通用数据返回格式说明")
public class Result<T> implements Serializable {

//    @ApiModelProperty(value = "返回结果状态码 200:成功;其他状态码:参照码表 ")
    private Integer code;
//    @ApiModelProperty(value = "返回状态信息描述")
    private String msg;
//    @ApiModelProperty(value = "返回据体数据信息")
    private T data;


    public Result() {

    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
