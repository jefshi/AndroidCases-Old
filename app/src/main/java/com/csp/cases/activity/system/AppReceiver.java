package com.csp.cases.activity.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.csp.utils.android.log.LogCat;

/**
 * Description: 应用安装、卸载、更新监听
 * <p>Create Date: 2018/05/22
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class AppReceiver extends BroadcastReceiver {
    private static final String PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    private static final String PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    private static final String PACKAGE_REPLACED = "android.intent.action.PACKAGE_REPLACED";

    /**
     * 注册广播监听器
     */
    public static AppReceiver registerReceiver(Context context) {
        AppReceiver receiver = new AppReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(PACKAGE_ADDED);
        filter.addAction(PACKAGE_REMOVED);
        filter.addAction(PACKAGE_REPLACED);
        context.registerReceiver(receiver, filter);
        return receiver;
    }

    /**
     * 解除注册广播监听器
     */
    public static void unregisterReceiver(Context context, AppReceiver receiver) {
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        String packageName = null;
        if (intent.getData() != null)
            packageName = intent.getData().getSchemeSpecificPart();

        LogCat.e(action + ", 包名为 : " + packageName);
    }
}
