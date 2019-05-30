package com.cst.xinhe.common.netty.utils;

/**
 * @ClassName NettyDataUtils
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/28 16:15
 * @Vserion v0.0.1
 */

public class NettyDataUtils {

    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    public static int isOdd(int num) {
        return num & 0x1;
    }

    /**
     * @param [inHex]
     * @return int
     * @description 16进制转int
     * @date 15:51 2018/11/21
     * @auther lifeng
     **/
    public static int HexToInt(String inHex)//Hex字符串转int
    {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * @param [inHex]
     * @return byte
     * @description 16进制转1字节
     * @date 15:52 2018/11/21
     * @auther lifeng
     **/
    public static byte HexToByte(String inHex)//Hex字符串转byte
    {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * @param [inByte]
     * @return java.lang.String
     * @description 16进制转2字节
     * @date 15:52 2018/11/21
     * @auther lifeng
     **/
    public static String Byte2Hex(Byte inByte)//1字节转2个Hex字符
    {
        return String.format("%02x", inByte).toUpperCase();
    }

    /**
     * @param [inBytArr]
     * @return java.lang.String
     * @description 字节数组转hex字符串
     * @date 15:52 2018/11/21
     * @auther lifeng
     **/
    public static String ByteArrToHexString(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        int j = inBytArr.length;
        for (int i = 0; i < j; i++) {
            strBuilder.append(Byte2Hex(inBytArr[i]));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    /**
     * @param [inBytArr, offset, byteCount]
     * @return java.lang.String
     * @description //字节数组转转hex字符串，可选长度
     * @date 15:53 2018/11/21
     * @auther lifeng
     **/
    public static String ByteArrToHexString(byte[] inBytArr, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            strBuilder.append(Byte2Hex(inBytArr[i]));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    /**
     * @param [inHex]
     * @return byte[]
     * @description hex字符串转字节数组
     * @date 15:53 2018/11/21
     * @auther lifeng
     **/
    public static byte[] HexToByteArr(String inHex)//hex字符串转字节数组
    {
        int hexlen = inHex.length();
        byte[] result;
        if (isOdd(hexlen) == 1) {//奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {//偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 获取无空格的hexString
     *
     * @param str
     * @return
     */
    public static String getFomattedHexString(String str) {

        StringBuilder sb = new StringBuilder();
        String[] strArr = str.split(" ");
        int len = strArr.length;

        for (int i = 0; i < len; i++) {
            sb.append(strArr[i]);
        }
        return sb.toString();
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert 16进制字符串转字节数组
     *
     * @param hexString String 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char 转换 byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * int型转化为byte数组
     *
     * @param num
     * @return
     */
    public static byte[] intToByteArray(int num) {
        return new byte[]{
                (byte) ((num >> 24) & 0xFF),
                (byte) ((num >> 16) & 0xFF),
                (byte) ((num >> 8) & 0xFF),
                (byte) (num & 0xFF)
        };
    }
    public synchronized static byte[] toHexByteByStrings(byte[] data,int start,String s) {
        int j = start;
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            data[j-1] = (byte) ((ch & 0xFF00) >> 8 );
            data[j] = (byte) ( ch & 0xFF);
            j += 2;
        }
        return data;
    }
//    private static byte charToByte(char c){
//        return (byte)"0123456789ABCDEF".indexOf(c);
//    }
    public static void main(String[] args) {
//        byte[] l = NettyDataUtils.intToByteArray(30);
//        System.out.println(l[2]);
//        System.out.println(l[3]);
        String str = "区域管理1#空间4#(12956978.18,4851801.00)#区域管理1#空间4#(12956880.09,4851692.32)#-4851801.0".toUpperCase();

        char[] charArr = str.toCharArray();
        System.out.println(charArr);
        System.out.println("======");
        System.out.println("char长度：" + charArr.length);
        int charArrLen = charArr.length;
        byte[] body = new byte[charArrLen * 3];
        for (int i = 0, j = 0; i < body.length && j < charArrLen; i = i + 3, j++) {
            if (charArr[j] <= 128) {
                body[i] = 0;
                body[i + 1] = 0;
                body[i + 2] = (byte) (charArr[j] &0xff);
            } else {
                String s = String.valueOf(charArr[j]);
                byte[] t_s_b = s.getBytes();
                body[i] = (byte)(t_s_b[0]&0xff);
                body[i + 1] = (byte)(t_s_b[1]&0xff);
                body[i + 2] = (byte)(t_s_b[2]&0xff);
            }
        }
        System.out.println("-------");
        for (int i = 0 ; i< body.length ; i ++){
            if (i % 3 == 0){
                System.out.print(" | ");
            }
            System.out.printf("0x%02x ",body[i]);
        }
        System.out.println("\n-------");

    }
}
