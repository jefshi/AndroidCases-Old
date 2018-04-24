package cc.rengu.src.kitkat.android.os;

/**
 * Description:
 * <p>Create Date: 2017/10/10
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class Looper {
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>(); // ？？？

	final MessageQueue mQueue; // 消息队列，通过[looper()]方法循环获取消息，并执行
	final Thread mThread; // 当前[Looper]所在线程

	private Looper(boolean quitAllowed) {
		mQueue = new MessageQueue(quitAllowed);
		mThread = Thread.currentThread();
	}

	/**
	 * Return the Looper object associated with the current thread.  Returns
	 * null if the calling thread is not associated with a Looper.
	 */
	public static Looper myLooper() {
		return sThreadLocal.get();
	}
}
