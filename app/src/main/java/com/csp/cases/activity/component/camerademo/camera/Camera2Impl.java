package com.csp.cases.activity.component.camerademo.camera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.AFlashFlag;
import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera2.AutoFitTextureView;
import com.csp.cases.activity.component.camerademo.camera2.Camera2Util;
import com.csp.utils.android.classutil.BitmapUtil;

import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Impl implements ICamera {

    private ICamera.Builder mBuilder;

    private Camera2Util mCameraUtil;

    private AutoFitTextureView mTextureView;

    public Camera2Impl(Builder builder) {
        mBuilder = builder;
        onResume();
    }

    @Override
    public int getCameraApi() {
        return CameraFlag.CAMERA_API_2;
    }

    @Override
    public void onResume() {
        if (mCameraUtil == null) {
            mTextureView = new AutoFitTextureView(mBuilder.getContext());


//            mLfraPreview.removeAllViews();
//            mLfraPreview.addView(mTextureView);

//            Camera2Util.Builder builder = new Camera2Util.Builder(mBuilder.getActivity())
//                    .setLensFacing(mBuilder.getLensFacing())
//                    .setTextureView(mTextureView);

            mCameraUtil = new Camera2Util(mBuilder.getActivity(), mBuilder, mTextureView);

            mCameraUtil.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    ByteBuffer buffer = reader.acquireNextImage().getPlanes()[0].getBuffer();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);

                    if (mBuilder.getTokenCallback() != null)
                        mBuilder.getTokenCallback().onPictureTaken(bytes);
                }
            });
        }

        mCameraUtil.onResume();
    }

    @Override
    public void releaseCamera() {
        mCameraUtil.closeCamera();
    }

    @Override
    public void onPause() {
        mCameraUtil.onPause();
    }

    @Override
    public View getPreview() {
        return mTextureView;
    }

    @Override
    public void startPreview() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void afreshPreview() {
//        mTextureView = new AutoFitTextureView(mBuilder.getContext());
        mCameraUtil.closeCamera();
        mCameraUtil.openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        // mCameraUtil.runPrecaptureSequence();
    }

    @Override
    public void takePicture() {
        mCameraUtil.takePicture();
    }

    @Override
    public boolean setFlashMode(int mode) {
        mCameraUtil.setFlashing(false); // TODO ???
        return true;
    }

    @Override
    public boolean setLensFace(int lensFacing) {
        mCameraUtil.setLensFace(toLensFacing(lensFacing)); // TODO ???
        return true;
    }


    // ==============================
    // 框架数据转 API 用数据
    // ==============================

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
