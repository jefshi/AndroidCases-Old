package com.csp.library.android.base;

import android.view.View;

import com.csp.library.java.interfaces.VerifyEmpty;

import java.util.List;

/**
 * Description: 页面(Activity、Fragment)都需要实现的接口
 * <p>Create Date: 2017-07-05
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
interface BaseLibraryFuns extends VerifyEmpty {
	/**
	 * @link View.findViewById(int)
	 */
	<T extends View> T findView(int resId);

	/**
	 * 获取当前页面布局对应的[View]对象
	 *
	 * @return [View]对象
	 */
	View getView();

	// ========================================
	// Log 方法
	// ========================================

	/**
	 * 打印日志
	 *
	 * @see com.csp.library.android.util.log.LogCat#e(Object)
	 */
	void logError(Object message);

	/**
	 * 打印日志
	 *
	 * @see com.csp.library.android.util.log.LogCat#e(String, Object)
	 */
	void logError(String explain, Object message);

	/**
	 * 打印日志
	 *
	 * @see com.csp.library.android.util.log.LogCat#e(String, Object[])
	 */
	void logError(String explain, Object[] message);

	/**
	 * 打印日志
	 *
	 * @see com.csp.library.android.util.log.LogCat#e(String, List)
	 */
	void logError(String explain, List message);
}
