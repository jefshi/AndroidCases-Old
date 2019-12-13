package com.csp.cases.activity.component.camerademo.camera.utils;

import android.util.Log;

import com.csp.utils.android.log.LogCat;

public interface Logger {

    int ERROR_COMMON = 0; // 一般异常日志

    int ERROR_NO_CAMERA = 1; // 摄像头不存在
    int ERROR_NO_PERMISSION = 2; // 相机权限未获取
    int ERROR_FLASH = 3; // 闪光灯设置失败
    int ERROR_LENS_FACE = 4; // 相机设置失败
    int ERROR_TOKEN_PICTURE = 5; // 拍照失败

    int ERROR_OPEN_CAMERA = 6; // 打开相机失败
    int ERROR_CONFIGURE_FAILED = 7; // 摄像头配置失败
    int ERROR_CAMERA_ACCESS = 8; // 预览数据捕获异常
    int ERROR_OPEN_CLOSE_LOCK = 9; // 打开关闭锁超时

    void log(String message);

    void printStackTrace(int type, Throwable t);

    class Sample implements Logger {

        @Override
        public void log(String message) {
            LogCat.e(message);
        }

        @Override
        public void printStackTrace(int type, Throwable t) {
            LogCat.printStackTrace(Log.ERROR, "type = " + type, t);
        }
    }
}
