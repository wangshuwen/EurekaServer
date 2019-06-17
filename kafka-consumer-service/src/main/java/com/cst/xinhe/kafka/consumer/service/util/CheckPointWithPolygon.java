package com.cst.xinhe.kafka.consumer.service.util;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-14 17:08
 **/
public class CheckPointWithPolygon implements ICheckPointWithPolygon {

    public List<WarningAreaCoordinate> areaInfo; //限制区域集合

    @Override
    public void updateAreaInfo(List<WarningAreaCoordinate> doubleList) {
        this.areaInfo = doubleList;
    }


    /**
     * 判断坐标点是否在多边形区域内
     * coordinate 包括 x,y 坐标
     * type类型  1：重点区域，2：限制区域
     * map: {info: 'areaInfo', result: 'true?false'}
     * 若坐标点存在多边形区域中返回true，否在返回false
     * @param coordinate
     * @param type
     * @return
     */
    public WarningAreaCoordinate judgeExistence(Point2D.Double coordinate, int type){
        WarningAreaCoordinate result = new WarningAreaCoordinate();
       return result;
    }

}
