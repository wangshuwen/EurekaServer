package com.cst.xinhe.kafka.consumer.service.util;

public interface ObserverableOfPoint {

    //添加
    void register(ICheckPointWithPolygon o);
    //移除
    void remove(ICheckPointWithPolygon o);
    //通知
    void notifyMsg();
}
