package com.cst.xinhe.persistence.dto.zone;

/**
 * @ClassName ZoneInfoDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/12/3 15:50
 * @Vserion v0.0.1
 */

public class ZoneInfoDto {


    private Integer zoneId;
    private Integer zoneType;
    private String areaNumber;
    private String stationNum;
    private String zoneName;

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getZoneType() {
        return zoneType;
    }

    public void setZoneType(Integer zoneType) {
        this.zoneType = zoneType;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public String getStationNum() {
        return stationNum;
    }

    public void setStationNum(String stationNum) {
        this.stationNum = stationNum;
    }
}
