package com.csp.utils.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Description: 当前应用信息
 * <p>Create Date: 2018/05/02
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class AppUtils {
    /**
     * 通过包名启动 App
     *
     * @param context     context
     * @param packageName 包名
     */
    public static void startAppByPackageName(Context context, String packageName) {
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageName);

        PackageManager pManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(resolveIntent, 0);
        if (resolveInfos == null)
            return;

        for (ResolveInfo resolveInfo : resolveInfos) {
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveInfo.activityInfo.name;

            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName componentName = new ComponentName(packageName, className);

            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }
}
