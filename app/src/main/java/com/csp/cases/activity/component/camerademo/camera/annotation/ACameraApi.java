package com.csp.cases.activity.component.camerademo.camera.annotation;

import android.support.annotation.IntDef;

import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({CameraFlag.CAMERA_API_1, CameraFlag.CAMERA_API_2})
@Retention(RetentionPolicy.SOURCE)
public @interface ACameraApi {
}
