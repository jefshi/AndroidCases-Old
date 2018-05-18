package com.csp.utils.android.log;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Description: 日志打印
 * <p>Create Date: 2017/07/14
 * <p>Modify Date: 2018/05/18
 *
 * @author csp
 * @version 1.0.4
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LogCat {
    private final static boolean DEBUG = true;
    private final static int LOG_MAX_LENGTH = 3072; // Android 能够打印的最大日志长度
    public final static int DEFAULT_STACK_ID = 2;

    /**
     * 获取日志标签, 例: --[类名][方法名]
     *
     * @param element 追踪栈元素
     * @return 日志标签
     */
    private static String getTag(StackTraceElement element) {
        String className = element.getClassName();
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
    private static String[] divideMessages(@NonNull String message) {
        if (message.length() <= LOG_MAX_LENGTH)
            return new String[]{message};

        String part;
        int index;
        Collection<String> list = new ArrayList<>();
        while (true) {
            if (message.length() <= LOG_MAX_LENGTH) {
                list.add(message);
                break;
            }

            part = message.substring(0, LOG_MAX_LENGTH);
            index = part.lastIndexOf('\n');
            if (index > -1)
                part = message.substring(0, index);

            list.add(part);
            message = message.substring(part.length());
        }

        String[] strings = new String[list.size()];
        return list.toArray(strings);
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
     * 获取异常栈信息
     *
     * @param throwable 异常错误对象
     * @return 异常栈信息
     */
    @SuppressWarnings("EmptyCatchBlock")
    private static String getStackTrace(Throwable throwable) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        throwable.printStackTrace(printStream);

        String message = baos.toString();
        printStream.close();
        return message;
    }

    /**
     * 打印异常信息
     *
     * @param explain   异常说明
     * @param throwable 异常错误对象
     */
    public static void printStackTrace(String explain, Throwable throwable) {
        String log = explain == null ? "" : explain;
        if (throwable != null)
            log += '\n' + getStackTrace(throwable);

        String tag = getTag(throwable != null
                ? throwable.getStackTrace()[0]
                : new Throwable().getStackTrace()[DEFAULT_STACK_ID - 1]);

        printLog(tag, log, Log.ERROR);
    }

    /**
     * @see #printStackTrace(String, Throwable)
     */
    public static void printStackTrace(Throwable throwable) {
        printStackTrace(null, throwable);
    }

    /**
     * 格式化[Message]
     *
     * @param explain 日志说明
     * @param message 日志内容
     */
    public static String formatLog(@NonNull final String explain, Object message) {
        String log;
        if (message instanceof Map) {
            log = formatLog(explain, (Map) message);
        } else if (message instanceof Collection) {
            log = formatLog(explain, (Collection) message);
        } else if (message.getClass().isArray()) {
            log = formatLog(explain, (Object[]) message);
        } else {
            log = (explain.length() == 0 ? "" : explain + ": ")
                    + String.valueOf(message);
        }
        return log;
    }

    /**
     * 格式化[Message]
     *
     * @param explain  日志说明
     * @param messages 日志内容
     */
    public static String formatLog(@NonNull final String explain, @NonNull Object[] messages) {
        StringBuilder log = new StringBuilder();
        String tip;
        if (messages.length == 0) {
            log.append(explain.length() == 0 ? "is empty" : explain + ": is empty");
        } else {
            for (int i = 0; i < messages.length; i++) {
                tip = explain + "[" + i + "]: ";
                log.append('\n').append(formatLog(tip, messages[i]));
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
    public static String formatLog(@NonNull final String explain, @NonNull Collection messages) {
        StringBuilder log = new StringBuilder();
        String tip;
        if (messages.isEmpty()) {
            log.append(explain.length() == 0 ? "is empty" : explain + ": is empty");
        } else {
            Iterator iterator = messages.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                tip = explain + "[" + i + "]: ";
                log.append('\n').append(formatLog(tip, iterator.next()));
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
    public static String formatLog(@NonNull final String explain, @NonNull Map messages) {
        StringBuilder log = new StringBuilder();
        String tip;
        if (messages.isEmpty()) {
            log.append(explain.length() == 0 ? "is empty" : explain + ": is empty");
        } else {
            Set keys = messages.keySet();
            int i = 0;
            for (Object key : keys) {
                tip = explain + "[" + i + ", " + key + "]: ";
                log.append('\n').append(formatLog(tip, messages.get(key)));
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
    @SuppressWarnings("ConstantConditions")
    public static void log(int level, int stackId, String explain, Object message) {
        if (!DEBUG)
            return;

        explain = explain == null ? "" : explain;
        message = message == null ? "null" : message;
        String log = formatLog(explain, message);

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