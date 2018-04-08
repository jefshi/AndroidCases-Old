package com.csp.library.android.util;

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

}
