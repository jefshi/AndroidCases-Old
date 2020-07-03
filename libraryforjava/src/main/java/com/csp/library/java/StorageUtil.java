package com.csp.library.java;

/**
 * 存储相关工具类
 * Created by csp on 2018/03/28
 * Modified by csp on 2018/03/28
 *
 * @version 1.0.1
 */
public class StorageUtil {
    public static final String B = "B";
    public static final String KB = "KB";
    public static final String MB = "MB";
    public static final String GB = "GB";
    public static final String TB = "TB";

    public static final long KB_SIZE = 1_024;
    public static final long MB_SIZE = 1_048_576;
    public static final long GB_SIZE = 1_073_741_824;
    public static final long TB_SIZE = 1_099_511_627_776L;

    public static String format(long size) {
        long unit_size;
        String unit_name;
        if (size < KB_SIZE) {
            unit_size = 1;
            unit_name = B;
        } else if (size < MB_SIZE) {
            unit_size = KB_SIZE;
            unit_name = KB;
        } else if (size < GB_SIZE) {
            unit_size = MB_SIZE;
            unit_name = MB;
        } else if (size < TB_SIZE) {
            unit_size = GB_SIZE;
            unit_name = GB;
        } else {
            unit_size = TB_SIZE;
            unit_name = TB;
        }
        return size < KB_SIZE
                ? size + B
                : String.format("%.2f", size * 1.0 / unit_size) + unit_name;
    }
}
