package cc.rengu.src.kitkat.android.os;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Looper;
import android.os.Process;

/**
 * Description: 自定义工作线程，模仿HandlerThread
 * <p>Create Date: 2017/9/7
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class HandlerThread extends Thread {
	private int priority; // 优先级
	private int tid; // 线程ID
	private Looper looper; // 消息迭代器(本身含消息队列)

	public HandlerThread(String name) {
		super(name);
		this.priority = Process.THREAD_PRIORITY_DEFAULT;
	}

	public HandlerThread(String name, int priority) {
		super(name);
		this.priority = priority;
	}

	@SuppressWarnings("EmptyCatchBlock")
	public Looper getLooper() {
		if (!isAlive()) // 工作线程是否激活
			return null;

		if (looper == null) {
			synchronized (this) {
				while (isAlive() && looper == null) {
					try {
						wait();
					} catch (InterruptedException e) {
						;
					}
				}
			}
		}
		return looper;
	}

	public int getThreadId() {
		return tid;
	}

	@Override
	public void run() {
		tid = Process.myTid(); // 获取线程ID
		Looper.prepare(); // 创建消息迭代器, 消息队列
		synchronized (this) {
			looper = Looper.myLooper(); // 获取消息队列
			notifyAll();
		}

		Process.setThreadPriority(priority); // 标准优先级
		onLooperPrepared(); // 补充动作
		Looper.loop(); // 死循环运行消息队列中的消息
		tid = -1; // 线程退出后
	}

	/**
	 * 补充动作
	 */
	protected void onLooperPrepared() {
	}

	/**
	 * 退出线程
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public boolean quit() {
		Looper looper = getLooper();
		if (looper != null) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				looper.quitSafely();
			} else {
				looper.quit();
			}
			return true;
		}
		return false;
	}
}