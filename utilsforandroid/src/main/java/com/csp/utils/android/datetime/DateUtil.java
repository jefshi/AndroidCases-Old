package com.csp.utils.android.datetime;

import android.os.SystemClock;

/**
 * Description: 日志打印
 * <p>Create Date: 2016/12/14
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class DateUtil {

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
