package com.csp.cases.activity.thread;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.util.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 线程案例
 * <p>Create Date: 2017/9/5
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class ThreadActivity extends BaseGridActivity {
	char taskName = 'A';

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("工作线程使用", "useHandlerThread", "方法01: 使用[HandlerThread]\n方法02：模仿[HandlerThread]自定义工作线程"));
		itemInfos.add(new ItemInfo("异步任务(串行)", "asyncTaskSerial", ""));
		itemInfos.add(new ItemInfo("异步任务(并行)", "asyncTaskParallel", ""));
		itemInfos.add(new ItemInfo("Rxjava", RxjavaActivity.class, ""));
		return itemInfos;
	}

	/**
	 * 工作线程使用
	 */
	private void useHandlerThread() {
		// 启动工作线程
		// final CustomThread work = new CustomThread(this.getClass().getSimpleName());
		final HandlerThread work = new HandlerThread(this.getClass().getSimpleName());
		work.start();
		logError("线程开始，线程名：" + work.getName() + "：线程ID：" + work.getThreadId());

		// 创建Handler，主线程：getMainLooper()
		// [Handler]绑定线程, 线程绑定[Looper], [Looper]死循环, 故使用静态内部类，防止内存泄漏
		Handler handler = new TaskHandler(work.getLooper());

		// 执行任务
		final Message msg = Message.obtain();
		msg.what = 1;
		msg.obj = new TaskContent(taskName++, handler);
		handler.sendMessage(msg);

		// 结束工作线程
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				work.quit();
				logError("线程结束，线程名：" + work.getName() + "：线程ID：" + work.getThreadId());
			}
		}, 0);
	}

	private void asyncTaskSerial() {
		CustomAsyncTask task = new CustomAsyncTask();
		task.execute(taskName++);

		task = new CustomAsyncTask();
		task.execute(taskName++);

		task = new CustomAsyncTask();
		task.execute(taskName++);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void asyncTaskParallel() {
		CustomAsyncTask task = new CustomAsyncTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, taskName++);

		task = new CustomAsyncTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, taskName++);

		task = new CustomAsyncTask();
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, taskName++);
	}

	/**
	 * [Handler]绑定线程, 线程绑定[Looper], [Looper]死循环, 故使用静态内部类，防止内存泄漏
	 */
	private static class TaskHandler extends Handler {

		public TaskHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				String name = (String) msg.obj;
				getRefreshUITask(name).run();
			}

			if (msg.what == 1) {
				((Runnable) msg.obj).run();
			}
		}
	}

	/**
	 * 任务模板
	 */
	private class TaskContent implements Runnable {
		private String taskName;
		private Handler handler;

		public TaskContent(char taskName, Handler handler) {
			this.taskName = "任务(" + taskName + ')';
			this.handler = handler;
		}

		@Override
		public void run() {
			try {
				LogCat.e(taskName + "开始，线程名：" + Thread.currentThread().getName());
				Thread.sleep(1000);
				LogCat.e(taskName + "结束，线程名：" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				LogCat.printStackTrace(e);
			}

			// 更新UI方法01
			if (handler != null) {
				Message msg = Message.obtain();
				msg.what = 0;
				msg.obj = taskName;
				handler.sendMessage(msg);
			}

			// 更新UI方法02
			runOnUiThread(getRefreshUITask(taskName));
		}
	}

	/**
	 * 更新UI
	 */
	private static Runnable getRefreshUITask(String name) {
		return new Runnable() {
			@Override
			public void run() {
				LogCat.e("两种方法更新UI，线程名(主线程)：" + Thread.currentThread().getName());
			}
		};
	}
}
