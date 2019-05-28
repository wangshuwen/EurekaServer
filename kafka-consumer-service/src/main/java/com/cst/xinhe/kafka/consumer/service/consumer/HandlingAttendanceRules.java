package com.cst.xinhe.kafka.consumer.service.consumer;


import com.cst.xinhe.common.utils.convert.DateConvert;
import com.cst.xinhe.kafka.consumer.service.context.SpringContextUtil;
import com.cst.xinhe.persistence.dao.attendance.AttendanceMapper;
import com.cst.xinhe.persistence.dao.attendance.TimeStandardMapper;
import com.cst.xinhe.persistence.model.attendance.Attendance;
import com.cst.xinhe.persistence.model.attendance.TimeStandard;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @program: demo
 * @description: 处理考勤规则类
 * @author: lifeng
 * @create: 2019-01-28 17:31
 **/
public class HandlingAttendanceRules {

    public static void process(Integer attendanceId) throws ParseException {
        //获取考勤dao操作
        AttendanceMapper attendanceMapper = SpringContextUtil.getBean(AttendanceMapper.class);
        //根据ID获取具体的考勤内容
        Attendance attendance = attendanceMapper.selectByPrimaryKey(attendanceId);
        //获取到当前
        Integer ruleId = attendance.getRuleId();
        //获取考勤规则的操作
        TimeStandardMapper timeStandardMapper = SpringContextUtil.getBean(TimeStandardMapper.class);
        //根据ID获取到具体的考勤规则内容
        TimeStandard timeStandard = timeStandardMapper.selectByPrimaryKey(ruleId);


        //获取标准的开始时间
        Date standardStartTime = timeStandard.getStartTime();
        Date standardStartHHmmss = DateConvert.convertStringToDate(DateFormat.getTimeInstance().format(standardStartTime),8);
        //获取标准的结束时间
        Date standardEndTime = timeStandard.getEndTime();
        Date standardEndHHmmss = DateConvert.convertStringToDate(DateFormat.getTimeInstance().format(standardEndTime),8);


        //获取实际的上班打卡时间
        Date realStartTime = attendance.getStartTime();
        //获取实际上班打卡的日期String类型
        String realStartOfDate = DateFormat.getDateInstance().format(realStartTime);
        //获取实际上班打卡的日期Date类型
        Date dateStartReal = DateConvert.convertStringToDate(realStartOfDate,10);


        //获取实际的下班打卡时间
        Date realEndTime = attendance.getEndTime();
        //获取实际下班打卡的日期String类型
        String realEndOfDate = DateFormat.getDateInstance().format(realEndTime);
        //获取实际下班打卡的日期Date类型
        Date dateEndReal = DateConvert.convertStringToDate(realEndOfDate,10);



        //判断是否跨零点
        if (standardStartTime.getTime() > standardEndTime.getTime()){ // 标准应该跨越零点
            long temp = dateEndReal.getTime() - dateStartReal.getTime();
            if (temp == 0 ){
                  // 迟到
                        //判断开始时间
//                 long flag = (standardEndDate.getTime() - dateStartReal.getTime());
//                 if (flag == 0){
//
//                 }
                //TODO standardStartTime 加上当天的年月日格式，在进行比较
                long start = (realStartTime.getTime() - standardStartTime.getTime())/(1000*60*60);
                if (start > 0 && start <= timeStandard.getElasticTime()){
                    attendance.setBackup1("正常，弹性时间内");
                }
                if (start > timeStandard.getElasticTime() && start <= (timeStandard.getLateTime() + timeStandard.getElasticTime())){
                    attendance.setBackup1("迟到" + start + "小时");
                }
                if (start > (timeStandard.getLateTime() + timeStandard.getElasticTime())){
                    attendance.setBackup1("严重迟到" + start + "小时");
                }
                  // 判断早退问题
                        //判断结束时间
                long end = (standardEndTime.getTime() - realEndTime.getTime())/(1000*60*60);
                if (end > 0 && end <= timeStandard.getLeaveEarlyTime()){
                    attendance.setBackup2("早退" + end + "小时");
                }
                if (end > timeStandard.getLeaveEarlyTime() ){
                    attendance.setBackup2("严重早退" + end + "小时");
                }

            }else {
                //可能迟到
                //跨零点，但是打卡在前一天

                //TODO standardStartTime 拼接上前一天的日期，在作比较
                long start = (realStartTime.getTime() - standardStartTime.getTime())/(1000*60*60);
                if (start > 0 && start <= timeStandard.getElasticTime()){
                    attendance.setBackup1("正常，弹性时间内");
                }
                if (start > timeStandard.getElasticTime() && start <= (timeStandard.getLateTime() + timeStandard.getElasticTime())){
                    attendance.setBackup1("迟到" + start + "小时");
                }
                if (start > (timeStandard.getLateTime() + timeStandard.getElasticTime())){
                    attendance.setBackup1("严重迟到" + start + "小时");
                }

                // 可能早退
                //TODO
                long end = (standardEndTime.getTime() - realEndTime.getTime())/(1000*60*60);
                if (end > 0 && end <= timeStandard.getLeaveEarlyTime()){
                    attendance.setBackup2("早退" + end + "小时");
                }
                if (end > timeStandard.getLeaveEarlyTime() ){
                    attendance.setBackup2("严重早退" + end + "小时");
                }
                // 可能加班
                if (end < 0){
                    attendance.setBackup2("加班" + (-end) + "小时");
                }

            }
        }else { // 标准不未跨越零点
            // 实际跨越
            // 1、早来
            long t = standardStartTime.getTime() - realStartTime.getTime();
            if(t > 0){
                attendance.setBackup1("正常" + t/(1000*60*60) + "小时");
            }
            // 2、晚走
            long t1 = standardEndTime.getTime() - realEndTime.getTime();
            if (t1 > 0){
                attendance.setBackup2("加班" + t1/(1000*60*60) + "小时");
            }
        }
        attendanceMapper.updateByPrimaryKey(attendance);
    }

    public static String processAttendanceRules(TimeStandard standard) throws ParseException {

        String standardStartTime = DateFormat.getTimeInstance().format(standard.getStartTime());
        String standardEndTime = DateFormat.getTimeInstance().format(standard.getEndTime());

        Integer flag = standard.getFlag();

        Date currentDay = new Date();
        String today = DateFormat.getDateInstance().format(currentDay);

        StringBuffer todayStandardStartTime = new StringBuffer();
        todayStandardStartTime.append(today).append(standardStartTime);

        StringBuffer todayStandardEndTime = new StringBuffer();
        todayStandardEndTime.append(today).append(todayStandardEndTime);

        Date todayStartOfDate = DateConvert.convertStringToDate(todayStandardStartTime.toString(),19);

        Date todayEndOfDate = DateConvert.convertStringToDate(todayStandardEndTime.toString(),19);

        long temp = ((currentDay.getTime() - todayStartOfDate.getTime())/60000); // 分钟

//        long end_temp =

        if (flag == 0){
            // 未跨越当前日期
            if (temp > 0) {
                // 迟到
                if (temp <= standard.getElasticTime()){
                    return "正常";
                }
                if (temp > standard.getElasticTime() && temp <= standard.getElasticTime() + standard.getLateTime()){
                    return "迟到";
                }
                if (temp > standard.getLateTime() + standard.getElasticTime()){
                    return "严重迟到";
                }
            }else {
                // 提前
                return "正常";
            }
        }
        else if (flag == 1){
            // 跨越

        }
        else if (flag == 2){
            // 零点开始

            long judge = currentDay.getTime() - todayStartOfDate.getTime();
            if (judge > 8){
                // 提前一天，在当前天加一天
                // 正常
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(currentDay);
                calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
                Date c = calendar.getTime(); //这个时间就是日期往后推一天的结果
                String str = DateFormat.getDateInstance().format(c);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str).append(standardStartTime);
                Date d = DateConvert.convertStringToDate(stringBuffer.toString(),19);
                long temp1 = currentDay.getTime() - d.getTime() ;
                if (temp1 < 0){
                    return "正常";
                }else {
                    if (temp1 <= standard.getElasticTime()){
                        return "正常";
                    }
                    if (temp1 > standard.getElasticTime() && temp1 <= standard.getElasticTime() + standard.getLateTime()) {
                        return "迟到";
                    }else {
                        return "严重迟到";
                    }
                }

            }
            if ( judge < 8*60&& judge > 0 ){
                // 迟到
                if (judge <= standard.getElasticTime()){
                    return "正常";
                }
                if (judge > standard.getElasticTime() && judge <= standard.getElasticTime() + standard.getLateTime()){
                    return "迟到";
                }
                if (judge > standard.getLateTime() + standard.getElasticTime()){
                    return "严重迟到";
                }
            }
        }






        return null;
    }
}
