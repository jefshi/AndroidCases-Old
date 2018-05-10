package com.csp.utils.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;

/**
 * Created by chenshp on 2018/3/28.
 */

/**
 * Description: 测量工具类
 * <p>Create Date: 2018/04/09
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class MetricsUtil {

    /**
     * 获取 DisplayMetrics 对象
     *
     * @param context context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * ? -> px
     *
     * @param context context
     * @param unit    {@link TypedValue#applyDimension(int, float, DisplayMetrics)}
     * @param value   值
     * @return px
     */
    public static float toPx(Context context, int unit, float value) {
        return TypedValue.applyDimension(unit, value, getDisplayMetrics(context));
    }

    /**
     * dip -> px
     *
     * @param context context
     * @param dip     dip
     * @return px
     */
    public static float dipToPx(Context context, float dip) {
        return toPx(context, TypedValue.COMPLEX_UNIT_DIP, dip);
    }

    /**
     * sp -> px
     *
     * @param context context
     * @param sp      dip
     * @return px
     */
    public static float spToPx(Context context, float sp) {
        return toPx(context, TypedValue.COMPLEX_UNIT_SP, sp);
    }

    /**
     * px -> dip
     *
     * @param context context
     * @param px      px
     * @return dip
     * @see TypedValue#applyDimension(int, float, DisplayMetrics)
     */
    public static float pxToDip(Context context, float px) {
        return px / getDisplayMetrics(context).density;
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
