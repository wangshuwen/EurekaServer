package com.cst.xinhe.terminal.monitor.server.utils;

/**
 * @program: Eureka-Server
 * @description:
 * @author: lifeng
 * @create: 2019-10-24 17:16
 **/
public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两坐标点的距离
     *

     * @return 距离
     */
    public static double getDistance(double x1, double y1, double x2,
                                     double y2) {
        double distance = Math.sqrt(Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
        return distance;
    }

    public static void main(String[] args) {
        double distance = getDistance(0, 0,
                3, 4);
        System.out.println("距离" + distance  + "公里");
    }



}
