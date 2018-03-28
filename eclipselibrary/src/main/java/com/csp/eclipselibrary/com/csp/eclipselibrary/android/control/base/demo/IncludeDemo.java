package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.demo;

import com.common.android.control.base.BaseInclude;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class IncludeDemo extends BaseInclude {

	public static IncludeDemo onAttach(Activity activity, Intent intent) {
		return new IncludeDemo(activity, null, intent);
	}

	public static IncludeDemo onAttach(View view, Intent intent) {
		return new IncludeDemo(null, view, intent);
	}

	protected IncludeDemo(Activity activity, View view, Intent intent) {
		super(activity, view, intent);
	}

	@Override
	public void initView() {
	}

	@Override
	public void initViewContent() {

	}

	@Override
	public void initViewEvent() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
