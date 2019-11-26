package com.csp.cases.activity.component.camerademo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseActivity
 * Created by csp on 2019/12/15.
 * Modified by csp on 2019/03/21.
 *
 * @version 1.0.4
 */
abstract class BaseButterKnifeActivity extends BaseActivity {

    protected boolean initialized; // true: 页面初始化过数据
    private boolean destroyed = false;
    Unbinder unbinder;

    public void setInitialized(boolean initialized) {
        if (!this.initialized && initialized) {
            this.initialized = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;

        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    @SuppressLint("ObsoleteSdkInt")
    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return super.isDestroyed();
        } else {
            return destroyed || isFinishing();
        }
    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * @return 布局资源 ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     */
    protected abstract void init();

    /**
     * @param showed true：显示数据存在的页面（正常情况）
     */
    public void showContent(boolean showed) {
        setInitialized(showed);
        if (showed) {
            showErrorNetWork(null, false);
            showErrorNoData(false);
        }
    }

//    /**
//     * 异常处理
//     *
//     * @param throwable 异常信息
//     * @param type      异常类型，如：网络请求失败、数据为空等类型
//     *                  {@link ConstantTable.System#ERROR_NO_DATA}
//     *                  {@link ConstantTable.System#ERROR_NETWORK}
//     */
//    public void onError(Throwable throwable, int type) {
//        if (type == ConstantTable.System.ERROR_NO_DATA) {
//            showErrorNoData(true);
//            return;
//        }
//        if (!initialized && type == ConstantTable.System.ERROR_NETWORK)
//            showErrorNetWork(throwable, true);
//    }

    /**
     * @param showed true：显示数据为空的界面
     */
    public void showErrorNoData(boolean showed) {
        setInitialized(showed);
        if (showed) {
            showContent(false);
            showErrorNetWork(null, false);
        }
    }

    /**
     * @param throwable 异常信息
     * @param showed    true：显示网络错误的页面
     */
    public void showErrorNetWork(Throwable throwable, boolean showed) {
        if (showed) {
            showContent(false);
            showErrorNoData(false);
        }
    }

    public String format(@StringRes int resId, Object... values) {
        String text = getString(resId);
        if (values == null)
            return text;

        for (int i = 0; i < values.length; i++) {
            if (values[i] == null)
                values[i] = "";
        }
        return String.format(text, values);
    }
}
