package com.csp.cases.activity.component.camerademo.camera1;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.csp.utils.android.log.LogCat;

import java.io.IOException;

/**
 * @Author zhaoyafei
 * @Time 2019/8/21  16:13
 * @Description ：基础版摄像头不太清楚
 */
public class CameraPreviews extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    /**
     * 预览尺寸集合
     */
    private final SizeMap mPreviewSizes = new SizeMap();

    public CameraPreviews(Context context, Camera camera) {
        super(context);
        //初始化Camera对象
        mCamera = camera;
        //得到SurfaceHolder对象
        mHolder = getHolder();
        //添加回调，得到Surface的三个声明周期方法
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //设置预览方向
            mCamera.setDisplayOrientation(90);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(holder);
            //开启预览效果
            mCamera.startPreview();
        } catch (IOException e) {
            LogCat.printStackTrace("相机预览错误: ", e);
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

    }

}
