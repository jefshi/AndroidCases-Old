package com.csp.cases.activity.system;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
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
        items.add(new ItemInfo("应用扫描(含系统应用)", "scanApple01", ""));
        items.add(new ItemInfo("应用扫描(不含系统应用)", "scanApple02", ""));
        return items;
    }

    private void scanApple01() {
        PackageManager pManager = this.getPackageManager(); // 获得PackageManager对象
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pManager));

        List<AppInfo> appInfos = new ArrayList<>();
        for (ResolveInfo reInfo : resolveInfos) {
            String packageName = reInfo.activityInfo.packageName;
            String appLabel = (String) reInfo.loadLabel(pManager);
            Drawable icon = reInfo.loadIcon(pManager);

            AppInfo app = new AppInfo();
            app.setLabel(appLabel);
            app.setPackageName(packageName);
            app.setIcon(icon);
            appInfos.add(app);
        }

        ListView lsv = new ListView(this);
        lsv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lsv.setAdapter(new AppInfoAdapter(this, appInfos));

        lfrItem.removeView(lsv);
        lfrItem.addView(lsv);

        LogCat.e(appInfos);
    }

    private void scanApple02() {

    }
}
