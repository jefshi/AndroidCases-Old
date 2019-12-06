package com.csp.cases.activity.component.camerademo.camera;

import android.view.View;

public class Camera1Impl implements ICamera {

    private ICamera.Builder mBuilder;

    public Camera1Impl(Builder builder) {
        mBuilder = builder;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public View getPreview() {
        return null;
    }

    @Override
    public void startPreview() {

    }

    @Override
    public void stopPreview() {

    }

    @Override
    public void resumePreview() {

    }

    @Override
    public void takePicture() {

    }

    @Override
    public void setFlashMode(int mode) {

    }

    @Override
    public void setLensFace(int lensFacing) {

    }
}
