package com.csp.library.android.base;

import android.view.View;

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
}
