package com.csp.cases.activity.component.camerademo.camera.constant;

import android.hardware.camera2.CameraCharacteristics;

public interface CameraFlag {

    // API 类型
    int CAMERA_API_1 = 1;
    int CAMERA_API_2 = 2; // camera2 的 API

    // 相机类型
    int LENS_FACING_FRONT = 0; // 前置
    int LENS_FACING_BACK = 1; // 后置
    int LENS_FACING_EXTERNAL = 2; // 扩展

    // 闪光灯
    int FLASH_AUTO = 0; // 自动
    int FLASH_CLOSE = 1; // 关
    int FLASH_OPEN = 2; // 开
    int FLASH_LIGHT = 3; // 常亮


}
