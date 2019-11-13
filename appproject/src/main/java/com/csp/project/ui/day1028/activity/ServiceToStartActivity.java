package com.project.day1028.activity;

import android.app.IntentService;
import android.content.Intent;

public class ServiceToStartActivity extends IntentService {
	public ServiceToStartActivity() {
		super("ServiceToStartActivity");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// Intent target = new Intent(this, ActivityLauchModeSingletask.class);
		Intent target = new Intent("my.android.app.ActivityLauchModeSingletask");
		
		// 其他组件启动[Activity], 需要执行以下方法
		target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		String tip = "";
		tip += "\n使用[Service]启动[Activity]成功, 若不创建新[Activity]对象, 则调用[onNewIntent()]方法";
		target.putExtra("Info", tip);
		startActivity(target);
	}
}
