package com.csp.library.android.util.display_metrics;

import android.content.Context;

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
    public static int dipToPx(Context context, int dip) {
        if (density <= 0.0F) {
            density = context.getResources().getDisplayMetrics().density;
        }

        return (int) ((float) dip * density + 0.5F);
    }

    /**
     * px --> dip
     *
     * @param context context
     * @param px      px
     * @return dip
     */
    public static int pxToDip(Context context, int px) {
        if (density <= 0.0F) {
            density = context.getResources().getDisplayMetrics().density;
        }

        return (int) ((float) px / density + 0.5F);
    }
}
