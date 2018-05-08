package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.util;

import com.common.constant.JConstant;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Log, Toast 工具类, 工具类
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-17 17:03:07
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class LogUtil {
	// 自定义错误码
	public static final int OTHER_FAIL = -200000; // 其他失败

	/**
	 * 将字符串数组转为字符串, "\n"为分隔符
	 * @param message
	 * @return
	 */
	public static String getString(Object... message) {
		String msg = "";
		for (Object object : message) {
			msg += String.valueOf(object) + ", ";
		}
		return msg.substring(0, msg.length() - 2);
	}

	/**
	 * 显示[Log]
	 * @param message 日志消息
	 */
	public static void logInfo(Object... message) {
		if (JConstant.DEBUG) {
			Log.i("info", getString(message));
		}
	}

	/**
	 * 显示[Log]
	 * @param message 日志消息
	 */
	public static void logD(Object... message) {
		if (JConstant.DEBUG) {
			Log.d("TAG", getString(message));
		}
	}

	/**
	 * 显示[Log]
	 * @param code 错误码
	 * @param msg  日志消息
	 */
	public static void logError(int code, Object message) {
		if (JConstant.DEBUG) {
			Log.e("error", "error(" + code + "): " + getString(message));
		}
	}

	/**
	 * 显示[Toast]
	 * @param context 上下文对象
	 * @param message 日志消息
	 */
	public static void toast(Context context, String message) {
		if (JConstant.DEBUG) {
			Toast.makeText(context, getString(message), Toast.LENGTH_LONG).show();
		}
	}
}
