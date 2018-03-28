package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPrefsUtil {

	/**
	 * 获取[SharedPreferences]对象
	 * @param context 上下文
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 获取[SharedPreferences]对象
	 * @param context 上下文
	 * @param name    偏好文件名
	 */
	public static SharedPreferences getSharedPreferences(Context context, String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * 获取[Editor]对象
	 * @param context 上下文
	 */
	public static Editor getEditor(Context context) {
		return getSharedPreferences(context).edit();
	}

	/**
	 * 获取[Editor]对象
	 * @param context 上下文
	 * @param name    偏好文件名
	 */
	public static Editor getEditor(Context context, String name) {
		return getSharedPreferences(context, name).edit();
	}
}
