package com.csp.cases.activity.component.camerademo.camera1;

import android.app.Activity;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.csp.utils.android.log.LogCat;

import java.io.IOException;
import java.util.SortedSet;

/**
 * @author 郭翰林
 * @date 2019/2/28 0028 17:06
 * 注释:相机预览视图
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean isPreview;
    private Context context;
    /**
     * 预览尺寸集合
     */
    private final SizeMap mPreviewSizes = new SizeMap();
    /**
     * 图片尺寸集合
     */
    private final SizeMap mPictureSizes = new SizeMap();
    /**
     * 屏幕旋转显示角度
     */
    private int mDisplayOrientation;
    /**
     * 设备屏宽比
     */
    private AspectRatio mAspectRatio;

    /**
     * TODO 会不会造成未知问题，未知
     */
    public void setCamera(Camera camera) {
        mCamera = camera;
    }

    /**
     * 注释：构造函数
     * 时间：2019/2/28 0028 17:10
     * 作者：郭翰林
     *
     * @param context
     * @param mCamera
     */
    public CameraPreview(Context context, Camera mCamera) {
        super(context);
        this.context = context;
        this.mCamera = mCamera;
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        mDisplayOrientation = ((Activity) context).getWindowManager().getDefaultDisplay().getRotation();
        mAspectRatio = AspectRatio.of(16, 9);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //设置设备高宽比
            mAspectRatio = getDeviceAspectRatio((Activity) context);
            mAspectRatio = AspectRatio.of(4, 3);
            int orientationDegrees = getCameraDisplayOrientation(
                    (Activity) context, Camera.CameraInfo.CAMERA_FACING_BACK);
            // 设置相机镜头旋转角度(默认摄像头是横拍)
            //设置预览方向
            mCamera.setDisplayOrientation(orientationDegrees);
            Camera.Parameters parameters = mCamera.getParameters();
            //获取所有支持的预览尺寸
            mPreviewSizes.clear();
            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                LogCat.d("预览==" + new Size(size.width, size.height));
                mPreviewSizes.add(new Size(size.width, size.height));
            }

            //获取所有支持的图片尺寸
            mPictureSizes.clear();
            for (Camera.Size size : parameters.getSupportedPictureSizes()) {
                LogCat.d("图片==" + new Size(size.width, size.height));
                mPictureSizes.add(new Size(size.width, size.height));
            }
            //mPictureSizes.add(new Size(1840, 4000));
            LogCat.d("chooseOptimalSize==" + mPreviewSizes);
            LogCat.d("chooseOptimalSize==" + mAspectRatio);
            LogCat.d("chooseOptimalSize==" + mPreviewSizes.sizes(mAspectRatio));
            Size previewSize = chooseOptimalSize(mPreviewSizes.sizes(mAspectRatio));
            Size pictureSize = mPictureSizes.sizes(mAspectRatio).last();
            //设置相机参数
            parameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
            parameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setRotation(90);
           /* List<String> focusModes = parameters.getSupportedFocusModes();//
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);//设置对焦模式，自动对焦
            }*/
            //自动连续对焦
            if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
            }
            mCamera.setParameters(parameters);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(holder);
            //开启预览效果
            mCamera.startPreview();
            isPreview = true;
        } catch (IOException e) {
            Log.e("CameraPreview", "相机预览错误: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }
        //停止预览效果
        mCamera.stopPreview();
        //重新设置预览效果
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            if (isPreview) {
                //正在预览
                holder.removeCallback(this);
              /*  mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.lock();
                mCamera.release();
                mCamera =null;*/
            }
        }
    }


    /**
     * 注释：获取设备屏宽比
     * 时间：2019/3/4 0004 12:55
     * 作者：郭翰林
     */
    private AspectRatio getDeviceAspectRatio(Activity activity) {
        int width = activity.getWindow().getDecorView().getWidth();
        int height = activity.getWindow().getDecorView().getHeight();
        return AspectRatio.of(width, height);
    }

    /**
     * 注释：选择合适的预览尺寸
     * 时间：2019/3/4 0004 11:25
     * 作者：郭翰林
     *
     * @param sizes
     * @return
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private Size chooseOptimalSize(SortedSet<Size> sizes) {
        int desiredWidth;
        int desiredHeight;
        final int surfaceWidth = getWidth();
        final int surfaceHeight = getHeight();
        if (isLandscape(mDisplayOrientation)) {
            desiredWidth = surfaceHeight;
            desiredHeight = surfaceWidth;
        } else {
            desiredWidth = surfaceWidth;
            desiredHeight = surfaceHeight;
        }
        Size result = null;
        for (Size size : sizes) {
            if (desiredWidth <= size.getWidth() && desiredHeight <= size.getHeight()) {
                return size;
            }
            result = size;
        }
        return result;
    }

    /**
     * Test if the supplied orientation is in landscape.
     *
     * @param orientationDegrees Orientation in degrees (0,90,180,270)
     * @return True if in landscape, false if portrait
     */
    private boolean isLandscape(int orientationDegrees) {
        return (orientationDegrees == 90 ||
                orientationDegrees == 270);
    }

    /**
     * 获取拍照时偏移方向的角度(此方法在手机方向没有锁定，为自由切换时易用)
     *
     * @param activity
     * @param cameraId 开启相机的id(0.后相机,1.前相机)
     * @return 偏移的角度
     */
    public static int getCameraDisplayOrientation(Activity activity, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }
}
