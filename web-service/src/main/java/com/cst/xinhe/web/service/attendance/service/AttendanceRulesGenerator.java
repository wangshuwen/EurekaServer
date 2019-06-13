package com.cst.xinhe.web.service.attendance.service;

import java.text.ParseException;
import java.util.Date;

/**
 * @program: demo
 * @description: 考勤具体规则生成
 * @author: lifeng
 * @create: 2019-02-14 15:00
 **/
public interface AttendanceRulesGenerator {

    void generatorRules(Date currentDate, Integer count) throws ParseException;
}
