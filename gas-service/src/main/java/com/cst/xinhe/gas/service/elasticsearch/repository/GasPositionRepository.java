package com.cst.xinhe.gas.service.elasticsearch.repository;

import com.cst.xinhe.gas.service.elasticsearch.entity.GasPositionEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-15 17:43
 **/
@Repository
public interface GasPositionRepository extends ElasticsearchRepository<GasPositionEntity,Long> {

}
