package com.csp.library.java.log;

/**
 * Description: 日志工具类，统一打印形式
 * <p>Create Date: 2018/05/17
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.2
 * @since JavaLibrary 1.0.0
 */
public class LogUtil {

    public static void printStackTrace(Exception exception) {
        exception.printStackTrace();
    }
}
