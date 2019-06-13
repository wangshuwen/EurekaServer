package com.cst.xinhe.web.service.attendance.task;

import com.cst.xinhe.attendance.service.service.AttendanceRulesGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @ClassName Task  定时任务
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/5 20:33
 * @Vserion v0.0.1
 */
@Component
@Scope("singleton")
public class Task {

    @Resource
    private AttendanceRulesGenerator attendanceRulesGenerator;

    int i = 0;
    /**
     * @description: 定时生成考勤具体规则时间
     * @param: []
     * @return: void
     * @author: lifeng
     * @date: 2019-02-14
     */
    @Scheduled(cron = "0 0 0 * * ?") // 每天的零点执行一次定时任务
//    @Scheduled(cron = "0 30/10 * * * ? ") // 每天的零点执行一次定时任务
    public void createAttendanceData() throws ParseException {
        System.out.println("执行 " + i + " 次");
        attendanceRulesGenerator.generatorRules(new Date(), i);
        i ++;
    }
}
