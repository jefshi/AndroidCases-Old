package com.csp.cases.activity.view.arcmenu;

import android.view.WindowManager;

/**
 * Created by csp on 2018/4/2 002.
 */

public interface IWindowLayoutParams {
    /**
     * initialize WindowManager.LayoutParams
     */
    void initWindowLayoutParams();

    WindowManager.LayoutParams getWindowLayoutParams();

    void setWindowLayoutParams(int x, int y);
}
