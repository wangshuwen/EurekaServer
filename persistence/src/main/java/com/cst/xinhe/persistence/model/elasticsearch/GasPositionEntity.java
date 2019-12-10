package com.cst.xinhe.persistence.model.elasticsearch;

import lombok.Data;


import java.util.Date;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-10 09:26
 **/
@Data

public class GasPositionEntity {


    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private String staffname;



    private String gaspositionid;

    private Double co;

    private Integer counit;

    private Double ch4;

    private Integer ch4unit;

    private Double o2;

    private Integer o2unit;

    private Double co2;

    private Integer co2unit;

    private Double temperature;

    private Integer temperatureunit;

    private Double humidity;

    private Integer humidityunit;

    private Double field3;

    private Integer field3unit;

    private Integer staffid;

    private Integer terminalid;

    private Integer stationid;

    private String terminalip;

    private String stationip;

    private Date terminalrealtime;

    private Integer infotype;

    private Integer sequenceid;

    private Double positionx;

    private Double positiony;

    private Double positionz;

    private Integer stationid1;

    private Integer stationid2;

    private Double wifistrength1;

    private Double wifistrength2;

    private Date createtime;

    private String temppositionname;

    private Integer isore;

    private Integer gasFlag;

}
