package com.csp.utils.android.widget;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * Description: 当前应用信息
 * <p>Create Date: 2018/04/28
 * <p>Modify User: csp
 * <p>Modify Date: nothing
 *
 * @author fukq
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class ChronometerUtil {
    /**
     * 刷新 Chronometer 控件，格式：00:00:00
     *
     * @param chronometer Chronometer 控件
     * @param time        起始时间
     */
    public static void refreshChronometer(Chronometer chronometer, long time) {
        chronometer.setBase(SystemClock.elapsedRealtime() - time);//计时器清零
        int hour = (int) (time / 1000 / 60 / 60);
        if (hour <= 9) {
            chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
        } else {
            chronometer.setFormat("%s");
        }
        chronometer.start();
    }
}
