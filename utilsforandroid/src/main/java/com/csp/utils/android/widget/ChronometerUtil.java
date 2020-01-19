package com.csp.utils.android.widget;

import android.widget.Chronometer;

import com.csp.utils.android.datetime.DateUtil;

/**
 * Chronometer 控件工具
 * Created by fukq on 2018/04/20.
 * Modified by csp on 2018/06/22.
 *
 * @version 1.0.1
 */
@SuppressWarnings("unused")
public class ChronometerUtil {

    /**
     * 刷新 Chronometer 控件，格式：00:00:00
     *
     * @param chronometer Chronometer 控件
     * @param time        起始时间
     */
    public static void refreshChronometer(Chronometer chronometer, boolean start, long time) {
        time = time < 0 ? 0 : time;
        int day = 0;
        if (time >= DateUtil.TIME_24_00_00) {
            day = (int) (time / DateUtil.TIME_24_00_00);
            time -= day * DateUtil.TIME_24_00_00;
        }

        chronometer.setFormat(formatString(time, day));
        chronometer.setBase(DateUtil.getNowClock() - time);
        if (start) {
            // 应用切换到后台，该监听器就不会执行了
            // 所以要么随时刷新格式，要么刷新 Chronometer 控件
            chronometer.setOnChronometerTickListener(new ChronometerFormatListener(day));
            chronometer.start();
        } else {
            chronometer.setOnChronometerTickListener(null);
            chronometer.stop();
        }
    }

    public static String formatString(long time, int day) {
        StringBuilder builder = new StringBuilder();
        if (day != 0)
            builder.append(day).append(":");

        if (time < DateUtil.TIME_00_59_59)
            builder.append("00:%s");
        else if (time < DateUtil.TIME_09_59_59)
            builder.append("0%s");
        else
            builder.append("%s");

        return builder.toString();
    }
}
