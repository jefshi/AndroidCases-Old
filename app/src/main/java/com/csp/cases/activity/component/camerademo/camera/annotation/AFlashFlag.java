package com.csp.cases.activity.component.camerademo.camera.annotation;

import android.support.annotation.IntDef;

import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 闪光灯标识
 */
@IntDef({CameraFlag.FLASH_AUTO, CameraFlag.FLASH_CLOSE, CameraFlag.FLASH_OPEN, CameraFlag.FLASH_LIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface AFlashFlag {
}