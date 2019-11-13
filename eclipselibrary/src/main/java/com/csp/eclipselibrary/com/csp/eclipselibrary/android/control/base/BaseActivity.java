package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base;

import com.common.android.aInterface.InitialUi;
import com.common.android.control.util.LogUtil;
import com.common.constant.JConstant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * BaseActivity 类, 用于[Activity]继承
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-15 16:47:29
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public abstract class BaseActivity extends Activity implements InitialUi, OnClickListener {
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		beforeSetContentView();
		setContentView();
		ButterKnife.bind(this); // ButterKnife 初始化
		afterSetContentView();

		// 初始化UI
		initialUi();
	}
	
	@Override
	@SuppressLint("ShowToast")
	public void initialUi() {
		toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

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
	
	@Override
	public void onClick(View v) {
	}

	// ========================================
	// setContentView()
	// ========================================
	public void beforeSetContentView() {
	}

	/**
	 * 设置布局资源
	 */
	public void setContentView() {
		setContentView(getContentViewId());
	}

	public void afterSetContentView() {
	}

	/**
	 * 获取布局资源
	 * @return int 布局资源(xml)
	 */
	public abstract int getContentViewId();

	// ========================================
	// log, toast方法
	// ========================================
	private String toLogString(Object msg) {
		return "from [" + getClassName() + "]: " + String.valueOf(msg);
	}

	/**
	 * 显示[Log]
	 * @param msg 日志消息
	 */
	public void logD(Object msg) {
		LogUtil.logD(toLogString(msg));
	}

	/**
	 * 显示[Log]
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
	 * @param msg 日志消息
	 */
	public void toast(Object msg) {
		if (JConstant.DEBUG) {
			String message = String.valueOf(msg);
			if (!TextUtils.isEmpty(message)) {
				toast.setText(message);
				toast.show();
			}
		}
	}

	/**
	 * 显示[Log][Toast]
	 * @param toast [Toast]日志消息
	 * @param log   [Log]日志消息
	 */
	public void logAndToast(Object toast, Object log) {
		toast(toast);
		logD(log);
	}

	/**
	 * 显示[Log][Toast]
	 * @param toast [Toast]日志消息
	 * @param log   [Log]日志消息
	 */
	public void logAndToast(Object toast, int errorCode, Object log) {
		toast(toast);
		logError(errorCode, log);
	}

	// ========================================
	// 其他方法
	// ========================================
	/**
	 * 获取当前类名称
	 * @return
	 */
	public String getClassName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * 获取资源ID, 根据资源名称
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
