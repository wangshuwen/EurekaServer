package com.cst.xinhe.web.service.operation_log.service;

import com.cst.xinhe.persistence.model.operation_log.OperationLog;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-10/9:33
 */
public interface OperationLogService {
    int addOperationLog(OperationLog operationLog);

    Page getOperationLog(Integer startPage, Integer pageSize, String starTime, String endTime);
}
