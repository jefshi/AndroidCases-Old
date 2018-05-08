package com.csp.utils.android.aaaTemp;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

/**
 * Description: 页面跳转类, 工具类
 * <p>Create Date: 2016-10-11 17:59:18
 * <p>Modify Date: 2016-06-13 17:26:38
 *
 * @author csp
 * @version 1.0.2
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class IntentUtil {
	private static int enterResId; // 入场动画
	private static int exitResId; // 出场动画

	// ========================================
	// 页面跳转, 基本
	// ========================================

	/**
	 * 仅跳转界面
	 *
	 * @see #startActivity(Context, Class, Bundle, Bundle)
	 */
	public static void startActivity(Context conFrom, Class<?> clsTo) {
		startActivity(conFrom, clsTo, null, null);
	}

	/**
	 * 跳转界面, 并携带[Bundle]数据
	 *
	 * @see #startActivity(Context, Class, Bundle, Bundle)
	 */
	public static void startActivity(Context conFrom, Class<?> clsTo, Bundle data) {
		startActivity(conFrom, clsTo, data, null);
	}

	/**
	 * 跳转界面, 携带[Bundle]数据, 携带附加[Bundle]项(如: 动画)
	 *
	 * @param conFrom 起点
	 * @param clsTo   目的
	 * @param data    携带数据
	 * @param options 携带附加项
	 */
	public static void startActivity(Context conFrom, Class<?> clsTo, Bundle data, Bundle options) {
		Intent intent = new Intent(conFrom, clsTo);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (data != null)
			intent.putExtras(data);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			conFrom.startActivity(intent, options);
		else
			conFrom.startActivity(intent);
	}

	// ========================================
	// 页面跳转, 高级
	// ========================================

	/**
	 * 设置出场入场动画
	 *
	 * @param enterResId 入场动画
	 * @param exitResId  出场动画
	 */
	public static void setSkipActivityAnim(int enterResId, int exitResId) {
		IntentUtil.enterResId = enterResId;
		IntentUtil.exitResId = exitResId;
	}

	/**
	 * 跳转页面, 携带出场入场动画
	 *
	 * @see #setSkipActivityAnim(int, int)
	 * @see #startActivityByAnim(Context, Class, Bundle, int, int)
	 */
	public static void startActivityByAnim(Context conFrom, Class<?> clsTo) {
		startActivityByAnim(conFrom, clsTo, null, enterResId, exitResId);
	}

	/**
	 * 跳转页面, 携带出场入场动画
	 *
	 * @see #setSkipActivityAnim(int, int)
	 * @see #startActivityByAnim(Context, Class, Bundle, int, int)
	 */
	public static void startActivityByAnim(Context conFrom, Class<?> clsTo, Bundle data) {
		startActivityByAnim(conFrom, clsTo, data, enterResId, exitResId);
	}

	/**
	 * 跳转页面, 携带出场入场动画
	 *
	 * @param conFrom    起点
	 * @param clsTo      目的
	 * @param data       携带数据
	 * @param enterResId 入场动画
	 * @param exitResId  出场动画
	 */
	public static void startActivityByAnim(Context conFrom, Class<?> clsTo, Bundle data, int enterResId, int exitResId) {
		Bundle options = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			ActivityOptions ao = ActivityOptions.makeCustomAnimation(conFrom, enterResId, exitResId);
			options = ao.toBundle();
		}
		startActivity(conFrom, clsTo, data, options);
	}
}
