package com.cst.xinhe.common.utils.convert;

/**
 * @ClassName FileSizeConvert
 * @Description 文件大小 转换工具
 * @Auther lifeng
 * @DATE 2018/8/16 18:41
 * @Vserion v0.0.1
 */

public class FileSizeConvert {

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
}
