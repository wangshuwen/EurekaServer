package com.cst.xinhe.terminal.monitor.server.utils;

import java.util.Calendar;


/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/7/11:16
 */
public class SequenceIdGenerate {
    private static int sequenceId=0;
    private static int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    public synchronized static int getSequenceId() {
        int now=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if(now!=day){
            sequenceId=0;
            day=now;
        }
        if(sequenceId==65533){
            sequenceId=0;
        }
        return ++sequenceId;
    }



}
