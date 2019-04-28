package com.cst.xinhe.persistence.dto.warn_level_setting;

/**
 * @program: demo
 * @description: 气体警报级别
 * @author: lifeng
 * @create: 2019-01-19 10:42
 **/
public class GasWarnSettingDto {

    private Integer levelDataId;

    private String levelName;

    private Integer gasWarnSettingId;


    private Integer gasType;


    private Integer warnLevelId;


    private Double multiple;


    private Double lowerLimit;


    private Double upperLimit;


    private Integer lowerContinuedTime;


    private Integer upperContinuedTime;


    private Integer standardId;

    public GasWarnSettingDto() {
    }

    public GasWarnSettingDto(Integer levelDataId, String levelName, Integer gasWarnSettingId, Integer gasType, Integer warnLevelId, Double multiple, Double lowerLimit, Double upperLimit, Integer lowerContinuedTime, Integer upperContinuedTime, Integer standardId) {
        this.levelDataId = levelDataId;
        this.levelName = levelName;
        this.gasWarnSettingId = gasWarnSettingId;
        this.gasType = gasType;
        this.warnLevelId = warnLevelId;
        this.multiple = multiple;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.lowerContinuedTime = lowerContinuedTime;
        this.upperContinuedTime = upperContinuedTime;
        this.standardId = standardId;
    }

    public Integer getLevelDataId() {
        return levelDataId;
    }

    public void setLevelDataId(Integer levelDataId) {
        this.levelDataId = levelDataId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getGasWarnSettingId() {
        return gasWarnSettingId;
    }

    public void setGasWarnSettingId(Integer gasWarnSettingId) {
        this.gasWarnSettingId = gasWarnSettingId;
    }

    public Integer getGasType() {
        return gasType;
    }

    public void setGasType(Integer gasType) {
        this.gasType = gasType;
    }

    public Integer getWarnLevelId() {
        return warnLevelId;
    }

    public void setWarnLevelId(Integer warnLevelId) {
        this.warnLevelId = warnLevelId;
    }

    public Double getMultiple() {
        return multiple;
    }

    public void setMultiple(Double multiple) {
        this.multiple = multiple;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Integer getLowerContinuedTime() {
        return lowerContinuedTime;
    }

    public void setLowerContinuedTime(Integer lowerContinuedTime) {
        this.lowerContinuedTime = lowerContinuedTime;
    }

    public Integer getUpperContinuedTime() {
        return upperContinuedTime;
    }

    public void setUpperContinuedTime(Integer upperContinuedTime) {
        this.upperContinuedTime = upperContinuedTime;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    @Override
    public String toString() {
        return "GasWarnSettingDto{" +
                "levelDataId=" + levelDataId +
                ", levelName='" + levelName + '\'' +
                ", gasWarnSettingId=" + gasWarnSettingId +
                ", gasType=" + gasType +
                ", warnLevelId=" + warnLevelId +
                ", multiple=" + multiple +
                ", lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                ", lowerContinuedTime=" + lowerContinuedTime +
                ", upperContinuedTime=" + upperContinuedTime +
                ", standardId=" + standardId +
                '}';
    }
}
