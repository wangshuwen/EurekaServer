package com.cst.xinhe.kafka.consumer.service.util;


import com.cst.xinhe.persistence.dto.warning_area.WarningAreaCoordinate;

import java.util.List;

/**
 * @program: EurekaServer
 * @description:
 * @author: lifeng
 * @create: 2019-06-17 09:30
 **/
public interface ICheckPointWithPolygon {

    void updateAreaInfo(List<WarningAreaCoordinate> doubleList);

}
