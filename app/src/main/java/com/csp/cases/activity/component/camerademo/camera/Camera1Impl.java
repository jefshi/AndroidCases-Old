package com.csp.cases.activity.component.camerademo.camera;

import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.AFlashFlag;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera1.CameraPreview;
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
        switchFlash();
    }

    @Override
    public int getCameraApi() {
        return CameraFlag.CAMERA_API_1;
    }

    @Override
    public void onResume() {
        initCamera();
        initCameraPreview();
        switchFlash();
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
        mCamera.startPreview();
    }

    @Override
    public void stopPreview() {
        mCamera.stopPreview();
    }

    @Override
    public void afreshPreview() {
        initCamera();
        initCameraPreview();
        switchFlash();
//        mPreview.setCamera(mCamera);

        mCamera.startPreview();
    }

    @Override
    public void takePicture() {
//        isTakePhoto = true;
        switchFlash();

        // 在这个例子中不使用前置摄像头
//        Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
//            continue;
//        }

        //调用相机拍照
        try {
            mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    //视图动画
//                cameraPhotobutton.setVisibility(View.GONE);
//                cameraGroup.setVisibility(View.GONE);
//                cameraTitle.setVisibility(View.GONE);
//                cameraConfirmLayout.setVisibility(View.VISIBLE);
//                //AnimSpring.getInstance(mConfirmLayout).startRotateAnim(120, 360);
//                imageData = data;
                    //停止预览
                    mCamera.stopPreview();

                    if (mBuilder.getTokenCallback() != null)
                        mBuilder.getTokenCallback().onPictureTaken(data);
                }
            });
        } catch (Exception e) {
            mBuilder.getErrorCallback().onError(ErrorCallback.ERROR_TOKEN_PICTURE, e);
        }
    }

    @Override
    public boolean setFlashMode(int mode) {
        mBuilder.setFlashMode(mode);
        return switchFlash();
    }

    @Override
    public boolean setLensFace(int lensFacing) {
        mBuilder.setLensFacing(lensFacing);
        if (!initCamera())
            return false;

//        mPreview.setCamera(mCamera);
        initCameraPreview();
        return true;
    }


    /**
     * TODO 要不要追加接口？
     *
     * @return
     */
    private boolean initCamera() {
        int cameraIndex = findCamera();
        if (cameraIndex < 0) {
            mBuilder.getErrorCallback().onError(ErrorCallback.ERROR_LENS_FACE, new Throwable("该设备不支持转换摄像头"));
            return false;
        }

        releaseCamera();
        mCamera = Camera.open(cameraIndex);
        return true;
    }

    private void initCameraPreview() {
        mPreview = new CameraPreview(mBuilder.getContext(), mCamera);
    }

    /**
     * 寻找相机
     *
     * @return 相机索引
     */
    private int findCamera() {
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

    @Override
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;

//            mPreview = null;
        }
    }


    // ==============================
    // 扩展功能：闪光灯等
    // ==============================

    /**
     * 注释：切换闪光灯
     *
     * @return true：操作成功
     */
    public boolean switchFlash() {
        try {
            PackageManager pm = mBuilder.getContext().getPackageManager();
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            for (FeatureInfo f : features) {
                if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) //判断设备是否支持闪光灯
                {
                    LogCat.e("FEATURE_CAMERA_FLASH");
                }
                LogCat.w(f.name);
            }

            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(toFlashMode(mBuilder.getFlashMode()));
            mCamera.setParameters(parameters);
            return true;
        } catch (Exception e) {
            mBuilder.getErrorCallback().onError(ErrorCallback.ERROR_FLASH, e);
            return false;
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
                return Camera.Parameters.FLASH_MODE_TORCH;
            case CameraFlag.FLASH_AUTO:
            default:
                return Camera.Parameters.FLASH_MODE_AUTO;
        }
    }

    @Override
    public int getLensFace() {
        return mBuilder.getLensFacing();
    }
}
