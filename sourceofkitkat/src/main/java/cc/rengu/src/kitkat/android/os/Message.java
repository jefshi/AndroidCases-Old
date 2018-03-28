package cc.rengu.src.kitkat.android.os;

import android.os.Handler;

/**
 * Description:
 * <p>Create Date: 2017/10/10
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class Message {
	Handler target; // 发送消息的[Handler]对象
	Runnable callback; // 消息执行内容
}
