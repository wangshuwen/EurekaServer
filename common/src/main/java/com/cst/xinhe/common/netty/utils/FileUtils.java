package com.cst.xinhe.common.netty.utils;

import com.cst.xinhe.common.utils.FileType;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName FileUtils
 * @Description
 * @Auther lifeng
 * @DATE 2018/9/29 15:07
 * @Vserion v0.0.1
 */

public class FileUtils {

    public static boolean createFile(String dir, String file, FileType type) {
        File fileDir = new File(dir);

        boolean flag = false;

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        StringBuffer stringBuffer = new StringBuffer(file);
        stringBuffer.append(".").append(type);
        String f = stringBuffer.toString();
        File source = new File(dir, f);
        try {
            flag = source.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean removeFile(String src) {
        File file = new File(src);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;

    }
}
