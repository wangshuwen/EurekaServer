package com.cst.xinhe.kafka.consumer.service.util;

import com.cst.xinhe.persistence.dto.warning_area.WarningAreaCoordinate;
import com.cst.xinhe.persistence.vo.resp.CoordinateVO;
import org.springframework.stereotype.Component;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-14 17:08
 **/
@Component
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
     *
     * @param coordinate
     * @param type
     * @return
     */
    public WarningAreaCoordinate judgeExistence(Point2D.Double coordinate) {
        WarningAreaCoordinate result = new WarningAreaCoordinate();


        for (WarningAreaCoordinate item : this.areaInfo) {
            boolean f = isPointInPolygon(coordinate, item.getDoubleList());
            result.setResult(f);
            result.setContainNumber(item.getContainNumber());
            result.setResidenceTime(item.getResidenceTime());
            result.setWarningAreaId(item.getWarningAreaId());
            result.setWarningAreaName(item.getWarningAreaName());
            result.setWarningAreaType(item.getWarningAreaType());
        }

        return result;
    }

    private boolean isPointInPolygon(Point2D.Double coordinate, List<CoordinateVO> doubleList) {

        int N = doubleList.size();
        //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        boolean boundOrVertex = true;
        //cross points count of x
        int intersectCount = 0;
        //浮点类型计算时候与0比较时候的容差
        double precision = 2e-10;
        //neighbour bound vertices
        Point2D.Double p1 = new Point2D.Double(), p2 = new Point2D.Double();
        //当前点
        Point2D.Double p = coordinate;

        //left vertex
        p1.x = doubleList.get(0).getX();
        p1.y = doubleList.get(0).getY();
//        p1 = doubleList.get(0);
        //check all rays
        for (int i = 1; i <= N; ++i) {
            if (p.equals(p1)) {
                //p is an vertex
                return boundOrVertex;
            }

            //right vertex
            p2.x = doubleList.get(i % N).getX();
            p2.y = doubleList.get(i % N).getY();
            //ray is outside of our interests
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {
                p1 = p2;
                //next ray left point
                continue;
            }

            //ray is crossing over by the algorithm (common part of)
            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {
                //x is before of ray
                if (p.y <= Math.max(p1.y, p2.y)) {
                    //overlies on a horizontal ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {
                        return boundOrVertex;
                    }
                    //ray is vertical
                    if (p1.y == p2.y) {
                        //overlies on a vertical ray
                        if (p1.y == p.y) {
                            return boundOrVertex;
                            //before ray
                        } else {
                            ++intersectCount;
                        }
                        //cross point on the left side
                    } else {
                        //cross point of y
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
                        //overlies on a ray
                        if (Math.abs(p.y - xinters) < precision) {
                            return boundOrVertex;
                        }

                        //before ray
                        if (p.y < xinters) {
                            ++intersectCount;
                        }
                    }
                }
                //special case when ray is crossing through the vertex
            } else {
                //p crossing over p2
                if (p.x == p2.x && p.y <= p2.y) {
                    //next vertex
                    Point2D.Double p3 = new Point2D.Double();
                    p3.x= doubleList.get((i + 1) % N).getX();
                    p3.y= doubleList.get((i + 1) % N).getY();
                    //p.x lies between p1.x & p3.x
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            //next ray left point
            p1 = p2;
        }

        //偶数在多边形外
        if (intersectCount % 2 == 0) {
            return false;
            //奇数在多边形内
        } else {
            return true;
        }

    }

}
