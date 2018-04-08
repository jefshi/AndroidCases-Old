package com.csp.library.android.util.display_metrics;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by chenshp on 2018/3/28.
 */

public class DisplayMetricsUtil {
    private static float density;

    /**
     * dip --> px
     *
     * @param context context
     * @param dip     dip
     * @return px
     */
    public static int dipToPx(Context context, float dip) {
        if (density <= 0.0F) {
            density = context.getResources().getDisplayMetrics().density;
        }

        return (int) (dip * density + 0.5F);
    }

    /**
     * px --> dip
     *
     * @param context context
     * @param px      px
     * @return dip
     */
    public static int pxToDip(Context context, float px) {
        if (density <= 0.0F) {
            density = context.getResources().getDisplayMetrics().density;
        }

        return (int) (px / density + 0.5F);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }
}
