package com.csp.cases.activity.component.camerademo;

import com.csp.cases.activity.component.camerademo.camera.constant.CameraFlag;

public class Camera02Activity extends CameraMixActivity {

    @Override
    protected void init() {
        mCameraApi = CameraFlag.CAMERA_API_2;

        super.init();
    }
}
