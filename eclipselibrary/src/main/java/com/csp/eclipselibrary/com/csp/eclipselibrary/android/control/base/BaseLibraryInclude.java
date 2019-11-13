package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;

import com.csp.android.common.android.interfaces.InitialUi;

import butterknife.ButterKnife;

/**
 * BaseLibraryInclude 类, 用于[Include]布局继承
 * 使用注意:
 * 1) 继承类需提供私有构造方法, 提供静态方法, 用于实例化对象, 可参考[demo/IncludeDemo.java]
 * a) onAttach(Activity activity, Intent intent)
 * b) onAttach(View view, Intent intent)
 * 3) 相关依附类销毁前, 需调用[onDetach()], 避免内存泄漏
 *
 * @author csp
 *         <p style='font-weight:bold'>Date:</p> 2016-12-15 16:47:29
 *         <p style='font-weight:bold'>AlterDate:</p>
 * @version 1.0
 */
public abstract class BaseLibraryInclude implements InitialUi, OnClickListener {
    private View view;
    private Context context;
    private Intent intent;

    // ========================================
    // 构造方法与销毁方法
    // ========================================

    /**
     * 构造方法
     */
    protected BaseLibraryInclude(Activity activity, View view, Intent intent) {
        if (view != null) {
            this.view = view;
        } else if (activity != null) {
            this.view = activity.getWindow().getDecorView();
        }

        context = this.view.getContext();
        ButterKnife.bind(this, this.view); // ButterKnife 初始化
        afterSetContentView();

        this.intent = intent;
    }

    /**
     * 销毁关联数据, 避免内存溢出
     */
    public void onDetach() {
        view = null;
        context = null;
        intent = null;
    }

    // ========================================
    // initialized method
    // ========================================
    @Override
    public void initialUi() {
        initBundle();
        initView();
        initViewContent();
        initViewEvent();
    }

    @Override
    public void initBundle() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initViewEvent() {
    }

    @Override
    public void onRefresh() {
    }

    // ========================================
    // 成员变量获取
    // ========================================

    /**
     * 获取View
     */
    public View getView() {
        return view;
    }

    /**
     * 获取Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * 获取Intent
     */
    public Intent getIntent() {
        return intent;
    }

    // ========================================
    // setContentView()
    // ========================================
    public void afterSetContentView() {
    }

    // ========================================
    // 其他方法
    // ========================================
    @Override
    public void onClick(View v) {
    }

    /**
     * 获取当前类名称
     *
     * @return
     */
    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 获取资源ID, 根据资源名称
     *
     * @param name    资源文件名称
     * @param defType 资源类型: "layout"
     * @return
     */
    public int getResourceId(String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

    // ========================================
    // 重写方法
    // ========================================

    /**
     * 重写方法: getString()
     */
    public String getString(int resId) {
        return context.getString(resId);
    }

    /**
     * 重写方法: view.findViewById()
     */
    @SuppressWarnings("unchecked")
    public <T> T findViewById(int id) {
        return (T) view.findViewById(id);
    }

    /**
     * 重写方法: sendBroadcast()
     */
    public void sendBroadcast(Intent intent) {
        context.sendBroadcast(intent);
    }

    /**
     * 重写方法: registerReceiver()
     */
    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        context.registerReceiver(receiver, filter);
    }

    /**
     * 重写方法: unregisterReceiver()
     */
    public void unregisterReceiver(BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
    }
}
