package com.csp.cases.activity.component.camerademo;

import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

public class Camera01Activity extends CameraMixActivity {

    @Override
    protected void init() {
        mCameraApi = CameraFlag.CAMERA_API_1;

        super.init();
    }
}
