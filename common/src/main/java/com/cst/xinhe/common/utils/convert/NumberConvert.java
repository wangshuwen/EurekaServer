package com.cst.xinhe.common.utils.convert;

import java.text.DecimalFormat;

/**
 * @ClassName NumberConvert
 * @Description 进制转换工具类
 * @Auther lifeng
 * @DATE 2018/9/12 9:57
 * @Vserion v0.0.1
 */

public class NumberConvert {


    /**
     * @param [number, type]
     * @return java.lang.String
     * @description
     * @date 10:44 2018/9/12
     * @auther lifeng
     **/
    public static String convertNum(long number, int type) {
        DecimalFormat decimalFormat = null;

        if (type == 1) {
            new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        }
        if (type == 2) {
            new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        }
        if (type == 3) {
            new DecimalFormat(".000");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        } else {
            new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        }
        String p = decimalFormat.format(number);//format 返回的是字符串
        return p;
    }

    /**
     * byte[] 转为16进制String
     */
    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 从一个byte[]数组中截取一部分
     *
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) bs[i - begin] = src[i];
        return bs;
    }

    //     转化十六进制编码为字符串
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            s = new String(baKeyword, "utf-8");//UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


}
