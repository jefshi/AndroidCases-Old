package com.csp.cases;

import android.Manifest;
import android.os.Bundle;

import com.csp.cases.activity.AnimationActivity;
import com.csp.cases.activity.MetricsActivity;
import com.csp.cases.activity.OtherCaseActivity;
import com.csp.cases.activity.ProcessActivity;
import com.csp.cases.activity.SettingActivity;
import com.csp.cases.activity.TestActivity;
import com.csp.cases.activity.activity.ActivityActivity;
import com.csp.cases.activity.component.ComponentActivity;
import com.csp.cases.activity.intent.IntentActivity;
import com.csp.cases.activity.network.NetworkActivity;
import com.csp.cases.activity.permissions.PermissionActivity;
import com.csp.cases.activity.storage.StorageActivity;
import com.csp.cases.activity.system.SystemActivity;
import com.csp.cases.activity.thread.ThreadActivity;
import com.csp.cases.activity.view.ViewActivity;
import com.csp.cases.activity.windowmanager.WindowManagerActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.permissions.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGridActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取权限
        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        PermissionUtil.requestPermissions(this, permissions, 1000);
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("Test", TestActivity.class, ""));
        items.add(new ItemInfo("Activity", ActivityActivity.class, "[Activity]案例"));
        items.add(new ItemInfo("Animation", AnimationActivity.class, "动画案例"));
        items.add(new ItemInfo("Component", ComponentActivity.class, "组件/控件案例"));
        items.add(new ItemInfo("Intent", IntentActivity.class, "意图案例"));
        items.add(new ItemInfo("Metrics", MetricsActivity.class, "度量以及屏幕参数案例"));
        items.add(new ItemInfo("Network", NetworkActivity.class, "网络案例"));
        items.add(new ItemInfo("Permission", PermissionActivity.class, "权限案例"));
        items.add(new ItemInfo("Process", ProcessActivity.class, "进程管理案例"));
        items.add(new ItemInfo("Setting", SettingActivity.class, "设置功能案例"));
        items.add(new ItemInfo("Storage", StorageActivity.class, "存储案例"));
        items.add(new ItemInfo("System", SystemActivity.class, "系统案例"));
        items.add(new ItemInfo("Thread", ThreadActivity.class, "线程管理案例"));
        items.add(new ItemInfo("View", ViewActivity.class, "View 案例"));
        items.add(new ItemInfo("WindowManager", WindowManagerActivity.class, "[WindowManager]案例"));
        items.add(new ItemInfo("OtherCase", OtherCaseActivity.class, "[其他]案例"));
        return items;
    }
}
