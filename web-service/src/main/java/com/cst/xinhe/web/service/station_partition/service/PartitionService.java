package com.cst.xinhe.web.service.station_partition.service;



import com.cst.xinhe.persistence.model.t_partition.Partition;

import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/11/14:02
 */
public interface PartitionService {

    List<Partition> findPartition();

    Integer add(Partition partition);

    Integer update(Partition partition);

    Integer delete(Integer id);

    String geParentNamesById(Integer id);

    List<Integer> getSonIdsById(Integer id);

    Integer findRootPartition();
}
