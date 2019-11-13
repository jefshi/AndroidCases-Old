package com.project.day1026.service;

import com.common.android.control.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

/**
 * 服务写好后需要注册
 * 1) 清单配置文件注册: <application> -> <service>
 * @version 1.0
 * @author tarena
 * <p style='font-weight:bold'>Date:</p> 2016年10月27日 下午2:24:20
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class MixService extends Service {
	private HandlerThread	workThread;
	private Handler			workHandler;
	
	/**
	 * 获取本进程绑定的Service
	 * 有效: 绑定模式
	 * 使用: 在[onBind()]方法中返回[new LocalBinder()]
	 *  推荐通过继承接口实现相应方法
	 */
	public class LocalBinder extends Binder {
		public MixService getService() {
			return MixService.this;
		}
	}

	/**
	 * Service 创建时执行
	 * 有效: 启动模式、绑定模式
	 */
	@Override
	@SuppressLint("HandlerLeak")
	public void onCreate() {
		LogUtil.logInfo("onCreate()");

		// 启动工作线程
		workThread = new HandlerThread("workThread 01");
		workThread.start();

		// 启动消息队列
		workHandler = new Handler(workThread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				// 休眠[2s]
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 结束请求
				LogUtil.logInfo("通讯.finished: " + msg.obj + "." + msg.arg1);
				stopSelfResult(msg.arg1);
			}
		};

		super.onCreate();
	}

	/**
	 * Service 销毁时执行
	 * 有效: 启动模式、绑定模式
	 */
	@Override
	public void onDestroy() {
		LogUtil.logInfo("onDestroy()");

		// 中止工作线程
		workThread.quit();

		super.onDestroy();
	}

	/**
	 * Service 绑定时执行
	 * 有效: 绑定模式
	 * 
	 * 其他进程访问本进程的[Service]
	 * 1) 问题01: Handler 只能在进程内使用
	 * 2) 问题02: Message 只能在进程内使用
	 * 3) 解决: 通过[Messenger], 关联本进程的[Handler]
	 * 4) 方法: new Messenger(Handler target).getBinder();
	 */
	@Override
	@SuppressLint("HandlerLeak")
	public IBinder onBind(Intent intent) {
		LogUtil.logInfo("onBind()");

		// 当前进程的[Handler]
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				LogUtil.logInfo(msg.getData().getString("key"));
			};
		};
		
		// TODO
		// ...
		// PendingIntent.getService(context, requestCode, intent, flags)

		// return new LocalBinder(); // 用于返回绑定的Service
		return new Messenger(handler).getBinder(); // 用于远程进程通讯
	}

	/**
	 * Service 解绑时执行
	 * 有效: 绑定模式
	 */
	@Override
	public void unbindService(ServiceConnection conn) {
		LogUtil.logInfo("unbindService()");
		super.unbindService(conn);
	}

	@Override
	/**
	 * Service 解绑时执行
	 * 有效: 绑定模式
	 */
	public boolean onUnbind(Intent intent) {
		LogUtil.logInfo("onUnbind()");
		return super.onUnbind(intent);
	}

	/**
	 * 不推荐使用
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void onStart(Intent intent, int startId) {
		LogUtil.logInfo("onStart()");
		super.onStart(intent, startId);
	}

	/**
	 * Service 收到请求时执行
	 * 有效: 启动模式
	 * @param flags 表示启动方式
	 * @return int
	 *   1) START_NOT_STICKY           进程非正常中止后, 服务不会自动重启
	 *   2) START_STICKY               进程非正常中止后, 服务会自动重启, 但不恢复未完成的请求(intent)
	 *   3) START_STICKY_COMPATIBILITY [START_STICKY]兼容值, 进程非正常中止后, 服务不一定会自动重启
	 *   4) START_REDELIVER_INTENT     进程非正常中止后, 服务会自动重启, 并自动恢复未完成的请求(intent)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtil.logInfo("onStartCommand()" + "." + flags + "." + startId);

		String key = intent.getStringExtra("key");

		if ("runMoreRequest()".equals(key)) {
			// 发送请求消息
			Message msg = Message.obtain();
			msg.obj = "onStartCommand()." + flags;
			msg.arg1 = startId;
			workHandler.sendMessage(msg);
		}
		
		return START_REDELIVER_INTENT;
	}
}
