package com.csp.library.java.aaaTemp.security;

import java.text.DecimalFormat;

/**
 * 单位转换工具类
 * Created by csp on 2018/07/02.
 * Modified by csp on 2018/07/02.
 *
 * @version 1.0.0
 */
public class UnitSwitchUtil {
    private static final int SIZE_KB = 1024;
    private static final int SIZE_MB = 1_048_576;
    private static final int SIZE_GB = 1_073_741_824;

    public static String toHumanSize(long byteLength) {
        String size;
        DecimalFormat df = new DecimalFormat("#.00");
        if (byteLength < SIZE_KB) {
            size = df.format((double) byteLength) + "BT";
        } else if (byteLength < SIZE_MB) {
            size = df.format((double) byteLength / SIZE_KB) + "KB";
        } else if (byteLength < SIZE_GB) {
            size = df.format((double) byteLength / SIZE_MB) + "MB";
        } else {
            size = df.format((double) byteLength / SIZE_GB) + "GB";
        }
        return size;
    }
}
