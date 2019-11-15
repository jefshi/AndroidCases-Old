package com.csp.cases.activity.component.camera2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;

import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Util {

    Activity mActivity;

    /**
     * 工作线程
     */
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;


//    /**
//     * ID of the current {@link CameraDevice}.
//     */
//    private String mCameraId;

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the camera.
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);


    /**
     * A {@link CameraCaptureSession } for camera preview.
     */
    private CameraCaptureSession mCaptureSession;

    /**
     * A reference to the opened {@link CameraDevice}.
     */
    private CameraDevice mCameraDevice;


    /**
     * An {@link ImageReader} that handles still image capture.
     */
    private ImageReader mImageReader;


    /**
     * 相机预览
     */
    private AutoFitTextureView mTextureView; // 预览载体
    private Size mPreviewSize; // 预览大小


    /**
     * {@link TextureView.SurfaceTextureListener} handles several lifecycle events on a
     * {@link TextureView}.
     */
    private final TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };


    /**
     * This a callback object for the {@link ImageReader}. "onImageAvailable" will be called when a
     * still image is ready to be saved.
     */
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {

        @Override
        public void onImageAvailable(ImageReader reader) {
            mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));
        }
    };


    /**
     * {@link CameraDevice.StateCallback} is called when {@link CameraDevice} changes its state.
     */
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            // This method is called when the camera is opened. We start camera preview here.
            mCameraOpenCloseLock.release();
            mCameraDevice = cameraDevice;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            mCameraDevice = null;
            Activity activity = getActivity();
            if (null != activity) {
                activity.finish();
            }
        }

    };

    public void setTextureView(AutoFitTextureView textureView) {
        mTextureView = textureView;
    }

    public AutoFitTextureView getTextureView() {
        return mTextureView;
    }

    public Camera2Util() {
    }

    // @Override
    public void onResume() {
//        super.onResume();
        startBackgroundThread();

        // When the screen is turned off and turned back on, the SurfaceTexture is already
        // available, and "onSurfaceTextureAvailable" will not be called. In that case, we can open
        // a camera and start preview from here (otherwise, we wait until the surface is ready in
        // the SurfaceTextureListener).
        if (mTextureView.isAvailable()) {
            openCamera(mTextureView.getWidth(), mTextureView.getHeight());
        } else {
            mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);
        }
    }

    // @Override
    public void onPause() {
        closeCamera();
        stopBackgroundThread();
//        super.onPause();
    }

    private CameraManager getCameraManager() {
        return (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
    }

    private Builder mBuilder;

    public static class Builder {

        private int mLensFacing;

        private ErrorListener mErrorListener;


        /**
         * @see CameraCharacteristics#LENS_FACING_FRONT
         * @see CameraCharacteristics#LENS_FACING_BACK
         * @see CameraCharacteristics#LENS_FACING_EXTERNAL
         */
        public void setLensFacing(int lensFacing) {
            mLensFacing = lensFacing;
        }
//
//        public int getLensFacing() {
//            return mLensFacing;
//        }


    }


    /**
     * Matrix 转换配置为 mTextureView
     * Configures the necessary {@link android.graphics.Matrix} transformation to `mTextureView`.
     * This method should be called after the camera preview size is determined in
     * setUpCameraOutputs and also the size of `mTextureView` is fixed.
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    private void configureTransform(int viewWidth, int viewHeight) {
//        Activity activity = getActivity();
        if (null == mTextureView || null == mPreviewSize) //  || null == activity)
            return;
//        }
        int rotation = getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, mPreviewSize.getHeight(), mPreviewSize.getWidth());
        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max(
                    (float) viewHeight / mPreviewSize.getHeight(),
                    (float) viewWidth / mPreviewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        mTextureView.setTransform(matrix);
    }


    private CameraParam mCameraParam; // 选中相机的参数


    private Display getDefaultDisplay() {
        return mActivity.getWindowManager().getDefaultDisplay();
    }


    private void setupPreviewSize(Size textureViewSize, Size largest) {
        Display defaultDisplay = getDefaultDisplay();
//        CameraCharacteristics characteristics = mCameraParam.mCharacteristics;
        StreamConfigurationMap map = mCameraParam.mMap;
        int sensorOrientation = mCameraParam.mSensorOrientation;

        // Find out if we need to swap dimension to get the preview size relative to sensor
        // coordinate.
        int displayRotation = defaultDisplay.getRotation();
        boolean swappedDimensions = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    swappedDimensions = true;
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    swappedDimensions = true;
                }
                break;
            default:
                LogCat.e("Display rotation is invalid: " + displayRotation);
        }

        Point displaySize = new Point();
        defaultDisplay.getSize(displaySize);

        int width = textureViewSize.getWidth();
        int height = textureViewSize.getHeight();
        int rotatedPreviewWidth = width;
        int rotatedPreviewHeight = height;
        int maxPreviewWidth = displaySize.x;
        int maxPreviewHeight = displaySize.y;

        if (swappedDimensions) {
            rotatedPreviewWidth = height;
            rotatedPreviewHeight = width;
            maxPreviewWidth = displaySize.y;
            maxPreviewHeight = displaySize.x;
        }

//        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
//            maxPreviewWidth = MAX_PREVIEW_WIDTH;
//        }
//
//        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
//            maxPreviewHeight = MAX_PREVIEW_HEIGHT;
//        }

        // Danger, W.R.! Attempting to use too large a preview size could exceed the camera
        // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
        // garbage capture data.
        mPreviewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                maxPreviewHeight, largest);
    }


    /**
     * Given {@code choices} of {@code Size}s supported by a camera, choose the smallest one that
     * is at least as large as the respective texture view size, and that is at most as large as the
     * respective max size, and whose aspect ratio matches with the specified value. If such size
     * doesn't exist, choose the largest one that is at most as large as the respective max size,
     * and whose aspect ratio matches with the specified value.
     *
     * @param choices           The list of sizes that the camera supports for the intended output
     *                          class
     * @param textureViewWidth  The width of the texture view relative to sensor coordinate
     * @param textureViewHeight The height of the texture view relative to sensor coordinate
     * @param maxWidth          The maximum width that can be chosen
     * @param maxHeight         The maximum height that can be chosen
     * @param aspectRatio       The aspect ratio
     * @return The optimal {@code Size}, or an arbitrary one if none were big enough
     */
    private static Size chooseOptimalSize(Size[] choices, int textureViewWidth,
                                          int textureViewHeight, int maxWidth, int maxHeight, Size aspectRatio) {

        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        // Collect the supported resolutions that are smaller than the preview Surface
        List<Size> notBigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getWidth() <= maxWidth && option.getHeight() <= maxHeight &&
                    option.getHeight() == option.getWidth() * h / w) {
                if (option.getWidth() >= textureViewWidth &&
                        option.getHeight() >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }

        // Pick the smallest of those big enough. If there is no one big enough, pick the
        // largest of those not big enough.
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            LogCat.e("Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    private void setupImageReader(Size largest) {
        mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                ImageFormat.JPEG, /*maxImages*/2);
        mImageReader.setOnImageAvailableListener(
                mOnImageAvailableListener, mBackgroundHandler);
    }

    /**
     * 配置 相机的 预览尺寸
     * Sets up member variables related to camera.
     *
     * @param width  The width of available size for camera preview
     * @param height The height of available size for camera preview
     */
//    @SuppressWarnings("SuspiciousNameCombination")
    private boolean setUpCameraOutputs(int width, int height) {
        return setUpCameraOutputs(new Size(width, height));
    }

    private boolean setUpCameraOutputs(Size textureViewSize) {
        CameraManager manager = getCameraManager();
        mCameraParam = new CameraParam(mBuilder, manager);
        if (mCameraParam.mCameraId == null)
            return false;

        StreamConfigurationMap map = mCameraParam.mMap;// characteristics.get(

        // For still image captures, we use the largest available size.
        Size largest = Collections.max(
                Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                new CompareSizesByArea());

        setupImageReader(largest);
        setupPreviewSize(textureViewSize, largest);
        return true;
    }

    /**
     * Opens the camera specified by {@link Camera2BasicFragment#mCameraId}.
     */
    @RequiresPermission(Manifest.permission.CAMERA)
    private void openCamera(int width, int height) {
        // 权限控制
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }
        if (setUpCameraOutputs(width, height))
            return;

        // We fit the aspect ratio of TextureView to the size of preview we picked.
        int orientation = mActivity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTextureView.setAspectRatio(
                    mPreviewSize.getWidth(), mPreviewSize.getHeight());
        } else {
            mTextureView.setAspectRatio(
                    mPreviewSize.getHeight(), mPreviewSize.getWidth());
        }


        configureTransform(width, height);
//        Context context = mActivity;
        CameraManager manager = getCameraManager();
        try {
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            manager.openCamera(mCameraParam.mCameraId, mStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            LogCat.e(e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }


    /**
     * Closes the current {@link CameraDevice}.
     */
    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            if (null != mCaptureSession) {
                mCaptureSession.close();
                mCaptureSession = null;
            }
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mImageReader) {
                mImageReader.close();
                mImageReader = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    // ========================================
    // 工作线程相关
    // ========================================

    /**
     * Starts a background thread and its {@link Handler}.
     */
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ========================================
    // 选中相机的参数
    // ========================================

    /**
     * 选择相机，并保有相机参数
     */
    public static class CameraParam {

        /**
         * 主要参数
         */
        private String mCameraId;
        private CameraCharacteristics mCharacteristics; // 描述摄像头的各种特性
        private StreamConfigurationMap mMap;

        /**
         * 其他参数
         */
        private boolean mFlashSupported; // true：支持闪光灯
        private int mSensorOrientation; // Orientation of the camera sensor（相机传感器方向）

        public CameraParam(@NonNull Builder builder, @NonNull CameraManager manager) {
            initMaster(builder, manager);
            initFlashSupported();
            initSensorOrientation();
        }

        private void initMaster(@NonNull Builder builder, @NonNull CameraManager manager) {
            try {
                for (String cameraId : manager.getCameraIdList()) {
                    CameraCharacteristics characteristics
                            = manager.getCameraCharacteristics(cameraId);

                    // We don't use a front facing camera in this sample.
                    Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (facing != null && facing != builder.mLensFacing)
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
                if (builder.mErrorListener != null)
                    builder.mErrorListener.onError(e);
            }
        }

        /**
         * after {@link #initMaster(Builder, CameraManager)}
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
    }

    // ========================================
    // 其他
    // ========================================

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

    public interface ErrorListener {

        void onError(Exception e);
    }
}
