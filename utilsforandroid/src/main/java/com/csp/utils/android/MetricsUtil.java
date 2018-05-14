package com.csp.utils.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;

/**
 * Description: 测量工具类
 * <p>Create Date: 2018/04/09
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
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

//    public static boolean isPortrait = true;
//    /**
//     * 用于获取状态栏的高度。
//     *
//     * @return 返回状态栏高度的像素值。
//     */
//    public static int getStatusBarHeight() {
//        int statusBarHeight = 0;
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object o = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = (Integer) field.get(o);
//            statusBarHeight = App.getAppContext().getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusBarHeight;
//    }
//
//    public static void getDisplayWidthHeight(int[] display) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) App.getAppContext().getSystemService(WINDOW_SERVICE);
//        wm.getDefaultDisplay().getRealMetrics(displayMetrics);
//        display[0] = displayMetrics.widthPixels;
//        display[1] = displayMetrics.heightPixels;
//    }
//
//    public static int getDisplayWidth() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) App.getAppContext().getSystemService(WINDOW_SERVICE);
//        wm.getDefaultDisplay().getRealMetrics(displayMetrics);
//        return displayMetrics.widthPixels;
//    }
//
//    public static int getDisplayHeight() {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) App.getAppContext().getSystemService(WINDOW_SERVICE);
//        wm.getDefaultDisplay().getRealMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }
//
//    /**
//     * 将px值转换为dip或dp值，保证尺寸大小不变
//     *
//     * @param pxValue
//     * @param （DisplayMetrics类中属性density）
//     * @return
//     */
//    public static int px2dip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    /**
//     * 判断当前屏幕是横屏还是竖屏 true竖屏 false横屏
//     *
//     * @return
//     */
//    public static boolean isScreenOriatationPortrait() {
//        Resources resources = App.getAppContext().getResources();
//        return resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
//    }
//
//    /**
//     * 将dip或dp值转换为px值，保证尺寸大小不变
//     *
//     * @param dipValue
//     * @param （DisplayMetrics类中属性density）
//     * @return
//     */
//    public static int dp2px(float dipValue) {
//        Resources resources = App.getAppContext().getResources();
//        final float scale = resources.getDisplayMetrics().density;
//        return (int) (dipValue * scale + 0.5f);
//    }
//
//    /**
//     * 将px值转换为sp值，保证文字大小不变
//     *
//     * @param pxValue
//     * @param （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int px2sp(Context context, float pxValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (pxValue / fontScale + 0.5f);
//    }
//
//    /**
//     * 将sp值转换为px值，保证文字大小不变
//     *
//     * @param spValue
//     * @param （DisplayMetrics类中属性scaledDensity）
//     * @return
//     */
//    public static int sp2px(Context context, float spValue) {
//        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * fontScale + 0.5f);
//    }
//
//    private static int DEFAULT_NAV_BAR_HEIGHT_DP = 48;
//    private static int DEFAULT_STATUS_BAR_HEIGHT_DP = 25;

//    /**
//     * 高度=屏幕高+状态栏高度
//     *
//     * @return
//     */
//    public static int screenHeight() {
//        return getDisplayHeight() + getStatusBarHeight() + navigationBarHeightPx();
//    }
//
//
//    public static int navigationBarHeightPx() {
//        Resources resources = App.getAppContext().getResources();
//        int mNavigationBarHeight;
//        int navBarHeightId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        if (navBarHeightId > 0) {
//            mNavigationBarHeight = resources.getDimensionPixelSize(navBarHeightId);
//        } else {
//            mNavigationBarHeight = dp2px(DEFAULT_NAV_BAR_HEIGHT_DP);
//        }
//        return mNavigationBarHeight;
//    }
}
