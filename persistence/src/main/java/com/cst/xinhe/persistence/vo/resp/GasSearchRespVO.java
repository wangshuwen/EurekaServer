package com.cst.xinhe.persistence.vo.resp;

/**
 * @ClassName GasSearchRespVO
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/21 16:12
 * @Vserion v0.0.1
 */

public class GasSearchRespVO {
    private double o2;
    private double o2_type;

    private double ch4;
    private double ch4_type;

    private double co;
    private double co_type;

    private double co2;
    private double co2_type;

    private double Temperature;
    private double Temperature_type;

    private double Humidity;
    private double Humidity_type;

    private String staffName;

    private String sequenceId;

    private String staffNumber;

    private String rt;
    private String createTime;


    //TODO 定位信息


    public double getO2_type() {
        return o2_type;
    }

    public void setO2_type(double o2_type) {
        this.o2_type = o2_type;
    }

    public double getCh4_type() {
        return ch4_type;
    }

    public void setCh4_type(double ch4_type) {
        this.ch4_type = ch4_type;
    }

    public double getCo_type() {
        return co_type;
    }

    public void setCo_type(double co_type) {
        this.co_type = co_type;
    }

    public double getCo2_type() {
        return co2_type;
    }

    public void setCo2_type(double co2_type) {
        this.co2_type = co2_type;
    }

    public double getTemperature_type() {
        return Temperature_type;
    }

    public void setTemperature_type(double temperature_type) {
        Temperature_type = temperature_type;
    }

    public double getHumidity_type() {
        return Humidity_type;
    }

    public void setHumidity_type(double humidity_type) {
        Humidity_type = humidity_type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public void setO2(double o2) {
        this.o2 = o2;
    }

    public double getO2() {
        return o2;
    }

    public double getCh4() {
        return ch4;
    }

    public void setCh4(double ch4) {
        this.ch4 = ch4;
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

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public double getHumidity() {
        return Humidity;
    }

    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }
}
