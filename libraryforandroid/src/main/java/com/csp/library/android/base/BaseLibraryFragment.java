package com.csp.library.android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csp.library.android.interfaces.InitialUi;
import com.csp.library.android.util.log.LogCat;

import java.util.List;

/**
 * Description: 用于 Fragment 继承
 * <p>Create Date: 2016-12-15
 * <p>Modify Date: 2016-06-13
 *
 * @author csp
 * @version 1.0.2
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BaseLibraryFragment extends Fragment implements InitialUi, BaseLibraryFuns {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getLayoutId(), container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// 初始化UI
		initialUi();
	}

	@Override
	public void initialUi() {
		initBundle();
		initView();
		initViewContent();
		initViewEvent();
	}

	/**
	 * 获取布局资源ID
	 *
	 * @return 布局资源ID
	 */
	public abstract int getLayoutId();

	// ========================================
	// BaseLibraryFuns
	// ========================================
	@Override
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public <T extends View> T findView(int resId) {
		return (T) getView().findViewById(resId);
	}

	@Override
	public boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	@Override
	public boolean isEmpty(List list) {
		return list == null || list.isEmpty();
	}

	@Override
	public boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	@Override
	public void logError(Object message) {
		LogCat.e(4, message);
	}

	@Override
	public void logError(String explain, Object message) {
		LogCat.e(4, explain, message);
	}

	@Override
	public void logError(String explain, Object[] message) {
		LogCat.e(4, explain, message);
	}

	@Override
	public void logError(String explain, List message) {
		LogCat.e(4, explain, message);
	}
	// ========================================
	// 其他方法
	// ========================================

	/**
	 * 获取[Context]对象
	 */
	@SuppressWarnings("ConstantConditions")
	public Context getContext() {
		return getView().getContext();
	}
}
