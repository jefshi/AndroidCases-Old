package com.csp.cases.activity.activity;

import android.os.Bundle;

import com.csp.cases.base.activity.BaseListActivity;

/**
 * Description:
 * <p>Create Date: 2017/12/26 026
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public abstract class LifeCycleActivity extends BaseListActivity {

	@Override
	public String toString() {
		return '[' + Integer.toHexString(hashCode()) + "][" + getClass().getSimpleName() + ']';
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		logError(toString() + "生命周期：onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();

		logError(toString() + "生命周期：onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();

		logError(toString() + "生命周期：onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();

		logError(toString() + "生命周期：onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();

		logError(toString() + "生命周期：onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		logError(toString() + "生命周期：onDestroy");
	}
}