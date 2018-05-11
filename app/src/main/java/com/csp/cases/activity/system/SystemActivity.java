package com.csp.cases.activity.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ListView;

import com.csp.cases.activity.system.appinfo.AppInfo;
import com.csp.cases.activity.system.appinfo.AppInfoAdapter;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description: 系统案例，包括应用扫描等
 * <p>Create Date: 2018/04/23
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class SystemActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("应用扫描(含系统应用)", "scanApple01", "速度慢是显示列表慢，并不是扫描慢"));
        items.add(new ItemInfo("应用扫描(含系统进程)", "scanApple02", "速度慢是显示列表慢，并不是扫描慢"));
        items.add(new ItemInfo("应用扫描(不含系统进程)", "scanApple03", "速度慢是显示列表慢，并不是扫描慢"));
        return items;
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

    @SuppressLint("WrongConstant")
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
        List<PackageInfo> packages = pManager.getInstalledPackages(0);

        List<AppInfo> apps = new ArrayList<>(packages.size());
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            ApplicationInfo info = packageInfo.applicationInfo;
            if (!((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0))
                continue;

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
}