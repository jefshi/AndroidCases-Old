package com.csp.cases.activity.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.FrameLayout;

import com.csp.cases.R;
import com.csp.cases.activity.component.CameraPreview;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;

/**
 * https://www.jianshu.com/p/9a2e66916fcb
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Activity extends Activity {

//
//    /**
//     * 相机类
//     */
//    private Camera mCamera;
//
//    /**
//     * *是否开启闪光灯
//     */
//    private boolean isFlashing  =true;
//    /**
//     * 拍照标记
//     */
//    private boolean isTakePhoto;
//
//    /**
//     * 图片流暂存
//     */
//    private byte[] imageData;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ac_camara);
//
//        findViewById(R.id.camera_photobutton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!isTakePhoto) {
//                    takePhoto();
//                }
//            }
//        });
//
////        getSystemService()
////
////        private val cameraManager: CameraManager by lazy { getSystemService(CameraManager::class.java) }
//
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mCamera == null)
//            initCamera();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        releaseCamera();
//    }
//
//
//    private void initCamera() {
////        notEnterpriseCard.setVisibility(View.GONE);
////        notButton.setVisibility(View.GONE);
////        cameraData.setVisibility(View.VISIBLE);
//        // 判断是否有摄像头
//        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
//            return;
////        //判断是否有权限
////        PermissionUtils.applicationPermissions(this, new PermissionUtils.PermissionListener() {
////            @Override
////            public void onSuccess(Context context) {
//        LogCat.i("初始化相机");
//        mCamera = Camera.open();    //初始化 Camera对象
//        CameraPreview mPreview = new CameraPreview(this, mCamera);
//
//        FrameLayout cameraPreview = findViewById(R.id.camera_preview);
//        cameraPreview.addView(mPreview);
////            }
////
////            @Override
////            public void onFailed(Context context) {
////                if (AndPermission.hasAlwaysDeniedPermission(context, Permission.Group.CAMERA)
////                        && AndPermission.hasAlwaysDeniedPermission(context, Permission.Group.STORAGE)) {
////                    AndPermission.with(context).runtime().setting().start();
////                }
////                ToastUtil.showToast(R.string.permission_camra_storage);
////                finish();
////            }
////        }, Permission.Group.STORAGE, Permission.Group.CAMERA);
//    }
//
//    private void releaseCamera() {
//        LogCat.i("释放相机");
//        if (mCamera != null) {
//            mCamera.setPreviewCallback(null);
//            mCamera.stopPreview();
//            mCamera.lock();
//            mCamera.release();
//            mCamera = null;
//        }
//    }
//
//    /**
//     * 注释：拍照并保存图片到相册
//     */
//    private void takePhoto() {
//        isTakePhoto = true;
//        switchFlash();
//        //调用相机拍照
//        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
//            @Override
//            public void onPictureTaken(byte[] data, Camera camera) {
//                //视图动画
////                cameraPhotobutton.setVisibility(View.GONE);
////                cameraGroup.setVisibility(View.GONE);
////                cameraTitle.setVisibility(View.GONE);
////                cameraConfirmLayout.setVisibility(View.VISIBLE);
////                //AnimSpring.getInstance(mConfirmLayout).startRotateAnim(120, 360);
//                imageData = data;
//                //停止预览
//                mCamera.stopPreview();
//            }
//        });
//    }
//
//
//    /**
//     * 注释：切换闪光灯
//     */
//    private void switchFlash() {
//        LogCat.i("是否开启闪光灯" + isFlashing);
//        //mFlashButton.setImageResource(isFlashing ? R.mipmap.flash_open : R.mipmap.flash_close);
//        //AnimSpring.getInstance(mFlashButton).startRotateAnim(120, 360);
//        try {
//            Camera.Parameters parameters = mCamera.getParameters();
//            parameters.setFlashMode(isFlashing ? Camera.Parameters.FLASH_MODE_ON : Camera.Parameters.FLASH_MODE_OFF);
//            mCamera.setParameters(parameters);
//        } catch (Exception e) {
//            ToastUtil.showToast("该设备不支持闪光灯");
//        }
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        //多图片选择
////        if (requestCode == PhotoManager.REQUEST_IMAGE) {
////            if (resultCode == MultiImageSelectorActivity.RESULT_CANCELED) {
////                return;
////            }
////            // 点击完成返回的数据
////            if (resultCode == MultiImageSelectorActivity.RESULT_OK) {
////                resultList = new ArrayList<>();
////                // 选择图片后返回来的数据:图片在本地的存储路径
////                resultList = data.getExtras().getStringArrayList(MultiImageSelectorActivity.EXTRA_RESULT);
////                if (resultList.size() > 0)
////                    getPresenter().uploadFiles(resultList, type, yunAutonymnodes);
////            }
////        }
//    }
//
////    /**
////     * 注释：取消保存
////     */
////    @Override
////    public void cancleSavePhoto() {
////        cameraPhotobutton.setVisibility(View.VISIBLE);
////        cameraGroup.setVisibility(View.VISIBLE);
////        cameraTitle.setVisibility(View.VISIBLE);
////        cameraConfirmLayout.setVisibility(View.GONE);
////        //AnimSpring.getInstance(mPhotoLayout).startRotateAnim(120, 360);
////        //开始预览
////        mCamera.startPreview();
////        imageData = null;
////        isTakePhoto = false;
////    }

    CameraPreview mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camara);
        FrameLayout lfraCamera = findViewById(R.id.lfra_camera);

        mCamera = new CameraPreview(this);
        lfraCamera.addView(mCamera);

//        cameraView = (CameraPreview) findViewById(R.id.cameraView);

        findViewById(R.id.camera_photobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePic(view);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume(this);
    }

    @Override
    protected void onPause() {
        mCamera.onPause();
        super.onPause();
    }

    public void takePic(View view) {
        mCamera.takePicture();
    }
}
