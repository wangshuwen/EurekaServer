package com.cst.xinhe.kafka.consumer.service.service.impl;

import com.cst.xinhe.kafka.consumer.service.consumer.TerminalInfoProcess;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import org.springframework.stereotype.Service;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 15:54
 **/
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @Override
    public void removeCarSet(Integer staffId) {
        TerminalInfoProcess.carSet.remove(staffId);
    }

    @Override
    public void removeOutPersonSet(Integer staffId) {
        TerminalInfoProcess.outPersonSet.remove(staffId);
    }

    @Override
    public void removeLeaderSet(Integer staffId) {
        TerminalInfoProcess.leaderSet.remove(staffId);
    }

    @Override
    public void removeStaffSet(Integer staffId) {
        TerminalInfoProcess.staffSet.remove(staffId);
    }

//    @Override
//    public void pushRtPersonData() {
//        TerminalInfoProcess.pushRtPersonData();
//    }
}
