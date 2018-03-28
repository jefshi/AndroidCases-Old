package com.csp.library.android.util.log;

import android.util.Log;

import com.csp.library.android.constants.SystemConstant;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ListIterator;

/**
 * Description: 日志打印
 * <p>Create Date: 2017/7/14
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LogCat {
	private static final boolean DEBUG = SystemConstant.LOG_DEBUG;
	private static final int LOG_MAX_LENGTH = 4096; // Android 能够打印的最大日志长度

	/**
	 * 获取日志标签, 例: --[类名][方法名]
	 *
	 * @param element 追踪栈元素
	 * @return 日志标签
	 */
	private static String getTag(StackTraceElement element) {
		String className = element.getMethodName();
		String methodName = element.getMethodName();
		String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
		return "--[" + simpleClassName + "][" + methodName + ']';
	}

	/**
	 * 日志内容分割
	 *
	 * @param message 日志内容
	 * @return 分割后的日志
	 */
	@SuppressWarnings("UnusedAssignment")
	private static String[] divideMessages(String message) {
		String partMessage = null;
		int index = -1;
		String[] list = new String[message.length() / LOG_MAX_LENGTH + 1];
		for (int i = 0; i < list.length; i++) {
			if (message.length() <= LOG_MAX_LENGTH) {
				list[i] = message;
				continue;
			}

			partMessage = message.substring(0, LOG_MAX_LENGTH);
			index = partMessage.lastIndexOf('\n');
			if (index > -1) {
				list[i] = message.substring(0, index);
				message = message.substring(index + 1);
			} else {
				list[i] = partMessage;
				message = message.substring(LOG_MAX_LENGTH);
			}
		}
		return list;
	}

	/**
	 * 打印日志
	 *
	 * @param tag     日志标签
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void printLog(String tag, String message, int level) {
		String[] messages = divideMessages(message);
		for (String msg : messages) {
			switch (level) {
				case Log.ERROR:
					Log.e(tag, msg);
					break;
				case Log.WARN:
					Log.w(tag, msg);
					break;
				case Log.INFO:
					Log.i(tag, msg);
					break;
				case Log.DEBUG:
					Log.d(tag, msg);
					break;
				default:
					Log.v(tag, msg);
					break;
			}
		}
	}

	/**
	 * 打印异常信息
	 *
	 * @param exception 异常错误对象
	 */
	public static void printStackTrace(Exception exception) {
		if (exception != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			exception.printStackTrace(new PrintStream(baos));

			String tag = getTag(exception.getStackTrace()[0]);
			printLog(tag, baos.toString(), Log.ERROR);
		}
	}

	/**
	 * 打印日志(生成[TAG)
	 *
	 * @param stackId 异常栈序号, 用于获取日志标签
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void log(int level, int stackId, String message) {
		String tag = getTag(new Exception().getStackTrace()[stackId]);
		printLog(tag, message, level);
	}

	/**
	 * 打印日志(生成[Message])
	 *
	 * @param stackId 异常栈序号, 用于获取日志标签
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void log(int stackId, Object message, int level) {
		if (DEBUG)
			log(level, stackId, String.valueOf(message));
	}

	/**
	 * 打印日志(生成[Message])
	 *
	 * @param stackId 异常栈序号, 用于获取日志标签
	 * @param explain 日志说明
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void log(int stackId, String explain, Object message, int level) {
		if (DEBUG)
			log(level, stackId, explain + String.valueOf(message));
	}

	/**
	 * 打印日志(生成[Message])
	 *
	 * @param stackId 异常栈序号, 用于获取日志标签
	 * @param explain 日志说明
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void log(int stackId, String explain, Object[] message, int level) {
		if (DEBUG) {
			String messages = "";
			if (message == null || message.length == 0) {
				messages += ": null";
			} else {
				for (int i = 0; i < message.length; i++) {
					messages += '\n' + explain + "[" + i + "]: " + String.valueOf(message[i]);
				}
				messages = messages.substring(1);
			}
			log(level, stackId, messages);
		}
	}

	/**
	 * 打印日志(生成[Message])
	 *
	 * @param stackId 异常栈序号, 用于获取日志标签
	 * @param explain 日志说明
	 * @param message 日志内容
	 * @param level   日志优先级
	 */
	private static void log(int stackId, String explain, List message, int level) {
		if (DEBUG) {
			String messages = "";
			if (message == null || message.isEmpty()) {
				messages += ": null";
			} else {
				ListIterator iterator = message.listIterator();
				for (int i = 0; iterator.hasNext(); i++) {
					messages += '\n' + explain + "[" + i + "]: " + String.valueOf(iterator.next());
				}
				messages = messages.substring(1);
			}
			log(level, stackId, messages);
		}
	}

	/**
	 * 打印日志
	 *
	 * @see #log(int, Object, int)
	 */
	public static void e(int stackId, Object message) {
		log(stackId, message, Log.ERROR);
	}

	/**
	 * 打印日志
	 *
	 * @see #log(int, String, Object, int)
	 */
	public static void e(int stackId, String explain, Object message) {
		log(stackId, explain, message, Log.ERROR);
	}

	/**
	 * 打印日志
	 *
	 * @see #log(int, String, Object[], int)
	 */
	public static void e(int stackId, String explain, Object[] message) {
		log(stackId, explain, message, Log.ERROR);
	}

	/**
	 * 打印日志
	 *
	 * @see #log(int, String, List, int)
	 */
	public static void e(int stackId, String explain, List message) {
		log(stackId, explain, message, Log.ERROR);
	}

	/**
	 * 打印日志
	 *
	 * @see #e(int, Object)
	 */
	public static void e(Object message) {
		e(4, message);
	}

	/**
	 * 打印日志
	 *
	 * @see #e(int, String, Object)
	 */
	public static void e(String explain, Object message) {
		e(4, explain, message);
	}

	/**
	 * 打印日志
	 *
	 * @see #e(int, String, Object[])
	 */
	public static void e(String explain, Object[] message) {
		e(4, explain, message);
	}

	/**
	 * 打印日志
	 *
	 * @see #e(int, String, List)
	 */
	public static void e(String explain, List message) {
		e(4, explain, message);
	}
}