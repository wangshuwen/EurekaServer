package com.cst.xinhe.web.service.operation_log.service.impl;

import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.persistence.dao.operation_log.OperationLogMapper;
import com.cst.xinhe.persistence.model.operation_log.OperationLog;
import com.cst.xinhe.web.service.operation_log.service.OperationLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019-12-10/9:34
 */
@Service
public class OperationLogImpl  implements OperationLogService {
    @Resource
    private OperationLogMapper  operationLogMapper;

    @Override
    public int addOperationLog(OperationLog operationLog) {
        int result = operationLogMapper.insertSelective(operationLog);
        return result;
    }

    @Override
    public Page getOperationLog(Integer startPage, Integer pageSize, String starTime, String endTime) {

        Date starTime1=null;
        Date endTime1=null;

        try {
            if(starTime!=null&&endTime!=null&!"".equals(starTime)){
                starTime1 = DateConvert.convertStringToDate(starTime, 10);
                endTime1  = DateConvert.convertStringToDate(endTime, 10);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Page  page = PageHelper.startPage(startPage, pageSize);
        List<OperationLog> list=operationLogMapper.getOperationLog(starTime1,endTime1);
        return null;
    }
}
