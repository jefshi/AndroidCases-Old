package com.csp.cases.activity.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csp.cases.BuildConfig;
import com.csp.cases.activity.system.appinfo.AppInfo;
import com.csp.cases.activity.system.appinfo.AppInfoAdapter;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.AppInfoUtils;
import com.csp.utils.android.AppUtil;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 系统案例，包括应用扫描等
 * <p>Create Date: 2018/04/23
 * <p>Modify Date: nothing
 * <p>
 * 1) 安装应用：通过隐式意图调用系统安装程序安装APK
 * step 1: 见代码
 * step 2: AndroidManifest.xml 文件下 <provider> 标签
 * step 3: res/xml 下 file_provider_paths.xmll 文件
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class SystemActivity extends BaseListActivity {
    private AppReceiver receiver;

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("当前应用的 ApplicationInfo 信息", "showApplicationInfo", ""));
        items.add(new ItemInfo("应用扫描(含系统应用)", "scanApple01", "速度慢是显示列表慢，并不是扫描慢"));
        items.add(new ItemInfo("应用扫描(含系统进程)", "scanApple02", "速度慢是显示列表慢，并不是扫描慢"));
        items.add(new ItemInfo("应用扫描(只有非系统应用)", "scanApple03", "速度慢是显示列表慢，并不是扫描慢"));
        items.add(new ItemInfo("应用安装卸载监听", "appInstallListen", ""));
        items.add(new ItemInfo("安装应用", "installApp", ""));
        items.add(new ItemInfo("通过包名启动应用", "startApp", ""));
        items.add(new ItemInfo("获取设备硬件信息", "getHardInfo", ""));
        return items;
    }

    @Override
    protected void onDestroy() {
        AppReceiver.unregisterReceiver(this, receiver);
        receiver = null;

        super.onDestroy();
    }

    private void showApplicationInfo() {
        try {
            PackageManager pManager = getPackageManager();
            ApplicationInfo appInfo = pManager.getApplicationInfo(BuildConfig.APPLICATION_ID, 0);
            LogCat.e("当前应用的 apk 安装包目录", appInfo.sourceDir);
            LogCat.e("当前应用的 apk 安装包目录", appInfo.publicSourceDir);
        } catch (PackageManager.NameNotFoundException e) {
            LogCat.printStackTrace(e);
        }
    }

    private void showAppList(List<AppInfo> appInfos) {
        ListView lsv = new ListView(this);
        lsv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lsv.setAdapter(new AppInfoAdapter(this, appInfos));

        lfrItem.removeAllViews();
        lfrItem.addView(lsv);

        LogCat.e(appInfos);
    }

    private void scanApple01() {
        PackageManager pManager = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pManager));

        List<AppInfo> appInfos = new ArrayList<>();
        for (ResolveInfo reInfo : resolveInfos) {
            ActivityInfo activityInfo = reInfo.activityInfo;
            String packageName = activityInfo.packageName;
            String appLabel = (String) reInfo.loadLabel(pManager);
            Drawable icon = reInfo.loadIcon(pManager);

            AppInfo app = new AppInfo();
            app.setLabel(appLabel);
            app.setPackageName(packageName);
            app.setIcon(icon);
            appInfos.add(app);
        }

        showAppList(appInfos);
    }

    private void scanApple02() {
        PackageManager pManager = this.getPackageManager();
        List<ApplicationInfo> applications = pManager.getInstalledApplications(
                PackageManager.GET_UNINSTALLED_PACKAGES |
                        PackageManager.GET_DISABLED_COMPONENTS);
        if (applications == null) {
            applications = new ArrayList<>();
        }

        List<AppInfo> apps = new ArrayList<>(applications.size());
        for (int i = 0; i < applications.size(); i++) {
            ApplicationInfo info = applications.get(i);
            AppInfo app = new AppInfo();
            app.setPackageName(info.packageName);
            app.setLabel(info.loadLabel(getPackageManager()).toString());
            app.setIcon(info.loadIcon(pManager));

            int versionCode = AppInfoUtils.getVersionCode(this, info.packageName);
            app.setVersionCode(versionCode);
            apps.add(app);
        }

        Collections.sort(apps, new Comparator<AppInfo>() {
            private final Collator sCollator = Collator.getInstance();

            @Override
            public int compare(AppInfo object1, AppInfo object2) {
                return sCollator.compare(object1.getLabel(), object2.getLabel());
            }
        });

        showAppList(apps);
    }

    @SuppressLint("WrongConstant")
    private void scanApple03() {
        PackageManager pManager = this.getPackageManager();
//        List<PackageInfo> packages = pManager.getInstalledPackages(0);

        List<PackageInfo> packages = AppUtil.getAppNotSystem(this);
        List<AppInfo> apps = new ArrayList<>(packages.size());
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            ApplicationInfo info = packageInfo.applicationInfo;
//            if (!((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0))
//                continue;

            AppInfo app = new AppInfo();
            app.setPackageName(packageInfo.packageName);
            app.setLabel(info.loadLabel(getPackageManager()).toString());
            app.setIcon(info.loadIcon(pManager));
            apps.add(app);
        }

        Collections.sort(apps, new Comparator<AppInfo>() {
            private final Collator sCollator = Collator.getInstance();

            @Override
            public int compare(AppInfo object1, AppInfo object2) {
                return sCollator.compare(object1.getLabel(), object2.getLabel());
            }
        });

        showAppList(apps);
    }

    /**
     * 应用安装卸载监听
     */
    private void appInstallListen() {
        if (receiver == null) {
            receiver = AppReceiver.registerReceiver(this);
        } else {
            AppReceiver.unregisterReceiver(this, receiver);
            receiver = null;
        }

        LogCat.e(receiver == null ? "解除注册" : "注册");
    }

    /**
     * 安装应用：通过隐式意图调用系统安装程序安装APK
     * step 1: 见代码
     * step 2: AndroidManifest.xml 文件下 <provider> 标签
     * step 3: res/xml 下 file_provider_paths.xml 文件
     */
    private void installApp() throws Exception {
        File file = Environment.getExternalStorageDirectory();
        file = new File(file, "/APK/app-debug.apk");
        if (!AppUtil.installApk(file)) {
            throw new Exception("可能是文件不存在导致失败，文件: " + file.getAbsolutePath());
        }
    }

    /**
     * 通过包名启动 App
     */
    private void startApp() throws Exception {
        // 360 浏览器
        String packageName = "com.qihoo.browser";
        if (!AppUtil.startAppByPackageName(packageName)) {
            throw new Exception("可能是应用不存在导致失败，包名: " + packageName);
        }
    }

    /**
     * 获取设备硬件信息
     */
    @SuppressLint("MissingPermission")
    private void getHardInfo() {
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNum = manager.getLine1Number();
        LogCat.e(phoneNum);
    }
}
