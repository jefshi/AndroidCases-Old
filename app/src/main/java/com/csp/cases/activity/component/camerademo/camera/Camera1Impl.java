package com.csp.cases.activity.component.camerademo.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.AFlashFlag;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera1.CameraPreview;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;

/**
 * 默认有权限，有摄像机
 */
public class Camera1Impl implements ICamera {

    private ICamera.Builder mBuilder;

    /**
     * 相机类
     */
    private Camera mCamera;

    /**
     * 相机预览
     */
    private CameraPreview mPreview;

    /**
     * TODO initCamera
     * TODO initCameraPreview
     *
     * @param builder
     */
    public Camera1Impl(Builder builder) {
        mBuilder = builder;
        initCamera();
        initCameraPreview();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        releaseCamera();
    }

    @Override
    public View getPreview() {
        return mPreview;
    }

    @Override
    public void startPreview() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void resumePreview() {

    }

    @Override
    public void takePicture() {

    }

    @Override
    public void setFlashMode(int mode) {
        mBuilder.setFlashMode(mode);
        switchFlash();
    }

    @Override
    public void setLensFace(int lensFacing) {
        mBuilder.setLensFacing(lensFacing);
        initCamera();
        mPreview.setCamera(mCamera);
//        initCameraPreview();
    }


    /**
     * TODO 要不要追加接口？
     */
    private void initCamera() {
        int CammeraIndex = FindCamera();
        releaseCamera();
        mCamera = Camera.open(CammeraIndex);

    }

    @Deprecated
    private void initCameraPreview() {
        mPreview = new CameraPreview(mBuilder.getContext(), mCamera);
    }

    /**
     * 寻找相机
     *
     * @return 相机索引
     */
    private int FindCamera() {
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == toLensFacing(mBuilder.getLensFacing())) {
                return camIdx;
            }
        }
        return -1;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }


    // ==============================
    // 扩展功能：闪光灯等
    // ==============================

    /**
     * 注释：切换闪光灯
     */
    public void switchFlash() {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(toFlashMode(mBuilder.getFlashMode()));
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            LogCat.printStackTrace(Log.DEBUG, null, e);
            ToastUtil.showToast("该设备不支持闪光灯");
        }
    }

    // ==============================
    // 框架数据转 API 用数据
    // ==============================

    /**
     * @param lensFacing {@link ALensFacing}
     * @return 转换为正确的 LensFacing
     */
    private int toLensFacing(@ALensFacing int lensFacing) {
        return lensFacing == CameraFlag.LENS_FACING_FRONT
                ? Camera.CameraInfo.CAMERA_FACING_FRONT
                : Camera.CameraInfo.CAMERA_FACING_BACK;
    }

    /**
     * @param flashFla {@link AFlashFlag}
     * @return 转换为正确的 FlashMode
     */
    private String toFlashMode(@AFlashFlag int flashFla) {
        switch (flashFla) {
            case CameraFlag.FLASH_CLOSE:
                return Camera.Parameters.FLASH_MODE_OFF;
            case CameraFlag.FLASH_OPEN:
                return Camera.Parameters.FLASH_MODE_ON;
            case CameraFlag.FLASH_LIGHT:
                return Camera.Parameters.FLASH_MODE_ON; // TODO 未知
            case CameraFlag.FLASH_AUTO:
            default:
                return Camera.Parameters.FLASH_MODE_AUTO;
        }
    }
}
