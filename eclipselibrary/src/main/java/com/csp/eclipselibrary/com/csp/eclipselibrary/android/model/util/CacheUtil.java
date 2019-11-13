package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

public class CacheUtil {
	/**
	 * 应用程序最大可用内存
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}
}
