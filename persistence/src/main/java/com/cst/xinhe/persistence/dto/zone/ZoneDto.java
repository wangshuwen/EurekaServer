package com.cst.xinhe.persistence.dto.zone;


import com.cst.xinhe.base.entity.BaseEntity;

/**
 * @ClassName ZoneDto
 * @Description
 * @Auther lifeng
 * @DATE 2018/11/23 10:00
 * @Vserion v0.0.1
 */

public class ZoneDto extends BaseEntity {


    private int areaNum;    //空间数

    public ZoneDto() {
    }

    public int getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(int areaNum) {
        this.areaNum = areaNum;
    }
}
