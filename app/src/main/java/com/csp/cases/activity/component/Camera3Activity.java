package com.csp.cases.activity.component;

import android.app.Activity;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.FrameLayout;

import com.csp.cases.R;
import com.csp.cases.activity.component.camera2.AutoFitTextureView;
import com.csp.cases.activity.component.camera2.Camera2Util;

/**
 * https://www.jianshu.com/p/9a2e66916fcb
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera3Activity extends Activity {

    Camera2Util mCamera2Util;

//    /**
//     * 相机预览的 View
//     */
//    private AutoFitTextureView mTextureView;


//    CameraPreview mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camara);
        FrameLayout cameraPreview = findViewById(R.id.lfra_camera);

        AutoFitTextureView mTextureView = new AutoFitTextureView(this);
        cameraPreview.addView(mTextureView);

        mCamera2Util = new Camera2Util.Builder()
                .setLensFacing(CameraCharacteristics.LENS_FACING_FRONT)
                .setTextureView(mTextureView)
                .build();

//        mCamera2Util.setTextureView(mTextureView);

        findViewById(R.id.camera_photobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera2Util.takePicture();
            }
        });
    }

    /**
     * Initiate a still image capture.
     */
    private void takePicture() {
//        lockFocus();
    }


    @Override
    protected void onResume() {
        mCamera2Util.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mCamera2Util.onPause();
        super.onPause();
    }

//    public void takePic(View view) {
//        mCamera.takePicture();
//    }
}
