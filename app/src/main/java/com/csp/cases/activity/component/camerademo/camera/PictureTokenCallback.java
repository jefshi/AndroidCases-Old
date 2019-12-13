package com.csp.cases.activity.component.camerademo.camera;

/**
 * 拍照回调。注意：前置摄像头的返回数据是上下颠倒的，同时不会做任何机型处理
 * Created by csp on 2019/12/13
 * Modified by csp on 2019/12/13
 *
 * @version 1.0.0
 */
public interface PictureTokenCallback {

    void onPictureTaken(byte[] imageData);
}