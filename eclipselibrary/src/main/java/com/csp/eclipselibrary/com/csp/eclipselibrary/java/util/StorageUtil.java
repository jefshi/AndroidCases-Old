package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util;

public class StorageUtil {
	public static final String	B	= "B";
	public static final String	KB	= "KB";
	public static final String	MB	= "MB";
	public static final String	GB	= "GB";
	public static final String	TB	= "TB";

	public static final int	KB_SIZE	= 1024;
	public static final int	MB_SIZE	= 1048576;
	public static final int	GB_SIZE	= 1073741824;

	public static String format(int size) {
		String format = null;
		if (size < KB_SIZE) {
			format = size + B;
		} else if (size < MB_SIZE) {
			format = String.format("%.2f", size * 1.0 / KB_SIZE) + KB;
		} else if (size < GB_SIZE) {
			format = String.format("%.2f", size * 1.0 / MB_SIZE) + MB;
		} else {
			format = String.format("%.2f", size * 1.0 / GB_SIZE) + GB_SIZE;
		}

		return format;
	}
}
