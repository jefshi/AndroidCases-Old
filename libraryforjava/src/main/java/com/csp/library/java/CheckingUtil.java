package com.csp.library.java;

import java.util.regex.Pattern;

/**
 * Description: 常用校验工具类
 * <p>Create Date: 2017/10/23
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings("unused")
public class CheckingUtil {
	/**
	 * 验证手机
	 *
	 * @return true: 成功
	 */
	public static boolean checkPhone(String phone) {
		return Pattern.matches("^[1](\\d){10}$", phone);
	}

	/**
	 * 验证手机
	 *
	 * @return true: 成功
	 */
	public static boolean checkEmail(String email) {
		return Pattern.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)+", email);
	}
}
