package com.csp.utils.android.classutil;

import android.annotation.SuppressLint;
import android.view.Gravity;

/**
 * Gravity 操作工具类
 * Created by csp on 2017/05/11.
 * Modified by csp on 2017/05/11.
 *
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class GravityUtl {
    /**
     * 格式化 Gravity 对象，确保水平方向支持 START，END，确保具备垂直方向（默认为 TOP）
     *
     * @param gravity Gravity
     * @return Gravity
     */
    public static int formatGravity(int gravity) {
        return formatGravity(gravity, true, true);
    }

    /**
     * 格式化 Gravity 对象，确保同时具备水平方向、垂直方向
     * {@link Gravity#START}， {@link Gravity#END}
     * {@link Gravity#TOP}， {@link Gravity#BOTTOM}
     *
     * @param gravity      Gravity
     * @param defaultStart true: 默认水平方向为 Gravity.START
     * @param defaultTop   true: 默认垂直方向为 Gravity.TOP
     * @return Gravity
     */
    public static int formatGravity(int gravity, boolean defaultStart, boolean defaultTop) {
        if ((gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
            gravity |= (defaultStart ? Gravity.START : Gravity.END);
        }
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
            gravity |= (defaultTop ? Gravity.TOP : Gravity.BOTTOM);
        }
        return gravity;
    }

    /**
     * TODO 不兼容 START，END
     * 返回 Gravity 对象，在水平方向上的方向
     *
     * @param gravity Gravity
     * @return {@link Gravity#LEFT}， {@link Gravity#CENTER_HORIZONTAL}， {@link Gravity#RIGHT}
     */
    @SuppressLint("RtlHardcoded")
    public static int getHorizontalGravity(int gravity) {
        if ((gravity & 0x0F) == Gravity.RIGHT)
            return Gravity.RIGHT;
        else if ((gravity & 0x0F) == Gravity.CENTER_HORIZONTAL)
            return Gravity.CENTER_HORIZONTAL;
        else
            return Gravity.LEFT;
    }

    /**
     * 返回 Gravity 对象，在垂直方向上的方向
     *
     * @param gravity Gravity
     * @return {@link Gravity#TOP}， {@link Gravity#CENTER_VERTICAL}， {@link Gravity#BOTTOM}
     */
    public static int getVerticalGravity(int gravity) {
        if ((gravity & 0xF0) == Gravity.BOTTOM)
            return Gravity.BOTTOM;
        else if ((gravity & 0xF0) == Gravity.CENTER_VERTICAL)
            return Gravity.CENTER_VERTICAL;
        else
            return Gravity.TOP;
    }
}
