package com.csp.utils.android;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
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
 * 应用包管理
 * Created by csp on 2017/04/23.
 * Modified by csp on 2018/06/13.
 *
 * @version 1.0.5
 */
public class AppUtil {

    private AppUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 当前应用是否处在前台
     *
     * @return true: 是
     */
    public static boolean isAppForeground() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }

    /**
     * 获取包名集合
     *
     * @param packageInfos 应用信息集合
     * @return 包名集合
     */
    public static List<String> getPackageNames(List<PackageInfo> packageInfos) {
        if (packageInfos == null)
            return new ArrayList<>();

        List<String> packageNames = new ArrayList<>();
        for (PackageInfo packageInfo : packageInfos)
            packageNames.add(packageInfo.packageName);

        return packageNames;
    }

    /**
     * 应用扫描(只有非系统应用)
     *
     * @param context context
     * @return 手机内的非系统应用
     */
    public static List<PackageInfo> getAppNotSystem(Context context) {
        PackageManager pManager = context.getPackageManager();
        List<PackageInfo> packageInfos = pManager.getInstalledPackages(0);
        int position = packageInfos == null ? -1 : (packageInfos.size() - 1);
        for (int i = position; i > -1; i--) {
            ApplicationInfo info = packageInfos.get(i).applicationInfo;
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
                packageInfos.remove(i);
        }
        return packageInfos;
    }

    /**
     * 应用扫描(含系统应用)
     *
     * @param context context
     * @return 手机内的非系统应用
     */
    public static List<ResolveInfo> getAppSystem(Context context) {
        PackageManager pManager = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return pManager.queryIntentActivities(mainIntent, 0);
    }

    /**
     * 应用扫描(含系统进程)
     *
     * @param context context
     * @return 手机内的非系统应用
     */
    @SuppressLint("WrongConstant")
    public static List<ApplicationInfo> getAppSystemProcess(Context context) {
        PackageManager pManager = context.getPackageManager();
        return pManager.getInstalledApplications(
                PackageManager.GET_UNINSTALLED_PACKAGES |
                        PackageManager.GET_DISABLED_COMPONENTS);
    }

    /**
     * 是否存在指定的应用
     *
     * @param packageName 包名
     * @return null: 不存在指定的应用
     */
    public static ResolveInfo searchApplication(String packageName) {
        Context context = Utils.getAppContext();
        if (EmptyUtil.isBank(packageName) || context == null)
            return null;

        Intent searchIntent = new Intent(Intent.ACTION_MAIN, null);
        searchIntent.setPackage(packageName);

        PackageManager pManager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(searchIntent, 0);
        return EmptyUtil.isEmpty(resolveInfos) ? null : resolveInfos.get(0);
    }

    /**
     * 通过包名启动 App
     *
     * @param packageName 包名
     * @return true: 成功
     */
    public static boolean startAppByPackageName(String packageName) {
        Context context = Utils.getAppContext();
        ResolveInfo resolveInfo = searchApplication(packageName);
        if (resolveInfo == null || context == null)
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
     * step 3: res/xml 下 <provider><meta-data> 指定的 XML 文件
     *
     * @param file apk 文件路径
     * @return true: 成功
     */
    // @RequiresPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public static boolean installApk(File file) {
        if (!file.exists())
            return false;

        Context context = Utils.getAppContext();
        if (context == null)
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

    /**
     * 卸载应用
     *
     * @param context     context
     * @param packageName 包名
     */
    public static void uninstallApp(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }
}
