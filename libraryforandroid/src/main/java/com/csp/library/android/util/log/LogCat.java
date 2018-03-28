package com.csp.library.android.util.log;

import android.support.annotation.NonNull;
import android.util.Log;

import com.csp.library.android.constants.SystemConstant;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: 日志打印
 * <p>Create Date: 2017/7/14
 * <p>Modify Date: 2018/03/28
 *
 * @author csp
 * @version 1.0.3
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LogCat {
    private static final boolean DEBUG = SystemConstant.LOG_DEBUG;
    private static final int LOG_MAX_LENGTH = 4096; // Android 能够打印的最大日志长度
    public static final int DEFAULT_STACK_ID = 2;

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
     * 格式化[Message]
     *
     * @param explain  日志说明
     * @param messages 日志内容
     */
    private static String formatLog(@NonNull String explain, Object[] messages) {
        StringBuilder log = new StringBuilder();
        if (messages.length == 0) {
            log.append(": null");
        } else {
            for (int i = 0; i < messages.length; i++) {
                log.append('\n').append(explain)
                        .append("[").append(i).append("]: ")
                        .append(messages[i]);
            }
            log.deleteCharAt(0);
        }
        return log.toString();
    }

    /**
     * 格式化[Message]
     *
     * @param explain  日志说明
     * @param messages 日志内容
     */
    private static String formatLog(@NonNull String explain, Collection messages) {
        StringBuilder log = new StringBuilder();
        if (messages.isEmpty()) {
            log.append(": null");
        } else {
            Iterator iterator = messages.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                log.append('\n').append(explain)
                        .append("[").append(i).append("]: ")
                        .append(iterator.next());
            }
            log.deleteCharAt(0);
        }
        return log.toString();
    }

    /**
     * 格式化[Message]
     *
     * @param explain  日志说明
     * @param messages 日志内容
     */
    private static String formatLog(@NonNull String explain, Map messages) {
        StringBuilder log = new StringBuilder();
        if (messages.isEmpty()) {
            log.append(": null");
        } else {
            Set keys = messages.keySet();
            int i = 0;
            for (Object key : keys) {
                log.append('\n').append(explain)
                        .append("[")
                        .append(i).append(", ").append(key)
                        .append("]: ")
                        .append(messages.get(key));
                i++;
            }
            log.deleteCharAt(0);
        }
        return log.toString();
    }

    /**
     * 打印日志(生成[Message])
     *
     * @param stackId 异常栈序号, 用于获取日志标签
     * @param explain 日志说明
     * @param message 日志内容
     * @param level   日志优先级
     */
    public static void log(int level, int stackId, String explain, Object message) {
        if (!DEBUG)
            return;

        String log;
        explain = explain == null ? "" : explain;
        if (message instanceof Map) {
            log = formatLog(explain, (Map) message);
        } else if (message instanceof Collection) {
            log = formatLog(explain, (Collection) message);
        } else if (message.getClass().isArray()) {
            log = formatLog(explain, (Object[]) message);
        } else {
            log = explain + String.valueOf(message);
        }

        String tag = getTag(new Exception().getStackTrace()[stackId]);
        printLog(tag, log, level);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void log(int level, String explain, Object message) {
        log(level, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void log(int level, Object message) {
        log(level, DEFAULT_STACK_ID, null, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void e(String explain, Object message) {
        log(Log.ERROR, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void e(Object message) {
        log(Log.ERROR, DEFAULT_STACK_ID, null, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void w(String explain, Object message) {
        log(Log.WARN, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void w(Object message) {
        log(Log.WARN, DEFAULT_STACK_ID, null, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void i(String explain, Object message) {
        log(Log.INFO, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void i(Object message) {
        log(Log.INFO, DEFAULT_STACK_ID, null, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void d(String explain, Object message) {
        log(Log.DEBUG, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void d(Object message) {
        log(Log.DEBUG, DEFAULT_STACK_ID, null, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void v(String explain, Object message) {
        log(Log.VERBOSE, DEFAULT_STACK_ID, explain, message);
    }

    /**
     * @see #log(int, int, String, Object)
     */
    public static void v(Object message) {
        log(Log.VERBOSE, DEFAULT_STACK_ID, null, message);
    }
}