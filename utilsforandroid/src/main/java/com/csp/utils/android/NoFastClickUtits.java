package com.csp.utils.android;

import android.support.annotation.IdRes;
import android.util.SparseLongArray;

import com.csp.utils.android.log.LogCat;

/**
 * 防重复点击
 * Created by csp、gcc on 2019/12/31
 * Modified by csp、gcc on 2019/12/31
 *
 * @version 1.0.0
 */
public class NoFastClickUtits {
    private static int sSpaceTime = 1000;
    private static SparseLongArray sViews = new SparseLongArray();

    public static void setSpaceTime(int spaceTime) {
        sSpaceTime = spaceTime;
    }

    public static boolean isFastClick(@IdRes int viewId) {
        return isFastClick(viewId, sSpaceTime);
    }

    public static boolean isFastClick(@IdRes int viewId, long spaceTime) {
        long currentTime = System.currentTimeMillis();
        long lastClickTime = sViews.get(viewId);
        boolean firstRegister = lastClickTime == 0;
        sViews.put(viewId, currentTime);

        if (BuildConfig.DEBUG && !firstRegister) {
            LogCat.i("lastClickTime is", lastClickTime);
        }
        return !firstRegister
                || currentTime - lastClickTime < spaceTime;
    }
}
