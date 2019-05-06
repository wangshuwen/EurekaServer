package com.cst.xinhe.common.netty.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @ClassName WriteFileUtil
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/29 9:07
 * @Vserion v0.0.1
 */

public class WriteFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(WriteFileUtil.class);

    public static void writeByteToFile(String fileName, byte[] data, int start, int end, boolean overWrite) {

        File file = new File(fileName);
//        System.out.println(end);

        OutputStream out = null;

        try {
            out = new FileOutputStream(file, overWrite);
            out.write(data, start, end);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在，建立文件连接失败！");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO异常！");
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("关闭输出流失败！");
                    e.printStackTrace();
                }
            }
        }

    }
}
