package com.csp.eclipselibrary.com.csp.eclipselibrary.java.util;

public class CompareUtil {

	/**
	 * 比较整数
	 */
	public static int compareTo(long src, long desc) {
		if (src > desc) {
			return 1;
		} else if (src == desc) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 比较字符串
	 * TODO 中文优先
	 * @param src     源字符串
	 * @param desc    目标字符串
	 * @param isPrior 是否中英文优先
	 * @return
	 */
	public static int compareTo(String src, String desc, boolean isPrior) {
		if (src != null) {
//			if (isPrior) {
//				if (desc == null) {
//					return 1;
//				}
//
//				// 获取首字母
//				String srcChStr = String.valueOf(src.charAt(0));
//				String decChStr = String.valueOf(desc.charAt(0));
//
//				// 首字母非中文, 非英文
//				if (srcChStr.matches("[a-zA-Z\u4e00-\u9fff]")) {
//					return -1;
//				}
//			}
			return src.compareTo(desc);
		} else {
			return -1;
		}
	}
}
