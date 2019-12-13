package com.csp.cases.activity.component.camerademo.camera.utils;

public class LogDelegate {

    private static Logger sLogger;

    public static Logger getLogger() {
        return sLogger;
    }

    public static void setLogger(Logger logger) {
        LogDelegate.sLogger = logger;
    }

    private LogDelegate() {
    }

    public static void log(String message) {
        sLogger.log(message);
    }
}
