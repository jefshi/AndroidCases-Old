package com.csp.cases.activity.component.camerademo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import com.csp.cases.activity.component.camerademo.camera1.CameraPreview;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;

public class CameraUtil {


    /**
     * 相机类
     */
    private Camera mCamera;

    private CameraPreview mPreview;


    /**
     * *是否开启闪光灯
     */
    private boolean isFlashing = true;
    /**
     * 拍照标记
     */
    private boolean isTakePhoto;

    /**
     * @see Camera#CameraInfo#CAMERA_FACING_FRONT
     * @see Camera#CameraInfo#CAMERA_FACING_BACK
     */
    private int mLensFace = Camera.CameraInfo.CAMERA_FACING_BACK;

//    /**
//     * 图片流暂存
//     */
//    private byte[] imageData;


    public void setFlashing(boolean flashing) {
        isFlashing = flashing;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public CameraPreview getPreview() {
        return mPreview;
    }

    public void setLensFace(int lensFace) {
        mLensFace = lensFace;
    }

    public void initCamera(Context context) {
//        notEnterpriseCard.setVisibility(View.GONE);
//        notButton.setVisibility(View.GONE);
//        cameraData.setVisibility(View.VISIBLE);
        // 判断是否有摄像头
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return;
//        //判断是否有权限
//        PermissionUtils.applicationPermissions(this, new PermissionUtils.PermissionListener() {
//            @Override
//            public void onSuccess(Context context) {
        LogCat.i("初始化相机");

//        int CammeraIndex = FindFrontCamera();
//        if (CammeraIndex == -1) {
//            CammeraIndex = FindBackCamera();
//        }

        int CammeraIndex = FindCamera();

        releaseCamera();
        mCamera = Camera.open(CammeraIndex);

    }

    public void initCameraPreview(Context context) {
        mPreview = new CameraPreview(context, mCamera);
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    @Deprecated
    private int FindFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    private int FindCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == mLensFace) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    @Deprecated
    private int FindBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }


    /**
     * 注释：拍照并保存图片到相册
     */
    public void takePhoto(final Camera.PictureCallback callback) {
        isTakePhoto = true;
        switchFlash();

        // 在这个例子中不使用前置摄像头
//        Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//        if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
//            continue;
//        }

        //调用相机拍照
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

                if (callback != null)
                    callback.onPictureTaken(data, camera);
            }
        });
    }


    /**
     * 注释：切换闪光灯
     */
    public void switchFlash() {
        LogCat.d("是否开启闪光灯" + isFlashing);
        //mFlashButton.setImageResource(isFlashing ? R.mipmap.flash_open : R.mipmap.flash_close);
        //AnimSpring.getInstance(mFlashButton).startRotateAnim(120, 360);
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(isFlashing ? Camera.Parameters.FLASH_MODE_ON : Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            ToastUtil.showToast("该设备不支持闪光灯");
        }
    }

    /**
     * 注释：取消保存
     */
//    @Override
    public void afreshPreview() {
//        cameraPhotobutton.setVisibility(View.VISIBLE);
//        cameraGroup.setVisibility(View.VISIBLE);
//        cameraTitle.setVisibility(View.VISIBLE);
//        cameraConfirmLayout.setVisibility(View.GONE);
        //AnimSpring.getInstance(mPhotoLayout).startRotateAnim(120, 360);
        //开始预览
        mCamera.startPreview();
//        imageData = null;
        isTakePhoto = false;
    }

}
