package com.csp.library.java.interfaces;

import java.util.List;

/**
 * Description: 空对象校验
 * Create Date: 2017/7/20
 * Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings("unused")
public interface VerifyEmpty {
	/**
	 * 字符串是否为空
	 *
	 * @param str 字符串
	 * @return true: 是
	 */
	boolean isEmpty(String str);

	/**
	 * 集合是否为空
	 *
	 * @param list 集合
	 * @return true: 是
	 */
	boolean isEmpty(List list);

	/**
	 * 数组是否为空
	 *
	 * @param array 集合
	 * @return true: 是
	 */
	boolean isEmpty(Object[] array);
}
