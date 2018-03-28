package com.csp.library.android.base.demo;

import android.view.View;
import android.widget.TextView;

import com.csp.library.android.base.BaseLibraryActivity;

/**
 * Description: Activity Demo
 * <p>Create Date: 2016-06-14
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BaseActivityDemo extends BaseLibraryActivity implements View.OnClickListener {
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

	/**
	 * 获取[TextView]类控件值
	 *
	 * @param edtId [TextView]类控件ID
	 * @return 控件值
	 */
	protected String getViewText(int edtId) {
		View view = findViewById(edtId);
		if (view instanceof TextView) {
			return String.valueOf(((TextView) view).getText());
		} else {
			return "";
		}
	}
}
