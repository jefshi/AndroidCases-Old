package com.csp.project.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.csp.project.common.util.LogUtil;
import com.csp.project.constants.SystemConstant;

/**
 * Created by csp on 2017/3/2 002.
 */
public abstract class BaseActivity extends Activity implements InitialUi {
    private Toast toast;

    // ========================================
    // 生命周期方法
    // ========================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentViewBefore();
        setContentView(getContentId());
        setContentViewAfter();

        initialUi();
    }

    /**
     * 加载布局对象之前
     */
    public void setContentViewBefore() {
    }

    /**
     * 加载布局对象之后
     */
    public void setContentViewAfter() {
    }

    /**
     * 获取布局资源文件ID
     *
     * @return 布局资源文件ID
     */
    public abstract int getContentId();

    // ========================================
    // InitialUi接口实现
    // ========================================
    @Override
    public void initialUi() {
        initBundle();
        initView();
        initViewContent();
        initViewEvent();
    }

    /**
     * 初始化传入的参数, 只运行一次
     */
    @Override
    public void initBundle() {

    }

    /**
     * 获取各个[View], 只运行一次
     */
    @Override
    public void initView() {

    }

    /**
     * 刷新UI, 运行多次
     */
    @Override
    public void onRefresh() {

    }

    // ========================================
    // log, toast方法
    // ========================================
    private String toLogString(Object msg) {
        return "from [" + getClassName() + "]: " + String.valueOf(msg);
    }

    /**
     * 显示[Log]
     *
     * @param msg 日志消息
     */
    public void logD(Object msg) {
        LogUtil.logD(toLogString(msg));
    }

    /**
     * 显示[Log]
     *
     * @param msg 日志消息
     */
    public void logInfo(Object msg) {
        LogUtil.logInfo(toLogString(msg));
    }

    /**
     * 显示[Log], 含错误码信息
     */
    public void logError(int code, Object msg) {
        LogUtil.logError(code, toLogString(msg));
    }

    /**
     * 显示[Toast]
     *
     * @param msg 日志消息
     */
    public void toast(Object msg) {
        if (SystemConstant.DEBUG) {
            String message = String.valueOf(msg);
            if (!TextUtils.isEmpty(message)) {
                toast.setText(message);
                toast.show();
            }
        }
    }

    // ========================================
    // 其他方法
    // ========================================

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
        return getResources().getIdentifier(name, defType, getPackageName());
    }

    /**
     * 获取当前[Activity]对应的[View]对象
     */
    public View getDecorView() {
        return getWindow().getDecorView();
    }

}
