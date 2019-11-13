package com.csp.eclipselibrary.com.csp.eclipselibrary.android.view.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class MetricsUtil {

	/**
	 * dip -> px
	 * @param context
	 * @param unit    单位, 详见[applyDimension]方法
	 * @param dip
	 * @return
	 */
	public static float getPxByDip(Context context, int dip) {
		DisplayMetrics dMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, dMetrics);
	}

	/**
	 * dip -> px
	 * @param context
	 * @param unit    单位, 详见[applyDimension]方法
	 * @param dip
	 * @return
	 */
	public static float getPxByDip(Context context, int unit, int dip) {
		DisplayMetrics dMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, dMetrics);
	}

	/**
	 * dip -> px
	 * @param context
	 * @param unit    单位, 详见[applyDimension]方法
	 * @param dip
	 * @return
	 */
	public static float getPxByDip(View view, int unit, int dip) {
		DisplayMetrics dMetrics = view.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dip, dMetrics);
	}

	/**
	 * 获取屏幕参数
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenParam(Context context) {
		return context.getResources().getDisplayMetrics();
	}
}
