package com.csp.cases.activity.component.camerademo.camera2;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.csp.cases.activity.component.camerademo.camera.ErrorCallback;
import com.csp.cases.activity.component.camerademo.camera.ICamera;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

/**
 * 选择相机，并保有相机常量参数
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class Camera2Param {

    /**
     * 主要参数
     */
    String mCameraId;
    CameraCharacteristics mCharacteristics; // 描述摄像头的各种特性
    StreamConfigurationMap mMap;

    /**
     * 其他参数
     */
    boolean mFlashSupported; // true：支持闪光灯
    int mSensorOrientation; // Orientation of the camera sensor（相机传感器方向）

    public Camera2Param(@NonNull ICamera.Builder builder, @NonNull CameraManager manager) {
        initMaster(builder, manager);
        initFlashSupported();
        initSensorOrientation();
    }

    private void initMaster(@NonNull ICamera.Builder builder, @NonNull CameraManager manager) {
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics
                        = manager.getCameraCharacteristics(cameraId);

                // We don't use a front facing camera in this sample.
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing != toLensFacing(builder.getLensFacing()))
                    continue;

                StreamConfigurationMap map = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (map != null) {
                    mCameraId = cameraId;
                    mCharacteristics = characteristics;
                    mMap = map;
                    return;
                }
            }
        } catch (Exception e) {
            builder.getErrorCallback().onError(ErrorCallback.ERROR_NO_CAMERA, e);
        }
    }

    /**
     * after {@link #initMaster(ICamera.Builder, CameraManager)}
     */
    private void initFlashSupported() {
        Boolean available = mCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
        mFlashSupported = available == null ? false : available;
    }

    private void initSensorOrientation() {
        Integer orientation = mCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        if (orientation != null)
            mSensorOrientation = orientation;
    }

    /**
     * @param lensFacing {@link ALensFacing}
     * @return 转换为正确的 LensFacing
     */
    private int toLensFacing(@ALensFacing int lensFacing) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && lensFacing == CameraFlag.LENS_FACING_EXTERNAL) {
            return CameraCharacteristics.LENS_FACING_EXTERNAL;
        }

        return lensFacing == CameraFlag.LENS_FACING_FRONT
                ? CameraCharacteristics.LENS_FACING_FRONT
                : CameraCharacteristics.LENS_FACING_BACK;
    }
}
