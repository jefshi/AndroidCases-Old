package com.csp.cases;

import android.app.Application;

import com.csp.utils.android.log.LogCat;


/**
 * Description: Application
 * Create Date: 2017/7/18
 * Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class CaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogCat.e("Application.onCreate");
    }

    @Override
    public void onTerminate() {
        LogCat.e("Application.onTerminate");

        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        LogCat.e("Application.onLowMemory(低内存)");

        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        LogCat.e("Application.onTrimMemory(清理内存)");

        super.onTrimMemory(level);
    }
}
