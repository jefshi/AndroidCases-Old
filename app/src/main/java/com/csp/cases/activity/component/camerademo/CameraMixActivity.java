package com.csp.cases.activity.component.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.csp.cases.CaseApp;
import com.csp.cases.R;
import com.csp.cases.activity.component.camerademo.camera.ICamera;
import com.csp.cases.activity.component.camerademo.camera.PictureTokenCallback;
import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;
import com.csp.library.java.fileSystem.FileUtil;
import com.csp.utils.android.ToastUtil;
import com.csp.utils.android.log.LogCat;

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

        initCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera.onPause();
    }

    private void initCamera() {
        if (mCamera == null) {
            mCamera = new ICamera.Builder()
                    .setLensFacing(CameraFlag.LENS_FACING_BACK)
                    .setFlashMode(CameraFlag.FLASH_CLOSE)
                    .setPictureTokenCallback(new PictureTokenCallback() {
                        @Override
                        public void onPictureTaken(byte[] imageData) {
                            mImageData = imageData;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showTakePicture(false);
                                    showUse(true);
                                }
                            });
                        }
                    }).build(this);
        }
        mLfraPreview.removeAllViews();
        mLfraPreview.addView(mCamera.getPreview());
    }

    public void finishForResult(int flag) {
        Intent intent = new Intent();
        intent.putExtra(KEY_FINISH_FLAG, flag);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick({R.id.img_flash, R.id.txt_jump, R.id.txt_cancel, R.id.img_lens_face, R.id.img_take_picture, R.id.txt_afresh, R.id.txt_use})
    @Override
    public void onClick(View v) {
        boolean toSelect;
        switch (v.getId()) {
            case R.id.img_flash:
                toSelect = !mImgFlash.isSelected();
                mImgFlash.setSelected(toSelect);
                mCamera.setFlashMode(toSelect ? CameraFlag.FLASH_OPEN : CameraFlag.FLASH_CLOSE);
                break;
            case R.id.txt_jump:
                finishForResult(FLAG_JUMP);
                break;
            case R.id.txt_cancel:
                finishForResult(FLAG_CANCEL);
                break;
            case R.id.img_lens_face:
                toSelect = !mImgLensFace.isSelected();
                mImgLensFace.setSelected(toSelect);
                mCamera.setLensFace(toSelect ? CameraFlag.LENS_FACING_BACK
                        : CameraFlag.LENS_FACING_FRONT);
                break;
            case R.id.img_take_picture:
                mCamera.takePicture();
                break;
            case R.id.txt_afresh:
                mImageData = null;
                showTakePicture(true);
                showUse(false);
                mCamera.resumePreview();
                break;
            case R.id.txt_use:
                if (mImageData == null || mImageData.length == 0) {
                    ToastUtil.showToast("相片数据获取失败，请重新拍照");
                    return;
                }

                try {
                    FileUtil.write(mImageData, SAVE_FILE, false);
                    finishForResult(FLAG_TAKE);
                } catch (IOException e) {
                    ToastUtil.showToast("相片无法保存，请重新拍照");
                    LogCat.printStackTrace(e);
                }
                break;
        }
    }

    private void showTakePicture(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;

        mImgFlash.setVisibility(show);
        mTxtCancel.setVisibility(show);
        mImgLensFace.setVisibility(show);
        mImgTakePicture.setVisibility(showed ? View.VISIBLE : View.INVISIBLE);

        mTxtJump.setVisibility(showed && mShowJump ? View.VISIBLE : View.GONE);
    }

    private void showUse(boolean showed) {
        int show = showed ? View.VISIBLE : View.GONE;

        mTxtAfresh.setVisibility(show);
        mTxtUse.setVisibility(show);
    }
}
