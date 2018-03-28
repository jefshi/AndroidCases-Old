package com.project.day1028.activity;

import com.common.android.control.util.LogUtil;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ActivitySaveStateByMemory extends BaseActivity implements TabListener {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogUtil.logInfo("onCreate().savedInstanceState: " + savedInstanceState);

		// 设置ActionBar
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("Tab01").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Tab02").setTabListener(this));
	}

	/**
	 * 内存恢复页面状态, 保存的内容在[Bundle savedInstanceState]中
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		LogUtil.logInfo("onRestoreInstanceState()");
		super.onRestoreInstanceState(savedInstanceState);

		// ActionBar 内容恢复
		if (savedInstanceState != null) {
			int select = savedInstanceState.getInt("ActionBar");
			actionBar.setSelectedNavigationItem(select);
		}
	}

	/**
	 * 内存保存页面状态, 保存到[Bundle outState]
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		LogUtil.logInfo("onSaveInstanceState()");
		super.onSaveInstanceState(outState);

		// ActionBar 内容保存
		outState.putInt("ActionBar", actionBar.getSelectedNavigationIndex());
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}
