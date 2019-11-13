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
public class Handler {
	final Callback mCallback; // ???
	final Looper mLooper; // 唯一绑定的[Looper]对象
	final MessageQueue mQueue; // 绑定的[Looper]对象的消息队列

	/**
	 * @param callback: {@link Callback}
	 * @param async
	 */
	public Handler(Callback callback, boolean async) {
		mLooper = Looper.myLooper();
		mQueue = mLooper.mQueue;
		mCallback = callback;
	}

	/**
	 * 允许不派生[Handler]的子类，创建[Handler]对象
	 */
	public Handler(Callback callback) {
		this(callback, false);
	}

	/**
	 * Handle system messages here.
	 * 执行通过[send][post]系列方法发送过来的消息
	 * 在[Looper]对象所在线程中运行，详见[Looper.loop()]
	 * 执行顺序：要么[Message.Runnable.run()]，要么[Handler.Callback.handleMessage()]，要么[Handler.handleMessage()]
	 */
	public void dispatchMessage(Message msg) {
		if (msg.callback != null) {
			handleCallback(msg);
		} else {
			if (mCallback != null) {
				if (mCallback.handleMessage(msg)) {
					return;
				}
			}
			handleMessage(msg);
		}
	}

	/**
	 * 处理/执行消息
	 */
	private static void handleCallback(Message message) {
		message.callback.run();
	}

	/**
	 * Callback interface you can use when instantiating a Handler to avoid
	 * having to implement your own subclass of Handler.
	 * 当不想通过派生[Handler]的子类创建[Handler]对象时，可以通过该接口创建[Handler]对象
	 */
	public interface Callback {
		boolean handleMessage(Message msg);
	}

	/**
	 * Subclasses must implement this to receive messages.
	 * [Handler]子类/匿名类对象必须实现的消息处理方法
	 */
	public void handleMessage(Message msg) {
	}
}
