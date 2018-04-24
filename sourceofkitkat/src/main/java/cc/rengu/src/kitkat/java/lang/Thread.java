package cc.rengu.src.kitkat.java.lang;

import java.lang.*;

/**
 * Description:
 * <p>Create Date: 2017/10/10
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class Thread implements Runnable {
	ThreadLocal.Values localValues; // 当前线程绑定的[ThreadLocal]数据

	@Override
	public void run() {

	}

	/**
	 * native
	 */
	public static Thread currentThread() {
		return new Thread();
	}
}
