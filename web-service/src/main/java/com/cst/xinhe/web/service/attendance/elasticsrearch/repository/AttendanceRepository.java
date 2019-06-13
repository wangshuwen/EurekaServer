package com.cst.xinhe.web.service.attendance.elasticsrearch.repository;


import com.cst.xinhe.web.service.attendance.elasticsrearch.entity.EsAttendanceEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-09 15:06
 **/
@Repository
public interface AttendanceRepository extends ElasticsearchRepository<EsAttendanceEntity,Long> {


}
