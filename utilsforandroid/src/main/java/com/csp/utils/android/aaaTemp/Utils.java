package com.csp.utils.android.aaaTemp;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by csp on 2018/4/2 002.
 */

public class Utils {

    /**
     * 执行300毫秒的震动效果
     *
     * @param context
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }


    /**
     * 获取应用运行的最大内存
     *
     * @return 最大内存
     */
    public static long getMaxMemory() {

        return Runtime.getRuntime().maxMemory() / 1024;
    }
}
