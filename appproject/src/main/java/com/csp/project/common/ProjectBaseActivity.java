package com.csp.project.common;

import android.content.pm.ActivityInfo;

import com.csp.project.common.base.BaseActivity;

/**
 * Created by csp on 2017/4/12 012.
 */
public abstract class ProjectBaseActivity extends BaseActivity {

    @Override
    protected void onResume() {
        /**
         * 设置横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
