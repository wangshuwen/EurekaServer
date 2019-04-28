package com.cst.xinhe.persistence.vo.resp;


import com.cst.xinhe.persistence.dto.warning_area.CoordinateDto;

import java.util.List;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-03-20 11:24
 **/
public class WarningAreaVO {
    private CoordinateDto currentArea;

    private List<CoordinateDto> otherArea;

    public CoordinateDto getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(CoordinateDto currentArea) {
        this.currentArea = currentArea;
    }

    public List<CoordinateDto> getOtherArea() {
        return otherArea;
    }

    public void setOtherArea(List<CoordinateDto> otherArea) {
        this.otherArea = otherArea;
    }
}
