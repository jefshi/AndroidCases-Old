package com.csp.cases.activity.animation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考博客：https://blog.csdn.net/totond/article/details/77881180
 */
public class GestureDetectorActivity extends BaseListActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        return items;
    }

    @Override
    public void initViewContent() {
        imgItem.setImageResource(R.mipmap.item_card);
        super.initViewContent();
    }

    @Override
    public void initViewEvent() {
        super.initViewEvent();

        // OnGestureListener，这个Listener监听一些手势，如单击、滑动、长按等操作
        GestureDetector mGestureDetector = new GestureDetector(this, this);
        // mGestureDetector.setIsLongpressEnabled(false); // 长按时间被捕获后，将不会回调其他事件

        // OnDoubleTapListener，这个Listener监听双击和单击事件
        mGestureDetector.setOnDoubleTapListener(this);

        // OnContextClickListener，接上外部设备时，当鼠标/触摸板，右键点击时候的回调
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGestureDetector.setContextClickListener(e -> {
                LogCat.e("OnContextClickListener.onContextClick，回调");
                return false;
            });
        }

        // SimpleOnGestureListener，实现了上面三个接口的类，拥有上面三个的所有回调方法。
        // GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener();
        // mGestureDetector = new GestureDetector(this, listener);

        View view = getView();
        view.setOnTouchListener((v, event)
                -> mGestureDetector.onTouchEvent(event));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setOnGenericMotionListener((v, event)
                    -> mGestureDetector.onGenericMotionEvent(event));
        }
    }

    /**
     * 用户按下屏幕的时候的回调。
     */
    @Override
    public boolean onDown(MotionEvent e) {
        LogCat.e("OnGestureListener.onDown，回调");
        return false;
    }

    /**
     * 用户按下按键后100ms（根据Android7.0源码）还没有松开或者移动就会回调，官方在源码的解释是说一般用于告诉用户已经识别按下事件的回调（我暂时想不出有什么用途，因为这个回调触发之后还会触发其他的，不像长按）。
     */
    @Override
    public void onShowPress(MotionEvent e) {
        LogCat.e("OnGestureListener.onShowPress，回调");
    }

    /**
     * 用户手指松开（UP事件）的时候如果没有执行onScroll()和onLongPress()这两个回调的话，就会回调这个，说明这是一个点击抬起事件，但是不能区分是否双击事件的抬起。
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogCat.e("OnGestureListener.onSingleTapUp，回调");
        return false;
    }

    /**
     * 用户长按后（好像不同手机的时间不同，源码里默认是100ms+500ms）触发，触发之后不会触发其他回调，直至松开（UP事件）。
     */
    @Override
    public void onLongPress(MotionEvent e) {
        LogCat.e("OnGestureListener.onLongPress，回调");
    }

    /**
     * 手指滑动的时候执行的回调（接收到MOVE事件，且位移大于一定距离），e1,e2分别是之前DOWN事件和当前的MOVE事件，distanceX和distanceY就是当前MOVE事件和上一个MOVE事件的位移量。
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        LogCat.e(String.format("OnGestureListener.onScroll: x 轴距离 %s，y 轴距离 %s", distanceX, distanceY));
        return false;
    }

    /**
     * 用户执行抛操作之后的回调，MOVE事件之后手松开（UP事件）那一瞬间的x或者y方向速度，如果达到一定数值（源码默认是每秒50px），就是抛操作（也就是快速滑动的时候松手会有这个回调，因此基本上有onFling必然有onScroll）。
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogCat.e(String.format("OnGestureListener.onFling: x 轴速率 %s，y 轴速率 %s", velocityX, velocityY));
        return false;
    }

    /**
     * 可以确认（通过单击DOWN后300ms没有下一个DOWN事件确认）这不是一个双击事件，而是一个单击事件的时候会回调。
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogCat.e("OnDoubleTapListener.onSingleTapConfirmed，回调");
        return false;
    }

    /**
     * 可以确认这是一个双击事件的时候回调。
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        LogCat.e("OnDoubleTapListener.onDoubleTap，回调");
        return false;
    }

    /**
     * onDoubleTap()回调之后的输入事件（DOWN、MOVE、UP）都会回调这个方法（这个方法可以实现一些双击后的控制，如让View双击后变得可拖动等）
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogCat.e("OnDoubleTapListener.onDoubleTapEvent，回调");
        return false;
    }
}