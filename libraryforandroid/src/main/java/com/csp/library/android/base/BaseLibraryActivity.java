package com.csp.library.android.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.csp.library.android.interfaces.InitialUi;

/**
 * Description: 用于 Activity 继承
 * <p>Create Date: 2016-12-15
 * <p>Modify Date: 2016-06-13
 *
 * @author csp
 * @version 1.0.2
 * @since AndroidLibrary 1.0.0
 */
public abstract class BaseLibraryActivity extends Activity implements InitialUi, BaseLibraryFuns {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();

		// 初始化UI
		initialUi();
	}

	@Override
	protected void onResume() {
		super.onResume();
		onRefresh();
	}

	@Override
	public void initialUi() {
		initBundle();
		initView();
		initViewContent();
		initViewEvent();
	}

	/**
	 * @link Activity.setContentView()
	 */
	protected abstract void setContentView();

	// ========================================
	// BaseLibraryFuns
	// ========================================
	@Override
	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int resId) {
		return (T) findViewById(resId);
	}

	@Override
	public View getView() {
		return getWindow().getDecorView();
	}
}