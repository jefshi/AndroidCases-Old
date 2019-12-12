package com.csp.cases.activity.component.camerademo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.library.java.fileSystem.FileUtil;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.classutil.BitmapUtil;
import com.csp.utils.android.log.LogCat;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

    public final static File SAVE_FILE = new File(CaseApp.getApplication().getExternalCacheDir(), "CameraCacheFromCameraActivity.JPEG");
    public final static int FLAG_CANCEL = 0;
    public final static int FLAG_JUMP = 1;
    public final static int FLAG_TAKE = 2;

    private boolean mShowJump;

    /**
     * 图片流暂存
     */
    private byte[] mImageData;

    private ICamera mCamera;

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
        if (getIntent() != null)
            mShowJump = getIntent().getBooleanExtra(KEY_SHOW_JUMP, false);

        showTakePicture(true);
        showUse(false);

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
                finish();
            }
        }, Manifest.permission.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null)
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
                .setLensFacing(CameraFlag.LENS_FACING_BACK)
                .setFlashMode(CameraFlag.FLASH_CLOSE)
                .setPictureTokenCallback(new PictureTokenCallback() {
                    @Override
                    public void onPictureTaken(byte[] imageData) {
                        mImageData = imageData;



                        try {
                            FileUtil.write(mImageData, SAVE_FILE, false);
//                            finishForResult(FLAG_TAKE);
                        } catch (IOException e) {
//                            ToastUtil.showToast("相片无法保存，请重新拍照");
                            LogCat.printStackTrace(e);
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showTakePicture(false);
                                showUse(true);
                                refreshImgVerify();
                            }
                        });
                        mCamera.onPause();

//                        mCamera.releaseCamera();
//                        View preview = mCamera.getPreview();
//                        if (preview instanceof CameraPreview) {
//                            // Camera 1
//                            Bitmap bitmap = BitmapUtil.toBitmap(mImageData); // .copy(Bitmap.Config.ARGB_8888, true);
//
//                            Matrix matrix = new Matrix();
//                            matrix.postScale(((float) preview.getWidth()) / bitmap.getWidth(),
//                                    ((float) preview.getHeight()) / bitmap.getHeight(),
//                                    0, 0);
//
//                            SurfaceHolder holder = ((CameraPreview) preview).getHolder();
//                            Canvas canvas = holder.lockCanvas();
//                            canvas.drawBitmap(bitmap, matrix, null);
//                            holder.unlockCanvasAndPost(canvas);
//                        } else if (preview instanceof TextureView) {
//                            // Camera 2
//                            Bitmap bitmap = BitmapUtil.toBitmap(mImageData).copy(Bitmap.Config.ARGB_8888, true);
//
//                            Canvas canvas = ((TextureView) preview).lockCanvas();
//                            canvas.setBitmap(bitmap);
//                            ((TextureView) preview).unlockCanvasAndPost(canvas);
//                        }
                    }
                }).setErrorCallback(new ErrorCallback() {
                    @Override
                    public void onError(int type, Throwable t) {
                        LogCat.printStackTrace(Log.DEBUG, null, t);
                        switch (type) {
                            case ErrorCallback.ERROR_NO_CAMERA:
                                // ToastUtil.showToast("该设备不存在摄像头，无法进行拍照");
                                // finishForResult(FLAG_CANCEL);
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

    @OnClick({R.id.img_flash, R.id.txt_jump, R.id.txt_cancel, R.id.img_lens_face, R.id.img_take_picture, R.id.txt_afresh, R.id.txt_use})
    @Override
    public void onClick(View v) {
        boolean toSelect;
        switch (v.getId()) {
            case R.id.img_flash:
                toSelect = !mImgFlash.isSelected();
                int mode = toSelect ? CameraFlag.FLASH_OPEN : CameraFlag.FLASH_CLOSE;
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
                if (mCamera.setLensFace(lensFacing)) {
                    mImgLensFace.setSelected(toSelect);
                    resetCameraAndPreview();
                }
                break;
            case R.id.img_take_picture:
                mCamera.takePicture();
                break;
            case R.id.txt_afresh:
                mImageData = null;
                showTakePicture(true);
                showUse(false);
                mCamera.onResume();
                resetCameraAndPreview();
                break;
            case R.id.txt_use:
                if (mImageData == null || mImageData.length == 0) {
                    ToastUtil.showToast("相片数据获取失败，请重新拍照");
                    return;
                }

//                try {
//                    FileUtil.write(mImageData, SAVE_FILE, false);
//                    finishForResult(FLAG_TAKE);
//                } catch (IOException e) {
//                    ToastUtil.showToast("相片无法保存，请重新拍照");
//                    LogCat.printStackTrace(e);
//                }
                finishForResult(FLAG_TAKE);
                break;
        }
    }

    private void showTakePicture(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;
        int show02 = showed ? View.VISIBLE : View.INVISIBLE;

        mImgFlash.setVisibility(show);
        mTxtCancel.setVisibility(show);
        mImgLensFace.setVisibility(show);

        mImgTakePicture.setVisibility(show02);
        mLfraPreview.setVisibility(show02);
        mTxtJump.setVisibility(showed && mShowJump ? View.VISIBLE : View.GONE);
    }

    private void showUse(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;

        mImgVerify.setVisibility(show);
        mTxtAfresh.setVisibility(show);
        mTxtUse.setVisibility(show);
    }

    /**
     * 不建议使用相机返回的数据，因为在某些手机上，后置摄像头也会莫名旋转90读，如小米
     */
    private void refreshImgVerify() {
        Bitmap bitmap = BitmapFactory.decodeFile(SAVE_FILE.getAbsolutePath());


        Bitmap bitmap = BitmapUtil.toBitmap(mImageData);
        bitmap = BitmapUtil.scaleBitmap(bitmap,
                (float) mLfraPreview.getWidth(),
                (float) mLfraPreview.getHeight());

        if (mCamera.getLensFace() == CameraFlag.LENS_FACING_FRONT)
            bitmap = BitmapUtil.rotateBitmap(bitmap, 180);

        mImgVerify.setImageBitmap(bitmap);
    }
}
