package com.cst.xinhe.kafka.consumer.service.util;

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
