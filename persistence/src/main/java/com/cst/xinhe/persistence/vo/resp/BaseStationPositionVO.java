package com.cst.xinhe.persistence.vo.resp;

/**
 * @ClassName BaseStationPostionVO
 * @Description
 * @Auther lifeng
 * @DATE 2018/10/30 10:21
 * @Vserion v0.0.1
 */

public class BaseStationPositionVO {

    //基站ID
    private Integer baseStationId;
    //基站编号
    private Integer baseStationNum;
    //位置坐标X
    private double positionX;
    //位置坐标Y
    private double positionY;
    //位置坐标Z
    private double positionZ;

    //基站名称
    private String baseStationName;

    public String getBaseStationName() {
        return baseStationName;
    }

    public void setBaseStationName(String baseStationName) {
        this.baseStationName = baseStationName;
    }

    public Integer getBaseStationId() {
        return baseStationId;
    }

    public void setBaseStationId(Integer baseStationId) {
        this.baseStationId = baseStationId;
    }

    public Integer getBaseStationNum() {
        return baseStationNum;
    }

    public void setBaseStationNum(Integer baseStationNum) {
        this.baseStationNum = baseStationNum;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(double positionZ) {
        this.positionZ = positionZ;
    }
}
