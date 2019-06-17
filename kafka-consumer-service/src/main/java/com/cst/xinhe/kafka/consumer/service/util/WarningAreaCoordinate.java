package com.cst.xinhe.kafka.consumer.service.util;


import com.cst.xinhe.persistence.dto.RssiInfo;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-17 09:46
 **/
public class WarningAreaCoordinate {

    private static WarningAreaCoordinate warningAreaCoordinate = new WarningAreaCoordinate();

    public static WarningAreaCoordinate getInstance(){
        try {
            return (WarningAreaCoordinate) warningAreaCoordinate.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new WarningAreaCoordinate();
    }

    private Integer warningAreaId;

    private List<Point2D.Double> doubleList;

    private String warningAreaName;

    private Integer warningAreaType;

    private double residenceTime;

    private Integer containNumber;

    private boolean result;

    public Integer getWarningAreaId() {
        return warningAreaId;
    }

    public void setWarningAreaId(Integer warningAreaId) {
        this.warningAreaId = warningAreaId;
    }

    public List<Point2D.Double> getDoubleList() {
        return doubleList;
    }

    public void setDoubleList(List<Point2D.Double> doubleList) {
        this.doubleList = doubleList;
    }

    public String getWarningAreaName() {
        return warningAreaName;
    }

    public void setWarningAreaName(String warningAreaName) {
        this.warningAreaName = warningAreaName;
    }

    public Integer getWarningAreaType() {
        return warningAreaType;
    }

    public void setWarningAreaType(Integer warningAreaType) {
        this.warningAreaType = warningAreaType;
    }

    public double getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(double residenceTime) {
        this.residenceTime = residenceTime;
    }

    public Integer getContainNumber() {
        return containNumber;
    }

    public void setContainNumber(Integer containNumber) {
        this.containNumber = containNumber;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
