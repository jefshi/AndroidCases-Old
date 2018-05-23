package com.csp.utils.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.csp.library.java.EmptyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 应用包管理
 * <p>Create Date: 2018/04/23
 * <p>Modify Date: 2018/05/23
 *
 * @author csp
 * @version 1.0.3
 * @since AndroidUtils 1.0.0
 */
public class AppUtil {

    /**
     * 扫描手机内的非系统应用
     *
     * @param context context
     * @return 手机内的非系统应用
     */
    public static List<String> getPackageNames(Context context) {
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> packages = pManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            ApplicationInfo info = packageInfo.applicationInfo;
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                packageNames.add(packageInfo.packageName);
        }
        return packageNames;
    }

    /**
     * 是否存在指定的应用
     *
     * @param context     context
     * @param packageName 包名
     * @return null: 不存在指定的应用
     */
    public static ResolveInfo searchApplication(Context context, String packageName) {
        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageName);

        PackageManager pManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(resolveIntent, 0);
        return EmptyUtil.isEmpty(resolveInfos) ? null : resolveInfos.get(0);
    }

    /**
     * 通过包名启动 App
     *
     * @param context     context
     * @param packageName 包名
     * @return true: 成功
     */
    public static boolean startAppByPackageName(Context context, String packageName) {
        ResolveInfo resolveInfo = searchApplication(context, packageName);
        if (resolveInfo == null)
            return false;

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
        return true;
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     * step 1: 见代码
     * step 2: AndroidManifest.xml 文件下 <provider> 标签
     * step 3: res/xml 下 file_provider_paths.xml 文件
     *
     * @param context context
     * @param file    apk 文件路径
     * @return true: 成功
     */
    public static boolean installApk(Context context, File file) {
        if (!file.exists())
            return false;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 参数 2（authority），要与 <provider> 标签的 authorities 属性保持
            String authority = context.getPackageName() + ".fileProvider";
            data = FileProvider.getUriForFile(context, authority, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 文件权限
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
        return true;
    }
}
