package com.cst.xinhe.persistence.vo.req;

/**
 * @program: demo
 * @description: 基站绑定气体标准VO类
 * @author: lifeng
 * @create: 2019-01-21 11:36
 **/
public class BaseStationBindingStandardVO {

    private Integer standardId;

    private Integer[] baseStationIds;

    public BaseStationBindingStandardVO() {
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Integer[] getBaseStationIds() {
        return baseStationIds;
    }

    public void setBaseStationIds(Integer[] baseStationIds) {
        this.baseStationIds = baseStationIds;
    }
}
