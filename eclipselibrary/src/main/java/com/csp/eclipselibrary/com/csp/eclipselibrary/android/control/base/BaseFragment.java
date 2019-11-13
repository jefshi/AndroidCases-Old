package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base;

import com.common.android.aInterface.InitialUi;
import com.common.android.control.util.LogUtil;
import com.common.constant.JConstant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;

/**
 * BaseFragment 类, 用于[Fragment]继承
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-15 16:47:29
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public abstract class BaseFragment extends Fragment implements InitialUi, OnClickListener {
	private Toast toast;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getContentViewId(), container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ButterKnife.bind(this, getView()); // ButterKnife 初始化
		afterSetContentView();

		initialUi();
	}

	@Override
	@SuppressLint("ShowToast")
	public void initialUi() {
		toast = Toast.makeText(getContext(), "", Toast.LENGTH_LONG);

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
	 * 获取[Context]对象
	 * @return
	 */
	public Context getContext() {
		return getView().getContext();
	}

	/**
	 * 重写方法: view.findViewById()
	 */
	@SuppressWarnings("unchecked")
	public <T> T findViewById(int resId) {
		return (T) getView().findViewById(resId);
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
		return getResources().getIdentifier(name, defType, getContext().getPackageName());
	}
}
