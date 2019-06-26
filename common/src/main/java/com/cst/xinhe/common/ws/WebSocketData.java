package com.cst.xinhe.common.ws;

import java.io.Serializable;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-28 09:35
 **/
public class WebSocketData implements Cloneable {

    private static WebSocketData webSocketData = new WebSocketData();

    public static WebSocketData getInstance(){
        try {
            return (WebSocketData) webSocketData.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new WebSocketData();
    }

    private Integer type;

    private Object data;



    public WebSocketData() {
    }

    public WebSocketData(Integer type,  Object data) {
        this.type = type;
        this.data = data;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "WebSocketData{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
