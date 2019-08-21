package com.cst.xinhe.kafka.consumer.service.service.impl;


import com.cst.xinhe.kafka.consumer.service.consumer.TerminalInfoProcess1;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import com.cst.xinhe.kafka.consumer.service.util.ICheckPointWithPolygon;
import com.cst.xinhe.kafka.consumer.service.util.ObserverableOfPoint;
import com.cst.xinhe.persistence.dao.warning_area.WarningAreaMapper;
import com.cst.xinhe.persistence.dto.warning_area.WarningAreaCoordinate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-04-29 15:54
 **/
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService, ObserverableOfPoint {

    private List<ICheckPointWithPolygon> list;

//    CheckPointWithPolygon checkPointWithPolygon;
    public KafkaConsumerServiceImpl() {
        this.list = new ArrayList<>();
//        this.checkPointWithPolygon = SpringContextUtil.getBean(CheckPointWithPolygon.class);
//        register(this.checkPointWithPolygon);
    }

    private List<WarningAreaCoordinate> doubleList;

    @Resource
    private WarningAreaMapper warningAreaMapper;


    @Override
    public void removeCarSet(Integer staffId) {
        TerminalInfoProcess1.carSet.remove(staffId);
    }

    @Override
    public void removeOutPersonSet(Integer staffId) {
        TerminalInfoProcess1.outPersonSet.remove(staffId);
    }

    @Override
    public void removeLeaderSet(Integer staffId) {
        TerminalInfoProcess1.leaderSet.remove(staffId);
    }

    @Override
    public void removeStaffSet(Integer staffId) {
        TerminalInfoProcess1.staffSet.remove(staffId);
    }

//    @Override
//    public void pushRtPersonData() {
//        TerminalInfoProcess.pushRtPersonData();
//    }


    @Override
    public void updateWarningAreaInfo() {
        this.doubleList = warningAreaMapper.findWarningAreaInfo();
        notifyMsg();
    }

    @Override
    public void register(ICheckPointWithPolygon o) {
        list.add(o);
    }

    @Override
    public void remove(ICheckPointWithPolygon o) {
        if(!list.isEmpty())
            list.remove(o);
    }

    @Override
    public void notifyMsg() {
        for(int i = 0; i < list.size(); i++) {
            ICheckPointWithPolygon iCheckPointWithPolygon = list.get(i);
            iCheckPointWithPolygon.updateAreaInfo(doubleList);
        }
    }
}
