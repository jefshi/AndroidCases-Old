package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base;

import com.common.android.aInterface.InitialUi;
import com.common.android.control.util.LogUtil;
import com.common.constant.JConstant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * BaseInclude 类, 用于[Include]布局继承
 * 使用注意:
 * 1) 继承类需提供私有构造方法, 提供静态方法, 用于实例化对象, 可参考[demo/IncludeDemo.java]
 *   a) onAttach(Activity activity, Intent intent)
 *   b) onAttach(View view, Intent intent)
 * 3) 相关依附类销毁前, 需调用[onDetach()], 避免内存泄漏
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-15 16:47:29
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public abstract class BaseInclude implements InitialUi, OnClickListener {
	private View	view;
	private Context	context;
	private Toast	toast;
	private Intent	intent;

	// ========================================
	// 构造方法与销毁方法
	// ========================================
	/**
	 * 构造方法
	 */
	protected BaseInclude(Activity activity, View view, Intent intent) {
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
		toast = null;
		intent = null;
	}

	// ========================================
	// initialized method
	// ========================================
	@Override
	@SuppressLint("ShowToast")
	public void initialUi() {
		toast = Toast.makeText(context, "", Toast.LENGTH_LONG);

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
	@Override
	public void onClick(View v) {
	}

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
