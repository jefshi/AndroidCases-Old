package com.csp.cases.activity.component.camerademo.camera;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.ACameraApi;
import com.csp.cases.activity.component.camerademo.camera.annotation.AFlashFlag;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera.utils.IncompatibleDevicesUtils;
import com.csp.utils.android.log.LogCat;

public interface ICamera {

    @ACameraApi
    int getCameraApi();

    void onResume();

    void onPause();

    /**
     * 释放相机资源
     */
    void releaseCamera();

    /**
     * @return 预览 View
     */
    View getPreview();

    /**
     * 开启预览
     */
    void startPreview();

    /**
     * 停止预览
     */
    void stopPreview();

    /**
     * 重新预览
     *
     * @see #startPreview()
     */
    void afreshPreview();

    /**
     * 拍照
     */
    void takePicture();

    /**
     * 设置闪光灯
     */
    void setFlashMode(@AFlashFlag int mode);

    /**
     * 设置相机类型：如前置、后置、扩展等
     */
    void setLensFace(@ALensFacing int lensFacing);

    class Builder {

        private Activity mActivity; // TODO ???
        private Context mContext; // TODO ???

        @ACameraApi
        private int mCameraApi;

        @ALensFacing
        private int mLensFacing = CameraFlag.LENS_FACING_BACK;

        @AFlashFlag
        private int mFlashMode = CameraFlag.FLASH_AUTO;

        private PictureTokenCallback mTokenCallback; // 拍照回调

        public int getCameraApi() {
            return mCameraApi;
        }

        public Context getContext() {
            return mContext;
        }

        public Activity getActivity() {
            return mActivity;
        }

        public int getLensFacing() {
            return mLensFacing;
        }

        public int getFlashMode() {
            return mFlashMode;
        }

        public PictureTokenCallback getTokenCallback() {
            return mTokenCallback;
        }

        public Builder setCameraApi(int cameraApi) {
            mCameraApi = cameraApi;
            return this;
        }

        public Builder setLensFacing(@ALensFacing int lensFacing) {
            mLensFacing = lensFacing;
            return this;
        }

        public Builder setPictureTokenCallback(PictureTokenCallback callback) {
            mTokenCallback = callback;
            return this;
        }

        public Builder setFlashMode(@AFlashFlag int mode) {
            mFlashMode = mode;
            return this;
        }


        public Builder(Activity activity, Context context) {
            mActivity = activity;
            mContext = context;
        }

        public ICamera build(Context context) {
            // 判断是否有摄像头
            if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
                return null;

            // TODO 测试代码 Begin
            return new Camera1Impl(this);
//            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//                    ? new Camera2Impl(this)
//                    : new Camera1Impl(this);
            // TODO 测试代码 End

            // API 选择
//            int cameraApi = mCameraApi != 0 ? mCameraApi : preferredCameraApi(context);
//            return cameraApi == CameraFlag.CAMERA_API_1
//                    ? new Camera1Impl(this)
//                    : new Camera2Impl(this);
        }


        @ACameraApi
        private int preferredCameraApi(Context context) {
            boolean userCamera2 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && !isLegacyCamera(context)
                    && !IncompatibleDevicesUtils.isIncompatibleDevice(Build.MODEL);

            return userCamera2 ? CameraFlag.CAMERA_API_2 : CameraFlag.CAMERA_API_1;
        }

        /**
         * There were more issues than benefits when using Legacy camera with Camera2 API.
         * I found it to be working much better with deprecated Camera1 API instead.
         *
         * @return true：推荐使用旧相机API
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private boolean isLegacyCamera(Context context) {
            try {
                CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (manager == null)
                    return true;

                for (String cameraId : manager.getCameraIdList()) {
                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                    Integer lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (lensFacing == null || lensFacing != mLensFacing)
                        continue;

                    Integer hardwareLevel = characteristics.get(
                            CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);

                    // 当设备的 Supported Hardware Level 低于 FULL 的时候，建议还是使用 Camera1，
                    // 因为 FULL 级别以下的 Camera2 能提供的功能几乎和 Camera1 一样，所以倒不如选择更加稳定的 Camera1。
                    // https://www.jianshu.com/p/9a2e66916fcb
                    return hardwareLevel == null
                            || hardwareLevel < CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY;
                }
                return true;
            } catch (Throwable t) {
                LogCat.printStackTrace(Log.DEBUG, null, t);
                return true;
            }
        }
    }
}
