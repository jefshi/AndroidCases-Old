package com.csp.utils.android.widget;

import android.os.Handler;
import android.widget.Chronometer;

import com.csp.utils.android.datetime.DateUtil;

/**
 * Chronometer 监听器 - 修改显示格式
 * Created by csp on 2018/06/22.
 * Modified by csp on 2018/06/22.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ChronometerFormatListener implements Chronometer.OnChronometerTickListener {
    private int day;

    public ChronometerFormatListener(int day) {
        this.day = day;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onChronometerTick(final Chronometer chronometer) {
        // 测试代码
        // testFormat(chronometer);

        long time = DateUtil.getNowClock() - chronometer.getBase();
        if (time >= DateUtil.TIME_23_59_59) {
            // 保险起见，加上同步
            synchronized (ChronometerFormatListener.class) {
                if (time >= DateUtil.TIME_23_59_59) {
                    day++;
                    time -= DateUtil.TIME_23_59_59;
                    chronometer.setBase(DateUtil.getNowClock() - time);
                }
            }
        }

        // 应用切换到后台，该监听器就不会执行了
        // 所以要么随时刷新格式，要么刷新 Chronometer 控件
        if (time <= DateUtil.TIME_00_00_01
                || time >= DateUtil.TIME_00_59_59 && time - DateUtil.TIME_00_59_59 <= 1000
                || time >= DateUtil.TIME_09_59_59 && time - DateUtil.TIME_09_59_59 <= 1000
                || time >= DateUtil.TIME_23_59_59 && time - DateUtil.TIME_23_59_59 <= 1000) {

            chronometer.setFormat(ChronometerUtil.formatString(time, day));
        }
    }

    /**
     * 测试代码 Begin
     */
    private void testFormat(final Chronometer chronometer) {
//        Handler handler = new Handler();
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 + 2000), 5100);
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 * 10 + 2000), 12100);
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 * 24 + 2000), 19100);
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 + 2000), 24100);
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 * 10 + 2000), 31100);
//        handler.postDelayed(() -> chronometer.setBase(DateUtil.getNowClock() - 3600_000 * 24 + 2000), 38100);
    }
}