package com.csp.cases.activity.component.camerademo.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.TextureView;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.ACameraApi;
import com.csp.cases.activity.component.camerademo.camera.annotation.AFlashFlag;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera.utils.IncompatibleDevicesUtils;
import com.csp.cases.activity.component.camerademo.camera.utils.LogDelegate;
import com.csp.cases.activity.component.camerademo.camera.utils.Logger;

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
     * <p>
     * 注意前置闪光灯是个巨大的坑，个别手机上设置闪光灯时没有报错，但拍照的时候有着巨大的问题，
     * 甚至会导致系统的相机都无法使用，点名小米手机
     *
     * @return true：设置成功
     */
    boolean setFlashMode(@AFlashFlag int mode);

    /**
     * 设置相机类型：如前置、后置、扩展等
     *
     * @return true：切换正常
     */
    boolean setLensFace(@ALensFacing int lensFacing);

    /**
     * @return 相机类型
     */
    @ALensFacing
    int getLensFace();

    class Builder {

        private Activity mActivity; // TODO ???
        private Context mContext; // TODO ???

        private Logger mLogger;

        @ACameraApi
        private int mCameraApi;

        private TextureView mPreViewForApi2;

        @ALensFacing
        private int mLensFacing = CameraFlag.LENS_FACING_BACK;

        @AFlashFlag
        private int mFlashMode = CameraFlag.FLASH_AUTO;

        private PictureTokenCallback mTokenCallback; // 拍照回调

        @NonNull
        private ErrorCallback mErrorCallback = new ErrorCallback.Sample(); // 错误回调

        public int getCameraApi() {
            return mCameraApi;
        }

        public Context getContext() {
            return mContext;
        }

        public Activity getActivity() {
            return mActivity;
        }

        public TextureView getPreViewForApi2() {
            return mPreViewForApi2;
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

        @NonNull
        public ErrorCallback getErrorCallback() {
            return mErrorCallback;
        }

        public Builder setLogger(Logger logger) {
            mLogger = logger;
            return this;
        }

        public Builder setCameraApi(int cameraApi) {
            mCameraApi = cameraApi;
            return this;
        }

        public Builder setPreViewForApi2(TextureView preViewForApi2) {
            mPreViewForApi2 = preViewForApi2;
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

        public Builder setErrorCallback(@NonNull ErrorCallback errorCallback) {
            mErrorCallback = errorCallback;
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

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public ICamera build(Context context) {
            // 判断是否有摄像头
            if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                mErrorCallback.onError(ErrorCallback.ERROR_NO_CAMERA, new Exception("摄像头不存在"));
                return null;
            }

            // 日志代理
            if (mLogger == null) {
                mLogger = new Logger.Sample();
            }
            LogDelegate.setLogger(mLogger);

            // API 选择
            int cameraApi = mCameraApi == CameraFlag.CAMERA_API_1 ? mCameraApi : preferredCameraApi(context);
            LogDelegate.log("选择的相机：" + cameraApi);

            // TODO 测试 begin
            cameraApi = CameraFlag.CAMERA_API_2;
            // TODO 测试 end

            return cameraApi == CameraFlag.CAMERA_API_1
                    ? new Camera1Impl(this)
                    : new Camera2Impl(this);
        }

        @ACameraApi
        private int preferredCameraApi(Context context) {
            boolean userCamera2 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && isAdvancedCamera(context)
                    && !IncompatibleDevicesUtils.isIncompatibleDevice(Build.MODEL);

            return userCamera2 ? CameraFlag.CAMERA_API_2 : CameraFlag.CAMERA_API_1;
        }

        /**
         * 当设备的 Supported Hardware Level 低于 FULL 的时候，建议还是使用 Camera1，
         * 因为 FULL 级别以下的 Camera2 能提供的功能几乎和 Camera1 一样，所以倒不如选择更加稳定的 Camera1。
         * <p>
         * LEVEL_LEGACY: 向后兼容模式, 如果是此等级, 基本没有额外功能, HAL层大概率就是HAL1(我遇到过的都是)
         * LEVEL_LIMITED: 有最基本的功能, 还支持一些额外的高级功能, 这些高级功能是LEVEL_FULL的子集
         * LEVEL_FULL: 支持对每一帧数据进行控制,还支持高速率的图片拍摄
         * LEVEL_3: 支持YUV后处理和Raw格式图片拍摄, 还支持额外的输出流配置
         * LEVEL_EXTERNAL: API28中加入的, 应该是外接的摄像头, 功能和LIMITED类似
         * <p>
         * 各个等级从支持的功能多少排序为: LEGACY < LIMITED < FULL < LEVEL_3
         * <p>
         * 来源：https://www.jianshu.com/p/9a2e66916fcb
         * 来源：https://www.jianshu.com/p/23e8789fbc10
         *
         * @return true：推荐使用新相机API
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_3
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_FULL
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY
         * @see CameraCharacteristics#INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL
         */
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private boolean isAdvancedCamera(Context context) {
            try {
                CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (manager == null)
                    return false;

                for (String cameraId : manager.getCameraIdList()) {
                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                    Integer lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (lensFacing == null || lensFacing != mLensFacing)
                        continue;

                    int requiredLevel = CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL;
                    return isHardwareLevelSupported(requiredLevel, characteristics);
                }
                return false;
            } catch (Throwable t) {
                mErrorCallback.onError(ErrorCallback.ERROR_COMMON, t);
                return false;
            }
        }

        /**
         * 判断相机的 Hardware Level 是否大于等于指定的 Level
         * <p>
         * 各个等级从支持的功能多少排序为: LEGACY < LIMITED < FULL < LEVEL_3
         *
         * @return true：支持
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        private boolean isHardwareLevelSupported(int requiredLevel, CameraCharacteristics characteristics) {
            Integer hardwareLevel = characteristics.get(
                    CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            if (hardwareLevel == null)
                return false;

            if (requiredLevel == hardwareLevel)
                return true;

            int[] sortedLevels;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sortedLevels = new int[]{
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY,
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED,
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL,
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3
                };
            } else {
                sortedLevels = new int[]{
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY,
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED,
                        CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL
                };
            }
            for (int sort : sortedLevels) {
                if (requiredLevel == sort)
                    return true;
                else if (hardwareLevel == sort)
                    return false;
            }
            return false;
        }
    }
}
