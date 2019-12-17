package com.csp.cases.activity.component.camerademo.camera;

import android.hardware.camera2.CameraCharacteristics;
import android.media.ImageReader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.TextureView;
import android.view.View;

import com.csp.cases.activity.component.camerademo.camera.annotation.ALensFacing;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera2.AutoFitTextureView;
import com.csp.cases.activity.component.camerademo.camera2.Camera2Util;
import com.csp.utils.android.log.LogCat;

import java.nio.ByteBuffer;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Impl implements ICamera {

    private ICamera.Builder mBuilder;

    private Camera2Util mCameraUtil;

    private TextureView mTextureView;

    public Camera2Impl(Builder builder) {
        mBuilder = builder;
        initCamera();
    }

    @Override
    public int getCameraApi() {
        return CameraFlag.CAMERA_API_2;
    }

    @Override
    public void onResume() {
        initCamera();
        mCameraUtil.onResume();
    }

    private void initCamera() {
        if (mCameraUtil == null) {
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT);

            TextureView preView = mBuilder.getPreViewForApi2();
            if (preView == null)
                preView = new AutoFitTextureView(mBuilder.getContext());
//            mTextureView.setLayoutParams(lp);


//            mLfraPreview.removeAllViews();
//            mLfraPreview.addView(mTextureView);

//            Camera2Util.Builder builder = new Camera2Util.Builder(mBuilder.getActivity())
//                    .setLensFacing(mBuilder.getLensFacing())
//                    .setTextureView(mTextureView);

            mTextureView = preView;
            mCameraUtil = new Camera2Util(mBuilder.getActivity(), mBuilder, mTextureView);

            mCameraUtil.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(final ImageReader reader) {
                    mBuilder.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mBuilder.getTokenCallback() != null) {
                                ByteBuffer buffer = reader.acquireNextImage().getPlanes()[0].getBuffer();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);

                                mBuilder.getTokenCallback().onPictureTaken(bytes);
                            }
                        }
                    });
                }
            });
        }
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
        int oldMode = mBuilder.getFlashMode();
        mBuilder.setFlashMode(mode);
        boolean result = mCameraUtil.setFlashMode();
        if (!result)
            mBuilder.setFlashMode(oldMode);

        return result;
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


    @Override
    public int getLensFace() {
        return mBuilder.getLensFacing();
    }
}
