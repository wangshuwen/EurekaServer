package com.cst.xinhe.common.utils;

import java.util.UUID;

/**
 * @ClassName GetUUID
 * @Description UUID 生成工具类
 * @Auther lifeng
 * @DATE 2018/8/17 10:27
 * @Vserion v0.0.1
 */

public class GetUUID {
    /**
     * @param []
     * @return java.lang.String
     * @description 生成UUID 并去除 ‘-’ 符号
     * @date 10:28 2018/8/17
     * @auther lifeng
     **/
    public synchronized static String getUuidReplace() {
        UUID uuidString = UUID.randomUUID();
        return uuidString.toString().replaceAll("-", "");
    }



    /**
     * @param []
     * @return java.lang.String
     * @description 获取 直接生成的 UUID
     * @date 10:28 2018/8/17
     * @auther lifeng
     **/
    public synchronized static String getUuid() {
        UUID uuidString = UUID.randomUUID();
        return uuidString.toString();
    }

    public static void main(String[] args) {
        System.out.println(GetUUID.getUuidReplace());
    }
}
