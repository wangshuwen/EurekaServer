package com.cst.xinhe.persistence.dto;


import java.io.Serializable;

/**
 * @ClassName GasInfo
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/26 19:46
 * @Vserion v0.0.1
 */

public class GasInfo implements Serializable,Cloneable {


    private static final long serialVersionUID = -2221447368205031802L;
    private static GasInfo gasInfo = new GasInfo();

    public static GasInfo getInstance(){
        try {
            return (GasInfo) gasInfo.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new GasInfo();
    }

    private String terminalId;

    private String terminalIp;

    private String stationId;

    private String stationIp;

    private String uploadTime;


    private double co;
    private int coFlag;
    private double co2;
    private int co2Flag;
    private double o2;
    private int o2Flag;
    private double ch4;
    private int ch4Flag;
    private double t;
    private int tFlag;
    private double h;
    private int hFlag;

    //后补
    private boolean isWarn;

    public boolean getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(boolean isWarn) {
        this.isWarn = isWarn;
    }

    public GasInfo() {
    }

    public GasInfo(String terminalId, String terminalIp, String stationId, String stationIp, String uploadTime, double co, int coFlag, double co2, int co2Flag, double o2, int o2Flag, double ch4, int ch4Flag, double t, int tFlag, double h, int hFlag) {
        this.terminalId = terminalId;
        this.terminalIp = terminalIp;
        this.stationId = stationId;
        this.stationIp = stationIp;
        this.uploadTime = uploadTime;
        this.co = co;
        this.coFlag = coFlag;
        this.co2 = co2;
        this.co2Flag = co2Flag;
        this.o2 = o2;
        this.o2Flag = o2Flag;
        this.ch4 = ch4;
        this.ch4Flag = ch4Flag;
        this.t = t;
        this.tFlag = tFlag;
        this.h = h;
        this.hFlag = hFlag;
    }


    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getO2() {
        return o2;
    }

    public void setO2(double o2) {
        this.o2 = o2;
    }

    public double getCh4() {
        return ch4;
    }

    public void setCh4(double ch4) {
        this.ch4 = ch4;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationIp() {
        return stationIp;
    }

    public void setStationIp(String stationIp) {
        this.stationIp = stationIp;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getCoFlag() {
        return coFlag;
    }

    public void setCoFlag(int coFlag) {
        this.coFlag = coFlag;
    }

    public int getCo2Flag() {
        return co2Flag;
    }

    public void setCo2Flag(int co2Flag) {
        this.co2Flag = co2Flag;
    }

    public int getO2Flag() {
        return o2Flag;
    }

    public void setO2Flag(int o2Flag) {
        this.o2Flag = o2Flag;
    }

    public int getCh4Flag() {
        return ch4Flag;
    }

    public void setCh4Flag(int ch4Flag) {
        this.ch4Flag = ch4Flag;
    }

    public int gettFlag() {
        return tFlag;
    }

    public void settFlag(int tFlag) {
        this.tFlag = tFlag;
    }

    public int gethFlag() {
        return hFlag;
    }

    public void sethFlag(int hFlag) {
        this.hFlag = hFlag;
    }

    @Override
    public String toString() {
        return "GasInfo{" +
                "terminalId='" + terminalId + '\'' +
                ", terminalIp='" + terminalIp + '\'' +
                ", stationId='" + stationId + '\'' +
                ", stationIp='" + stationIp + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", co=" + co +
                ", coFlag=" + coFlag +
                ", co2=" + co2 +
                ", co2Flag=" + co2Flag +
                ", o2=" + o2 +
                ", o2Flag=" + o2Flag +
                ", ch4=" + ch4 +
                ", ch4Flag=" + ch4Flag +
                ", t=" + t +
                ", tFlag=" + tFlag +
                ", h=" + h +
                ", hFlag=" + hFlag +
                '}';
    }
}
