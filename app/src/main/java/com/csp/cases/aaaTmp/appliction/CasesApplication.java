package com.csp.cases.aaaTmp.appliction;

import android.app.Application;
import android.content.Context;


/**
 * Description: Application
 * Create Date: 2017/7/18
 * Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class CasesApplication extends Application {
    // TODO 补充使用说明
    private static Context context;
//    private static DisplayMetrics metrics;

    public static Context getContext() {
        return context;
    }

//    @Override
//    public void onCreate() {
//        context = this;

//        metrics = MetricsUtil.getScreenParam(context);
//
//        // Common
//        HttpUtil.queue = Volley.newRequestQueue(context);
//        AConstant.context = this;
//    }


//    public static DisplayMetrics getMetrics() {
//        return metrics;
//    }
}
