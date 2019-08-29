package com.csp.utils.android;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast 相关
 * Created by csp on 2019/08/06.
 * Modified by csp on 2019/08/06.
 *
 * @version 1.0.0
 */
public class ToastUtil {
    private static Toast mToast;
    private static Toast mViewToast;

    // ====================
    // 普通 Toast 模块
    // ====================

    /**
     * 普通 Toast
     */
    private static void initToast() {
        if (mToast == null) {
            Context context = Utils.getAppContext();
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);

//            int textview_id = Resources.getSystem().getIdentifier("message", "id", "android");
//            ((TextView) mToast.getView().findViewById(textview_id)).setGravity(Gravity.CENTER);
        }
    }

    /**
     * @see Toast#setDuration(int)
     */
    private static void setDuration(int duration) {
        initToast();
        mToast.setDuration(duration);
    }

    /**
     * @see Toast#setGravity(int, int, int)
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        initToast();
        mToast.setGravity(gravity, xOffset, yOffset);
    }

    /**
     * Show the toast
     *
     * @param isShort true: use Toast.LENGTH_SHORT, else use Toast.LENGTH_LONG
     */
    private static void showToast(boolean isShort, final CharSequence text) {
        setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.show();
    }

    /**
     * Show the toast
     *
     * @param isShort true: use Toast.LENGTH_SHORT, else use Toast.LENGTH_LONG
     */
    private static void showToast(boolean isShort, @StringRes final int resId) {
        setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        mToast.setText(resId);
        mToast.show();
    }

    /**
     * 使用 String#format 来拼接内容
     *
     * @see #showToast(boolean, CharSequence)
     * @see String#format(String, Object...)
     */
    public static void showToast(boolean isShort, @StringRes final int resId, Object... values) {
        Context context = Utils.getAppContext();
        String text = context.getString(resId);
        text = String.format(text, values);
        showToast(isShort, text);
    }

    /**
     * @see #showToast(boolean, int, Object...)
     */
    public static void showToast(@StringRes final int resId, Object... values) {
        showToast(true, resId, values);
    }

    /**
     * @see #showCustomToast(CharSequence, boolean)
     */
    public static void showToast(final CharSequence text) {
        showToast(true, text);
    }

    /**
     * @see #showCustomToast(int, boolean)
     */
    public static void showToast(@StringRes final int resId) {
        showToast(true, resId);
    }

    /**
     * Toast 居中
     */
    public static void showCenterToast(final CharSequence text) {
        setGravity(Gravity.CENTER, 0, 0);
        showToast(text);
    }

    /**
     * Toast 居中
     */
    public static void showCenterToast(@StringRes final int resId) {
        setGravity(Gravity.CENTER, 0, 0);
        showToast(resId);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text) {
        showToast(true, text);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        showToast(true, resId);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text) {
        showToast(false, text);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        showToast(false, resId);
    }

    // ====================
    // 自定义 Toast 模块：文字居中、灰色底框，白色字
    // ====================

    /**
     * 自定义 Toast 模块：居中、灰色底框，白色字
     */
    private static void initCustomToast() {
        if (mViewToast == null) {
            // TODO 根据需求补充 Toast 的自定义 View 布局
//            Context context = Utils.getAppContext();
//            View view = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
//
//            mViewToast = new Toast(context);
//            mViewToast.setView(view);
//            mViewToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }

    private static void setCustomDuration(int duration) {
        initCustomToast();
        mViewToast.setDuration(duration);
    }

    /**
     * @see Toast#setText(int)
     */
    private static void setCustomText(@StringRes final int resId) {
        // TODO 根据需求补充 Toast 的自定义 View 布局
//        View view = mViewToast.getView();
//        if (view == null)
//            return;
//
//        TextView tv_toast = view.findViewById(R.id.tv_toast);
//        tv_toast.setText(resId);
    }

    /**
     * @see Toast#setText(CharSequence)
     */
    private static void setCustomText(final CharSequence text) {
        // TODO 根据需求补充 Toast 的自定义 View 布局
//        View view = mViewToast.getView();
//        if (view == null)
//            return;
//
//        TextView tv_toast = view.findViewById(R.id.tv_toast);
//        tv_toast.setText(text);
    }

    /**
     * Show the toast
     *
     * @param isShort true: use Toast.LENGTH_SHORT, else use Toast.LENGTH_LONG
     */
    private static void showCustomToast(boolean isShort, final CharSequence text) {
        setCustomDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        setCustomText(text);
        mViewToast.show();
    }

    /**
     * Show the toast
     *
     * @param isShort true: use Toast.LENGTH_SHORT, else use Toast.LENGTH_LONG
     */
    private static void showCustomToast(boolean isShort, @StringRes final int resId) {
        setCustomDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        setCustomText(resId);
        mViewToast.show();
    }

    /**
     * @see #showCustomToast(CharSequence, boolean)
     */
    public static void showCustomToast(final CharSequence text) {
        showCustomToast(true, text);
    }

    /**
     * @see #showCustomToast(int, boolean)
     */
    public static void showCustomToast(@StringRes final int resId) {
        showCustomToast(true, resId);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showCustomShort(final CharSequence text) {
        showCustomToast(true, text);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showCustomShort(@StringRes final int resId) {
        showCustomToast(true, resId);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showCustomLong(final CharSequence text) {
        showCustomToast(false, text);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showCustomLong(@StringRes final int resId) {
        showCustomToast(false, resId);
    }
}
