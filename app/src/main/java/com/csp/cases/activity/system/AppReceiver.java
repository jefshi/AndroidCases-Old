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

    /**
     * 注册广播监听器
     * filter.addDataScheme("package")：默认 android:scheme 假定为 content: 或 file:，所以无法匹配出结果
     */
    public static AppReceiver registerReceiver(Context context) {
        AppReceiver receiver = new AppReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addDataScheme("package");
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
        if (action == null)
            return;

        switch (action) {
            case Intent.ACTION_PACKAGE_ADDED:
            case Intent.ACTION_PACKAGE_REMOVED:
            case Intent.ACTION_PACKAGE_REPLACED:
                String packageName = null;
                if (intent.getData() != null)
                    packageName = intent.getData().getSchemeSpecificPart();

                LogCat.e(action + ", 包名为: " + packageName + ", Uri: " + intent.getDataString());
                break;
        }
    }
}
