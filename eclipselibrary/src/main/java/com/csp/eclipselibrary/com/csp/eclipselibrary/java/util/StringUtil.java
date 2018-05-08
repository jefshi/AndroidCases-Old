package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util;

import android.annotation.SuppressLint;

/**
 * 字符串应用类
 * @version 1.0.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-09-04 14:04:39
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class StringUtil {
	/**
	 * 转为大写
	 * @param object  文本
	 * @return String 大写文本
	 */
	@SuppressLint("DefaultLocale")
	public static String toUpperCase(Object object) {
		return object != null ? String.valueOf(object).toUpperCase() : "";
	}
	
	/**
	 * 重写方法: Sting.valueOf()
	 * @param object
	 * @return
	 */
	public static String valueOf(Object object) {
		return object != null ? String.valueOf(object) : "";
	}

	/**
	 * 判断字符串是否为空(至少含一个非空白字符)
	 * @version 1.0.0
	 * @param str 待检查的字符串
	 * @return boolean true: 非空, false: 空字符串
	 * @author csp
	 * <p style='font-weight:bold'>Date:</p> 2016-09-04 14:06:04
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static boolean isNotBlank(String str) {
		if (str == null) {
			return false;
		}

		str = str.trim();
		if (str.length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 将指定字符串的首字母转为大写, 并返回首字符大写的字符串
	 * @param str 指定字符串
	 * @return String 首字母大写的字符串
	 */
	@SuppressLint("DefaultLocale")
	public static String getFirstCapital(String str) {
		return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1, str.length());
	}
}
