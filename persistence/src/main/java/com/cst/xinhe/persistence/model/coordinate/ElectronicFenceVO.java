package com.cst.xinhe.persistence.model.coordinate;

import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/3/19/17:38
 */
public class ElectronicFenceVO {

    private List<Coordinate> currentArea;
    List<List<Coordinate>> otherArea;

    public List<List<Coordinate>> getOtherArea() {
        return otherArea;
    }

    public void setOtherArea(List<List<Coordinate>> otherArea) {
        this.otherArea = otherArea;
    }

    public List<Coordinate> getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(List<Coordinate> currentArea) {
        this.currentArea = currentArea;
    }
}
