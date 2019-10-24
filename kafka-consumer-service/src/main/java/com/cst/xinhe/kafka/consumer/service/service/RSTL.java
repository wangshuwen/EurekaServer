package com.cst.xinhe.kafka.consumer.service.service;


import com.cst.xinhe.kafka.consumer.service.util.LocationUtils;
import com.cst.xinhe.persistence.dao.base_station.BaseStationMapper;
import com.cst.xinhe.persistence.model.base_station.BaseStation;
import com.cst.xinhe.persistence.model.terminal_road.TerminalRoad;
import org.springframework.stereotype.Component;
import sensorHelper.Position;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-05-06 09:11
 **/
@Component
public class RSTL {

//    @Resource
//    private BaseStationService baseStationService;

    @Resource
    private BaseStationMapper baseStationMapper;

    public TerminalRoad locateConvert(int terminalId, int stationId1, int stationId2, double wifiStrength1, double wifiStrength2) {
        TerminalRoad teminalRoad = new TerminalRoad();
        teminalRoad.setCreatetime(new Date());
        teminalRoad.setStaffTerminalId(terminalId);
        teminalRoad.setStationId1(stationId1);
        teminalRoad.setStationId2(stationId2);
        teminalRoad.setWifiStrength1(wifiStrength1);
        teminalRoad.setWifiStrength2(wifiStrength2);

//        BaseStation station1 = baseStationService.findBaseStationByNum(stationId1);
//        BaseStation station1 = stationPartitionServiceClient.findBaseStationByNum(stationId1);
        BaseStation station1 = baseStationMapper.findBaseStationByNum(stationId1);
//        BaseStation station2 = baseStationService.findBaseStationByNum(stationId2);
//        BaseStation station2 = stationPartitionServiceClient.findBaseStationByNum(stationId2);
        BaseStation station2 = baseStationMapper.findBaseStationByNum(stationId2);

        if(station1==null||station2==null)
            return teminalRoad;

        double distance = 0D;
        double positionX1 = station1.getPositionX();
        double positionY1 = station1.getPositionY();

        double positionX2 = station2.getPositionX();
        double positionY2 = station2.getPositionY();

        //Position position = RSSI2.locationCal(positionX1, positionY1, positionX2, positionY2, wifiStrength1, wifiStrength2);
        Position position = sensorHelper.sensorHelper.locationCal(positionX1, positionY1, positionX2, positionY2, wifiStrength1, wifiStrength2);
        teminalRoad.setPositionX(position.getX());
        teminalRoad.setPositionY(position.getY());
        teminalRoad.setPositionZ(station1.getPositionZ());



        StringBuffer stringBuffer = new StringBuffer();
        /*stringBuffer.append(station1.getBaseStationName());
        stringBuffer.append("#").append(station2.getBaseStationName());
        stringBuffer.append("#");
        float a = (float) (distance - positionY1);
        stringBuffer.append(a);*/

        //计算两点之间的距离
        double distance1 = LocationUtils.getDistance(positionX1, positionY1, position.getX(), position.getY());
        double distance2 = LocationUtils.getDistance(positionX2, positionY2, position.getX(), position.getY());
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(distance1));
        stringBuffer.append("距离基站"+station1.getBaseStationNum()+"#"+df.format(distance1));
        stringBuffer.append("#距离基站"+station2.getBaseStationNum()+"#"+df.format(distance2));

        teminalRoad.setTempPositionName(stringBuffer.toString());
        return teminalRoad;
    }

}
