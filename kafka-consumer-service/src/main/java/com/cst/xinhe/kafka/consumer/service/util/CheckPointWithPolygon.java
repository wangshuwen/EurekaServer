package com.cst.xinhe.kafka.consumer.service.util;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-14 17:08
 **/
public class CheckPointWithPolygon {

    public static List<Point2D.Double> areaInfo;


    /**
     * 判断坐标点是否在多边形区域内
     * coordinate 包括 x,y 坐标
     * type类型  1：重点区域，2：限制区域
     * 若坐标点存在多边形区域中返回true，否在返回false
     * @param coordinate
     * @param type
     * @return
     */
    public static boolean judgeExistence(Point2D.Double coordinate,int type){
        return false;
    }

}
