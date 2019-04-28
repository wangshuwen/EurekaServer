package com.cst.xinhe.persistence.dto;

import java.io.Serializable;

/**
 * @ClassName RssiInfo
 * @Description
 * @Auther lifeng
 * @DATE 2018/11/28 9:46
 * @Vserion v0.0.1
 */

public class RssiInfo implements Serializable,Cloneable {


    private static final long serialVersionUID = -2959517936409491229L;
    private static RssiInfo rssiInfo = new RssiInfo();

    public static RssiInfo getInstance(){
        try {
            return (RssiInfo) rssiInfo.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new RssiInfo();
    }

    private UpLoadGasDto upLoadGasDto;

    private Integer terminalId;

    private Integer stationId1;

    private double rssi1;

    private Integer stationId2;

    private double rssi2;

    public RssiInfo() {
    }

    public RssiInfo(UpLoadGasDto upLoadGasDto, Integer terminalId, Integer stationId1, double rssi1, Integer stationId2, double rssi2) {
        this.terminalId = terminalId;
        this.stationId1 = stationId1;
        this.rssi1 = rssi1;
        this.stationId2 = stationId2;
        this.rssi2 = rssi2;
        this.upLoadGasDto = upLoadGasDto;
    }


    public UpLoadGasDto getUpLoadGasDto() {
        return upLoadGasDto;
    }

    public void setUpLoadGasDto(UpLoadGasDto upLoadGasDto) {
        this.upLoadGasDto = upLoadGasDto;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }

    public Integer getStationId1() {
        return stationId1;
    }

    public void setStationId1(Integer stationId1) {
        this.stationId1 = stationId1;
    }

    public double getRssi1() {
        return rssi1;
    }

    public void setRssi1(double rssi1) {
        this.rssi1 = rssi1;
    }

    public Integer getStationId2() {
        return stationId2;
    }

    public void setStationId2(Integer stationId2) {
        this.stationId2 = stationId2;
    }

    public double getRssi2() {
        return rssi2;
    }

    public void setRssi2(double rssi2) {
        this.rssi2 = rssi2;
    }

    @Override
    public String toString() {
        return "RssiInfo{" +
                "stationId1=" + stationId1 +
                ", rssi1=" + rssi1 +
                ", stationId2=" + stationId2 +
                ", rssi2=" + rssi2 +
                '}';
    }
}
