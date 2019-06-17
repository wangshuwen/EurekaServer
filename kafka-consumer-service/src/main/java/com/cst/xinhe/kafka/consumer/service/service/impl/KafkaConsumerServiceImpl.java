package com.cst.xinhe.kafka.consumer.service.service.impl;


import com.cst.xinhe.kafka.consumer.service.consumer.TerminalInfoProcess;
import com.cst.xinhe.kafka.consumer.service.context.SpringContextUtil;
import com.cst.xinhe.kafka.consumer.service.service.KafkaConsumerService;
import com.cst.xinhe.kafka.consumer.service.util.CheckPointWithPolygon;
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

    public KafkaConsumerServiceImpl(List<ICheckPointWithPolygon> list) {
        this.list = new ArrayList<>();
        register(SpringContextUtil.getBean(CheckPointWithPolygon.class));
    }

//    CheckPointWithPolygon checkPointWithPolygon;
    private List<WarningAreaCoordinate> doubleList;

    @Resource
    private WarningAreaMapper warningAreaMapper;


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
