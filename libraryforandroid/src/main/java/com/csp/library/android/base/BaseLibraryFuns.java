package com.csp.library.android.base;

import android.view.View;

import java.util.Collection;

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
interface BaseLibraryFuns {
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
     * @see com.csp.utils.android.log.LogCat#e(Object)
     */
    void logError(Object message);

    /**
     * 打印日志
     *
     * @see com.csp.utils.android.log.LogCat#e(String, Object)
     */
    void logError(String explain, Object message);

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
     * @param collection 集合
     * @return true: 是
     */
    boolean isEmpty(Collection collection);

    /**
     * 数组是否为空
     *
     * @param array 集合
     * @return true: 是
     */
    boolean isEmpty(Object[] array);
}
