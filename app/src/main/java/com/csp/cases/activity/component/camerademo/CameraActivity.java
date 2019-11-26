package com.csp.cases.activity.component.camerademo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.csp.cases.CaseApp;
import com.csp.cases.R;
import com.csp.cases.activity.component.camerademo.camera1.CameraPreview;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.classutil.BitmapUtil;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraActivity extends BaseButterKnifeActivity
        implements View.OnClickListener {

    @BindView(R.id.img_flash)
    ImageView mImgFlash;
    @BindView(R.id.txt_jump)
    TextView mTxtJump;
    @BindView(R.id.lfra_preview)
    FrameLayout mLfraPreview;
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
    public final static String KEY_IMAGE_DATA = "KEY_IMAGE_DATA";
    public final static File SAVE_FILE = new File(CaseApp.getApplication().getExternalCacheDir(), "CameraCacheFromCameraActivity.JPEG");

    /**
     * 图片流暂存
     */
    private byte[] mImageData;


    private CameraUtil mCameraUtil;

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, CameraActivity.class);
        activity.startActivityForResult(starter, REQUEST_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_camara_demo;


    }

    @Override
    protected void init() {
        showTakePicture(true);
        showUse(false);


//            }
//
//            @Override
//            public void onFailed(Context context) {
//                if (AndPermission.hasAlwaysDeniedPermission(context, Permission.Group.CAMERA)
//                        && AndPermission.hasAlwaysDeniedPermission(context, Permission.Group.STORAGE)) {
//                    AndPermission.with(context).runtime().setting().start();
//                }
//                ToastUtil.showToast(R.string.permission_camra_storage);
//                finish();
//            }
//        }, Permission.Group.STORAGE, Permission.Group.CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCameraUtil == null)
            initCamera();
    }

    private void initCamera() {
        Context context = this;

        if (mCameraUtil == null)
            mCameraUtil = new CameraUtil();

        mCameraUtil.initCamera(this);
        mCameraUtil.initCameraPreview(this);
//        Camera camera = mCameraUtil.getCamera();


//        mCamera = Camera.open();    //初始化 Camera对象
        CameraPreview mPreview = mCameraUtil.getPreview(); // new CameraPreview(context, camera);
//        return mPreview;

//        FrameLayout lfraCamera = findViewById(R.id.lfra_camera);
        mLfraPreview.removeAllViews();
        mLfraPreview.addView(mPreview);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mCameraUtil.releaseCamera();
    }

    @OnClick({R.id.img_flash, R.id.txt_jump, R.id.txt_cancel, R.id.img_lens_face, R.id.img_take_picture, R.id.txt_afresh, R.id.txt_use})
    @Override
    public void onClick(View v) {
        boolean toSelect;
        switch (v.getId()) {
            case R.id.img_flash:
                toSelect = !mImgFlash.isSelected();
                mCameraUtil.setFlashing(toSelect);
                mCameraUtil.switchFlash();
                mImgFlash.setSelected(toSelect);
                break;
            case R.id.txt_jump:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.txt_cancel:
                break;
            case R.id.img_lens_face:
                toSelect = !mImgLensFace.isSelected();
                mCameraUtil.setLensFace(toSelect ? Camera.CameraInfo.CAMERA_FACING_BACK
                        : Camera.CameraInfo.CAMERA_FACING_FRONT);
                initCamera();
//                mCameraUtil.initCamera(this);
                mImgLensFace.setSelected(toSelect);
                break;
            case R.id.img_take_picture:
                mCameraUtil.takePhoto(new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        mImageData = data;
                        showTakePicture(false);
                        showUse(true);

//                        Bitmap bitmap = BitmapUtil.toBitmap(mImageData).copy(Bitmap.Config.ARGB_8888, true);
//                        Canvas canvas = new Canvas();
//                        canvas.drawBitmap(bitmap, 0, 0, null);
//
//                        SurfaceHolder holder = mCameraUtil.getPreview().getHolder();
//                        Canvas canvas = holder.lockCanvas();
//                        if (canvas != null)
//                            canvas.drawBitmap(bitmap, 0, 0, null);
//                        else
//                            canvas = new Canvas(bitmap);
//
//                        holder.unlockCanvasAndPost(canvas);
                    }
                });
                break;
            case R.id.txt_afresh:
                mImageData = null;
                showTakePicture(true);
                showUse(false);
                mCameraUtil.afreshPreview();
                break;
            case R.id.txt_use:
                if (mImageData == null || mImageData.length == 0) {
                    ToastUtil.showToast("出现异常状况，请重新拍照");
                    return;
                }


//                byte[] bytes = new byte[mImageData.];
//                buffer.get(bytes);
                FileOutputStream output = null;
                try {
                    output = new FileOutputStream(SAVE_FILE);
                    output.write(mImageData);
                } catch (IOException e) {
                    LogCat.printStackTrace(e);
                } finally {
//                    mImage.close();
                    if (null != output) {
                        try {
                            output.close();
                        } catch (IOException e) {
                            LogCat.printStackTrace(e);
                        }
                    }
                }

                Intent intent = new Intent();
//                intent.putExtra(KEY_IMAGE_DATA, mImageData);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void showTakePicture(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;

        mImgFlash.setVisibility(show);
        mTxtJump.setVisibility(show);
        mTxtCancel.setVisibility(show);
        mImgLensFace.setVisibility(show);
        mImgTakePicture.setVisibility(showed ? View.VISIBLE : View.INVISIBLE);
    }

    private void showUse(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;

        mTxtAfresh.setVisibility(show);
        mTxtUse.setVisibility(show);
    }
}
