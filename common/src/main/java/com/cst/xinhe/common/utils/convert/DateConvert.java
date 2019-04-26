package com.cst.xinhe.common.utils.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateConvert   时间格式化工具
 * @Description
 * @Auther lifeng
 * @DATE 2018/8/17 9:08
 * @Vserion v0.0.1
 */

public class DateConvert {

    private static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat yyyyMMddHHmmss_t = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");
    private static final int T_YYYY_MM_DD = 10;
    private static final int T_yyyyMMddHHmmss = 19;
    private static final int T_HHmmss = 8;

    /**
     * @param [source, type]
     * @return java.lang.String
     * @description 将时间转换为字符串格式
     * @date 13:13 2018/10/29
     * @auther lifeng
     **/
    public static String convert(Date source, int type) {
        switch (type) {
            case 10:
                return yyyyMMdd.format(source);
            case 15:
                return yyyyMMddHHmmss_t.format(source);
            case 19:
                return yyyyMMddHHmmss.format(source);
            case 8:
                return HHmmss.format(source);
            default:
                return yyyyMMddHHmmss.format(source);
        }
    }

    /**
     * @param [s, type]
     * @return java.util.Date
     * @description 将时间的字符串格式转化为时间
     * @date 13:12 2018/10/29
     * @auther lifeng
     **/
    public static Date convertStringToDate(String s, int type) throws ParseException {
        switch (type) {
            case 10:
                return yyyyMMdd.parse(s);
            case 19:
                return yyyyMMddHHmmss.parse(s);
            case 8:
                return HHmmss.parse(s);
            default:
                return yyyyMMddHHmmss.parse(s);
        }
    }

    /**
     *
     * @param s
     * @param type
     * @return
     * @throws ParseException
     */
    public static String convertDateToStamp(String s, int type) throws ParseException {
        switch (type) {
            case 10:
                return String.valueOf(yyyyMMdd.parse(s).getTime());
            case 19:
                return String.valueOf(yyyyMMddHHmmss.parse(s).getTime());
            case 8:
                return String.valueOf(HHmmss.parse(s).getTime());
            default:
                return String.valueOf(yyyyMMddHHmmss.parse(s).getTime());
        }
    }

    /*
     * 将时间戳转换为时间
     */
    public static Date convertStampToDate(String s, int type) throws ParseException {
        switch (type) {
            case 10:
                return DateConvert.convertStringToDate(yyyyMMdd.format(new Date(new Long(s))), 10);
            case 19:
                return DateConvert.convertStringToDate(yyyyMMddHHmmss.format(new Date(new Long(s))), 19);
            case 8:
                return DateConvert.convertStringToDate(HHmmss.format(new Date(new Long(s))), 8);
            default:
                return DateConvert.convertStringToDate(yyyyMMddHHmmss.format(new Date(new Long(s))), 19);
        }

    }

    public static String convertStampToDateString(String s, int type) {
        switch (type) {
            case 10:
                return yyyyMMdd.format(new Date(new Long(s)));
            case 19:
                return yyyyMMddHHmmss.format(new Date(new Long(s)));
            case 8:
                return HHmmss.format(new Date(new Long(s)));
            default:
                return yyyyMMddHHmmss.format(new Date(new Long(s)));
        }

    }

    /**
     * @param
     * @return void
     * @description 测试主函数
     * @date 13:13 2018/10/29
     * @auther lifeng
     **/
    public static void main(String[] args) {
//        String time = "2018-10-5 10:4:1";
//        String time = "Mar 6, 2019";
        String time = "1551826668000";
        try {
            System.out.println(DateConvert.convertStringToDate(time, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
