package com.csp.cases.activity.component.camerademo.camera.annotation;

import android.support.annotation.IntDef;

import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 相机类型（朝向）
 */
@IntDef({CameraFlag.LENS_FACING_FRONT, CameraFlag.LENS_FACING_BACK, CameraFlag.LENS_FACING_EXTERNAL})
@Retention(RetentionPolicy.SOURCE)
public @interface ALensFacing {
}
