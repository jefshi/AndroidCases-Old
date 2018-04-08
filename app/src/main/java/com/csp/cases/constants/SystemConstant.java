package com.csp.cases.constants;

/**
 * Description: 系统常量
 * <p>Create Date: 2017/6/27
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public interface SystemConstant {
    boolean LOG_DEBUG = true; // 调试模式：开启LOG
    boolean FILE_LOG_DEBUG = LOG_DEBUG && false; // 调试模式：开启记录[LOG]到文件

    String DATABASE_NAME = "Case.db";
    String SHARED_PREFERENCES_NAME = "CASE_SHARED_PREFERENCES";
}
