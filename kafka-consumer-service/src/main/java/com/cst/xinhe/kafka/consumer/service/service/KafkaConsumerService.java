package com.cst.xinhe.kafka.consumer.service.service;

public interface KafkaConsumerService {

    void removeCarSet(Integer staffId);

    void removeOutPersonSet(Integer staffId);

    void removeLeaderSet(Integer staffId);

    void removeStaffSet(Integer staffId);

//    void pushRtPersonData();
}
