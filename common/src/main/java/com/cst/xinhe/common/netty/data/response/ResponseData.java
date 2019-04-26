package com.cst.xinhe.common.netty.data.response;



import com.cst.xinhe.common.netty.data.request.RequestData;

import java.io.Serializable;

/**
 * @ClassName ResponseData 返回 或请求 据体数据的 数据封装类
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/28 15:24
 * @Vserion v0.0.1
 */
public class ResponseData implements Serializable {

    private volatile static ResponseData responseData;

    public static ResponseData getResponseData(){
        if (null == responseData){
            synchronized (ResponseData.class){
                if (null == responseData){
                    responseData = new ResponseData();
                }
            }
        }
        return responseData;
    }

    private RequestData customMsg; //请求数据的据体内容

    private byte code;  //状态码 （暂时未用）


    public RequestData getCustomMsg() {
        return customMsg;
    }

    public void setCustomMsg(RequestData customMsg) {
        this.customMsg = customMsg;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "customMsg=" + customMsg +
                ", code=" + code +
                '}';
    }
}
