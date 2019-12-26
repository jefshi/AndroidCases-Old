package com.csp.cases.activity.component.camerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.csp.cases.CaseApp;
import com.csp.cases.R;
import com.csp.cases.activity.component.camerademo.camera.ErrorCallback;
import com.csp.cases.activity.component.camerademo.camera.ICamera;
import com.csp.cases.activity.component.camerademo.camera.PictureTokenCallback;
import com.csp.cases.activity.component.camerademo.camera.annotation.ACameraApi;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.cases.activity.component.camerademo.camera2.AutoFitTextureView;
import com.csp.utils.android.ImageUtils;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 参考：
 * 1. Camera 2 官网：git@github.com:jefshi/google-camera-samples.git
 * 1. git@github.com:yangmingchuan/CameraMaster.git
 * 2.
 * 3. git@github.com:infinum/Android-GoldenEye.git
 */
public class CameraMixActivity extends BaseButterKnifeActivity
        implements View.OnClickListener {

    @BindView(R.id.img_flash)
    ImageView mImgFlash;
    @BindView(R.id.txt_jump)
    TextView mTxtJump;
    @BindView(R.id.lfra_preview)
    FrameLayout mLfraPreview;
    @BindView(R.id.img_verify)
    ImageView mImgVerify;
    @BindView(R.id.txt_cancel)
    TextView mTxtCancel;
    @BindView(R.id.img_take_picture)
    ImageView mImgTakePicture;
    @BindView(R.id.img_lens_face)
    ImageView mImgLensFace;
    @BindView(R.id.txt_afresh)
    TextView mTxtAfresh;
    @BindView(R.id.txt_use)
    TextView mTxtUse;

    public final static int REQUEST_CODE = 19117;
    public final static String KEY_SHOW_JUMP = "KEY_SHOW_JUMP";
    public final static String KEY_FINISH_FLAG = "KEY_FINISH_FLAG";

    public final static File SAVE_FILE = new File(CaseApp.getApplication().getExternalCacheDir(), "CameraCacheFromCameraActivity.jpeg");
    public final static int FLAG_CANCEL = 0;
    public final static int FLAG_JUMP = 1;
    public final static int FLAG_TAKE = 2;

    private boolean mShowJump;

    /**
     * 图片流暂存
     */
    private Bitmap mBitmap;

    private ICamera mCamera;

    // 测试用数据
    @ACameraApi
    protected int mCameraApi;

    public static void start(Activity activity) {
        start(activity, false);
    }

    /**
     * @param showJump true：显示【跳过】
     */
    public static void start(Activity activity, boolean showJump) {
        Intent starter = new Intent(activity, CameraMixActivity.class);
        starter.putExtra(KEY_SHOW_JUMP, showJump);
        activity.startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_camara_demo;
    }

    @Override
    protected void init() {
        LogCat.e("当前手机 Model = %s", Build.MODEL);

        if (getIntent() != null)
            mShowJump = getIntent().getBooleanExtra(KEY_SHOW_JUMP, false);

        showTakePicture(true);

        // 权限控制
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            resetCameraAndPreview();
            return;
        }

        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                resetCameraAndPreview();
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                //用户拒绝了权限的申请
                finishForResult(FLAG_CANCEL);
            }
        }, Manifest.permission.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null || !isTakePicture())
            return;

        if (mCamera.getCameraApi() == CameraFlag.CAMERA_API_1) {
            mCamera.onResume();
            resetCameraAndPreview();
        } else {
            mCamera.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null)
            mCamera.onPause();
    }

    private void resetCameraAndPreview() {
        // Camera 2 跳过操作
        if (mCamera != null
                && mCamera.getCameraApi() == CameraFlag.CAMERA_API_2)
            return;

        if (mCamera == null)
            mCamera = initCamera();

        if (mCamera == null) {
            ToastUtil.showToast("无法获取摄像头，设备可能不存在摄像头或者未授予相机权限");
            finishForResult(FLAG_CANCEL);
            return;
        }

        mLfraPreview.removeAllViews();
        mLfraPreview.addView(mCamera.getPreview());
    }

    @Nullable
    private ICamera initCamera() {
        return new ICamera.Builder(getActivity(), getContext())
                .setCameraApi(mCameraApi)
                .setPreViewForApi2(new AutoFitTextureView(this))
                .setLensFacing(CameraFlag.LENS_FACING_BACK)
                .setFlashMode(CameraFlag.FLASH_CLOSE)
                .setPictureTokenCallback(new PictureTokenCallback() {
                    @Override
                    public void onPictureTaken(byte[] imageData) {
                        // TODO 数据处理
                        Bitmap bitmap = switchoverBitmap(imageData);
                        if (bitmap == null) {
                            ToastUtil.showToast("相片数据获取失败，请重新拍照");
                            return;
                        }

                        mBitmap = bitmap;
                        mCamera.onPause();
                        showTakePicture(false);
                        refreshImgVerify();

//                        // 使用相机的预览 View 查看拍照图片。
//                        // 但有个问题，因为 onPause 的缘故，切到后台重新切回来时，回黑屏
//                        lookUpTokenPicture();
                    }
                }).setErrorCallback(new ErrorCallback() {
                    @Override
                    public void onError(int type, Throwable t) {
                        LogCat.printStackTrace(Log.DEBUG, null, t);
                        switch (type) {
                            case ErrorCallback.ERROR_NO_CAMERA:
                                ToastUtil.showToast("该设备不存在摄像头，无法进行拍照");
                                finishForResult(FLAG_CANCEL);
                                break;
                            case ErrorCallback.ERROR_FLASH:
                                ToastUtil.showToast("该设备不支持闪光灯");
                                break;
                            case ErrorCallback.ERROR_LENS_FACE:
                                ToastUtil.showToast("该设备不支持转换摄像头");
                                break;
                            case ErrorCallback.ERROR_TOKEN_PICTURE:
                                ToastUtil.showToast("拍照失败，请重新拍照");
                                break;
                        }
                    }
                }).build(this);
    }

//    /**
//     * 使用相机的预览 View 查看拍照图片。
//     * 但有个问题，因为 onPause 的缘故，切到后台重新切回来时，回黑屏
//     */
//    private void lookUpTokenPicture() {
//        mCamera.releaseCamera();
//        View preview = mCamera.getPreview();
//        if (preview instanceof CameraPreview) {
//            // Camera 1
//            Bitmap bitmap = ImageUtils.getBitmap(imageData, 0); // .copy(Bitmap.Config.ARGB_8888, true);
//
//            Matrix matrix = new Matrix();
//            matrix.postScale(((float) preview.getWidth()) / bitmap.getWidth(),
//                    ((float) preview.getHeight()) / bitmap.getHeight(),
//                    0, 0);
//
//            SurfaceHolder holder = ((CameraPreview) preview).getHolder();
//            Canvas canvas = holder.lockCanvas();
//            canvas.drawBitmap(bitmap, matrix, null);
//            holder.unlockCanvasAndPost(canvas);
//        } else if (preview instanceof TextureView) {
//            // Camera 2
//            Bitmap bitmap = ImageUtils.getBitmap(imageData, 0).copy(Bitmap.Config.ARGB_8888, true);
//
//            Canvas canvas = ((TextureView) preview).lockCanvas();
//            canvas.setBitmap(bitmap);
//            ((TextureView) preview).unlockCanvasAndPost(canvas);
//        }
//    }

    private Bitmap switchoverBitmap(byte[] imageData) {
        Bitmap bitmap = ImageUtils.getBitmap(imageData, 0);
        if (bitmap == null) {
            ToastUtil.showToast("相片数据获取失败，请重新拍照");
            return null;
        }

        LogCat.e("w = %s, h = %s", bitmap.getWidth(), bitmap.getHeight());

        // 自定义拍照，部分机型图片旋转问题
//        String[] devices = new String[]{"MIX 2S", "Redmi K20 Pro"};
//        for (String device : devices) {
        if (bitmap.getWidth() > bitmap.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
//                break;
        }
//        }

        Matrix matrix = new Matrix();
        matrix.setScale(((float) mLfraPreview.getWidth()) / bitmap.getWidth(),
                ((float) mLfraPreview.getHeight()) / bitmap.getHeight());
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        if (mCamera.getLensFace() == CameraFlag.LENS_FACING_FRONT) {
            matrix = new Matrix();
            matrix.setRotate(180);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

//            // 左右翻转
//            matrix = new Matrix();
//            matrix.setScale(-1, 1);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        finishOnlySetResult(FLAG_CANCEL);
        super.onBackPressed();
    }

    private void finishOnlySetResult(int flag) {
        Intent intent = new Intent();
        intent.putExtra(KEY_FINISH_FLAG, flag);
        setResult(RESULT_OK, intent);
    }

    private void finishForResult(int flag) {
        finishOnlySetResult(flag);
        finish();
    }

    private boolean isFlashClose() {
        return !mImgFlash.isSelected();
    }

    private boolean isLensFaceFront() {
        return mImgLensFace.isSelected();
    }

    private void showTakePicture(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;
        int show02 = showed ? View.VISIBLE : View.INVISIBLE;
        int gone = showed ? View.GONE : View.VISIBLE;

        mImgFlash.setVisibility(show);
        mTxtCancel.setVisibility(show);
        mImgLensFace.setVisibility(show);

        mImgTakePicture.setVisibility(show02);
        mLfraPreview.setVisibility(show02);
        mTxtJump.setVisibility(showed && mShowJump ? View.VISIBLE : View.GONE);

        mImgVerify.setVisibility(gone);
        mTxtAfresh.setVisibility(gone);
        mTxtUse.setVisibility(gone);
    }

    private boolean isTakePicture() {
        return mImgTakePicture.getVisibility() == View.VISIBLE;
    }

    /**
     * mImgVerify 控件是为了解决确认照片时，home 键切到后台，重新返回前台图片不能查看的问题
     * <p>
     * 不建议使用相机返回的数据，因为在某些手机上，后置摄像头也会莫名旋转90读，如小米
     */
    private void refreshImgVerify() {
        mImgVerify.setImageBitmap(mBitmap);
    }

    @OnClick({R.id.img_flash, R.id.txt_jump, R.id.txt_cancel, R.id.img_lens_face, R.id.img_take_picture, R.id.txt_afresh, R.id.txt_use})
    @Override
    public void onClick(View v) {
        boolean toSelect;
        switch (v.getId()) {
            case R.id.img_flash:
                toSelect = !mImgFlash.isSelected();
                if (toSelect && isLensFaceFront()) {
                    ToastUtil.showToast("前置摄像头不能打开闪光灯");
                    return;
                }
                int mode = toSelect ? CameraFlag.FLASH_LIGHT : CameraFlag.FLASH_CLOSE;
                if (mCamera.setFlashMode(mode))
                    mImgFlash.setSelected(toSelect);
                break;
            case R.id.txt_jump:
                finishForResult(FLAG_JUMP);
                break;
            case R.id.txt_cancel:
                finishForResult(FLAG_CANCEL);
                break;
            case R.id.img_lens_face:
                toSelect = !mImgLensFace.isSelected();
                int lensFacing = toSelect ? CameraFlag.LENS_FACING_FRONT : CameraFlag.LENS_FACING_BACK;
                // 前置摄像头闪光灯重置为关闭
                if (lensFacing == CameraFlag.LENS_FACING_FRONT && !isFlashClose())
                    onClick(mImgFlash);

                // 切换摄像头
                if (mCamera.setLensFace(lensFacing)) {
                    mImgLensFace.setSelected(toSelect);
                    resetCameraAndPreview();
                }
                break;
            case R.id.img_take_picture:
                mCamera.takePicture();
                break;
            case R.id.txt_afresh:
                mBitmap = null;
                showTakePicture(true);
                mCamera.onResume();
                resetCameraAndPreview();
                break;
            case R.id.txt_use:
                if (mBitmap == null) {
                    ToastUtil.showToast("相片数据获取失败，请重新拍照");
                    return;
                }
                boolean save = ImageUtils.save(mBitmap, SAVE_FILE, Bitmap.CompressFormat.JPEG);
                if (save)
                    finishForResult(FLAG_TAKE);
                else
                    ToastUtil.showToast("相片无法保存，请重新拍照");
                break;
        }
    }
}
