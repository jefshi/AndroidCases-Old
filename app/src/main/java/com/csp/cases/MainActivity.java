package com.csp.cases;

import android.Manifest;
import android.os.Bundle;
import android.view.WindowManager;

import com.csp.cases.activity.AnimationActivity;
import com.csp.cases.activity.MetricsActivity;
import com.csp.cases.activity.permissions.PermissionActivity;
import com.csp.cases.activity.ProcessActivity;
import com.csp.cases.activity.SharedPreferencesActivity;
import com.csp.cases.activity.activity.ActivityActivity;
import com.csp.cases.activity.broadcast.BroadcastActivity;
import com.csp.cases.activity.intent.IntentActivity;
import com.csp.cases.activity.network.NetworkActivity;
import com.csp.cases.activity.storage.StorageActivity;
import com.csp.cases.activity.storage.database.SqliteActivity;
import com.csp.cases.activity.thread.ThreadActivity;
import com.csp.cases.activity.uicomponent.DialogActivity;
import com.csp.cases.activity.uicomponent.NotificationActivity;
import com.csp.cases.activity.uicomponent.ToastActivity;
import com.csp.cases.activity.view.ViewActivity;
import com.csp.cases.activity.view.ViewEventActivity;
import com.csp.cases.activity.windowmanager.WindowManagerActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.util.permissions.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseGridActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 获取权限
		String[] permissions = {
				Manifest.permission.WRITE_EXTERNAL_STORAGE
		};
		PermissionUtil.requestPermissions(this, permissions, 1000);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("Permission", PermissionActivity.class, "Android 6.0以上 动态权限获取案例"));
		items.add(new ItemInfo("Animation", AnimationActivity.class, "动画案例"));
		items.add(new ItemInfo("Broadcast", BroadcastActivity.class, "广播案例"));
		items.add(new ItemInfo("Dialog", DialogActivity.class, "对话框案例"));
		items.add(new ItemInfo("Notification", NotificationActivity.class, "状态栏通知案例"));
		items.add(new ItemInfo("Toast", ToastActivity.class, "Toast 通知案例"));
		items.add(new ItemInfo("Metrics", MetricsActivity.class, "度量以及屏幕参数案例"));
		items.add(new ItemInfo("Storage", StorageActivity.class, "文件存储案例"));
		items.add(new ItemInfo("SharedPreferences", SharedPreferencesActivity.class, "偏好设置案例"));
		items.add(new ItemInfo("View", ViewActivity.class, "View 案例"));
		items.add(new ItemInfo("View Event", ViewEventActivity.class, "View 事件体系案例: 待完成"));
		items.add(new ItemInfo("Databases", SqliteActivity.class, "数据库案例"));
		items.add(new ItemInfo("Process", ProcessActivity.class, "进程管理案例"));
		items.add(new ItemInfo("Intent", IntentActivity.class, "数据传输案例"));
		items.add(new ItemInfo("Thread", ThreadActivity.class, "线程管理案例"));
		items.add(new ItemInfo("Network", NetworkActivity.class, "网络案例"));
		items.add(new ItemInfo("Activity", ActivityActivity.class, "[Activity]案例"));
		items.add(new ItemInfo("WindowManager", WindowManagerActivity.class, "[Activity]案例"));

		return items;
	}
}
