package com.cst.xinhe.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @param
 * @description 日志基础类
 * @date 16:27 2018/10/29
 * @auther lifeng
 * @return
 **/
public class BaseLog {

    public static Logger logger = null;

    public BaseLog() {
        logger = LoggerFactory.getLogger(getClass().getName());
    }
}
