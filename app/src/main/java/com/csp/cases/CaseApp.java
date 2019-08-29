package com.csp.cases;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.csp.utils.android.Utils;
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

    private volatile static CaseApp sApplication;
    private volatile static Context sContext;

    public static Context getContext() {
        return sContext != null ? sContext : getApplication().getApplicationContext();
    }

    public static CaseApp getApplication() {
        if (sApplication != null)
            return sApplication;

        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object at = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(at);
            sApplication = (CaseApp) app;
        } catch (Exception e) {
            LogCat.printStackTrace(e);
        }
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        sContext = getApplicationContext();
        Utils.init(this);

        LogCat.e("Application.onCreate");
    }

    @Override
    public void onTerminate() {
        LogCat.e("Application.onTerminate");

        sApplication = null;
        sContext = null;
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
