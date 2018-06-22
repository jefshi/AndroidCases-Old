package com.csp.utils.android.datetime;

import android.os.SystemClock;

/**
 * 时间工具
 * Created by csp on 2016/12/14.
 * Modified by csp on 2016/12/14.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DateUtil {
    public static final long TIME_00_00_01 = 1_000;
    public static final long TIME_01_00_00 = 3_600_000;
    public static final long TIME_10_00_00 = 36_000_000;
    public static final long TIME_24_00_00 = 86_400_000;
    public static final long TIME_00_59_59 = TIME_01_00_00 - 1_000;
    public static final long TIME_09_59_59 = TIME_10_00_00 - 1_000;
    public static final long TIME_23_59_59 = TIME_24_00_00 - 1_000;

    /**
     * @see System#currentTimeMillis()
     */
    public static long getNow() {
        return System.currentTimeMillis();
    }

    /**
     * @see SystemClock#elapsedRealtime()
     */
    public static long getNowClock() {
        return SystemClock.elapsedRealtime();
    }
}
