package com.cst.xinhe.persistence.dto.warning_area;


import com.cst.xinhe.persistence.vo.resp.CoordinateVO;

import java.util.Date;
import java.util.List;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-03-20 11:26
 **/
public class CoordinateDto {

    private Integer warningAreaId; //区域主键ID

    private String warningAreaName; //区域名称

    private String warningAreaDesc; //区域描述

    private Date createTime;    //创建时间

    private Integer warningAreaType;    //区域类型

    List<CoordinateVO> coordinateVOS;   //该区域所包含的所有点

    public Integer getWarningAreaId() {
        return warningAreaId;
    }

    public void setWarningAreaId(Integer warningAreaId) {
        this.warningAreaId = warningAreaId;
    }

    public String getWarningAreaName() {
        return warningAreaName;
    }

    public void setWarningAreaName(String warningAreaName) {
        this.warningAreaName = warningAreaName;
    }

    public String getWarningAreaDesc() {
        return warningAreaDesc;
    }

    public void setWarningAreaDesc(String warningAreaDesc) {
        this.warningAreaDesc = warningAreaDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWarningAreaType() {
        return warningAreaType;
    }

    public void setWarningAreaType(Integer warningAreaType) {
        this.warningAreaType = warningAreaType;
    }

    public List<CoordinateVO> getCoordinateVOS() {
        return coordinateVOS;
    }

    public void setCoordinateVOS(List<CoordinateVO> coordinateVOS) {
        this.coordinateVOS = coordinateVOS;
    }
}
