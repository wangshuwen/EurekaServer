package com.cst.xinhe.attendance.service.elasticsrearch.service;

import com.cst.xinhe.attendance.service.elasticsrearch.entity.EsAttendanceEntity;
import com.cst.xinhe.persistence.vo.req.AttendanceParamsVO;
import org.springframework.data.domain.Page;

/**
 * @program: springboot_demo
 * @description:
 * @author: lifeng
 * @create: 2019-04-09 15:10
 **/
public interface EsAttendanceService {
    Page<EsAttendanceEntity> searchAttendanceByParams(AttendanceParamsVO attendanceParamsVO);

    Page<EsAttendanceEntity> searchAttendanceInfo(Integer startPage, Integer pageSize);

    org.springframework.data.domain.Page<EsAttendanceEntity> searchAttendanceByStaffType(Integer startPage, Integer pageSize, Integer staffType, String staffName, String currentDate);
}
