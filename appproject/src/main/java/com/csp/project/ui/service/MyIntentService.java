package com.project.day1026.service;

import com.common.android.control.util.LogUtil;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("WorkService()");
	}

	/**
	 * 消息处理器, 启动Service后自动执行
	 * 地位相当于[Handler.handleMessage()]
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		LogUtil.logInfo("onHandleIntent() start 2000");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogUtil.logInfo("onHandleIntent() end 2000");
	}

}
