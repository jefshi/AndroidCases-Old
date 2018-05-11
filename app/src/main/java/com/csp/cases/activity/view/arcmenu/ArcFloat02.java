package com.csp.cases.activity.view.arcmenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.cases.activity.view.other.RECState;
import com.csp.utils.android.MetricsUtil;

/**
 * Created by chenshp on 2018/3/30.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcFloat02 extends FrameLayout implements IWindowLayoutParams {
    private ImageView imgRecordFloat;
    private Chronometer chronometer;

    private WindowManager wManager;
    private WindowManager.LayoutParams wlp;


    // 滑动
    private float lastX;
    private float lastY;
    private final float TOUCH_TOLERANCE = 6;
    private float mTouchTolerance;

    @Override
    public WindowManager.LayoutParams getWindowLayoutParams() {
        return wlp;
    }

    @Override
    public void setWindowLayoutParams(int x, int y) {
        wlp.x = x;
        wlp.y = y;
    }


    public ArcFloat02(@NonNull Context context) {
        this(context, null, 0, 0);
    }


    public ArcFloat02(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ArcFloat02(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcFloat02(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }
//
//    public ArcFloat02(@NonNull Context context, ArcMenu arcMenu, ArcExitView arcExitView, RelativeLayout arcTipView) {
//        super(context, null, 0, 0);
//
//        init(context);
//    }


    private ArcFloatManager arcManager;

    public ArcFloat02(@NonNull Context context, ArcFloatManager arcManager) {
        this(context, null, 0, 0);
        this.arcManager = arcManager;
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_arc_float, this, true);

        imgRecordFloat = (ImageView) findViewById(R.id.img_record_float);
        chronometer = (Chronometer) findViewById(R.id.chronometer_float);

        refreshChronometer(false);

        initWindowLayoutParams();

        initMetrics(context);
    }

    @Override
    public void initWindowLayoutParams() {
        if (wlp != null)
            return;

        wlp = new WindowManager.LayoutParams();
        wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        wlp.gravity = Gravity.START | Gravity.TOP;
        wlp.x = 200; // TODO 初始位置
        wlp.y = 300;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = 0;
        int maxHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            maxWidth = maxWidth < childWidth ? childWidth : maxWidth;
            maxHeight = maxHeight < childHeight ? childHeight : maxHeight;
        }
        setMeasuredDimension(maxWidth, maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        wlp.height = getWidth();
        wlp.width = getHeight();
    }


//    @Override
//    public void onClick(View v) {
//        // TODO 调整数据,使它对准
//        arcMenu.switchShow(wlp.x + wlp.width / 2, wlp.y + wlp.height / 2);
//    }

    // ？？？
    private void refreshChronometer(boolean start) {
        imgRecordFloat.setVisibility(start ? INVISIBLE : VISIBLE);
        chronometer.setVisibility(start ? VISIBLE : GONE);
        chronometer.setBase(SystemClock.elapsedRealtime() - RECState.getRecordTime());
        if (start)
            chronometer.start();
        else
            chronometer.stop();
    }

    private boolean dragging;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        final float x = event.getRawX();
        final float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = x - lastX;
                float deltaY = y - lastY;
                if (dragging || Math.abs(deltaX) > mTouchTolerance || Math.abs(deltaY) > mTouchTolerance) {
                    dragging = true;

                    wlp.x += deltaX;
                    wlp.y += deltaY;
                    lastX = x;
                    lastY = y;


                    arcManager.dragAnywhere(x, y);
                }







//                float deltaX = x - lastX;
//                float deltaY = y - lastY;
//                if (dragging || Math.abs(deltaX) > mScaledTouchSlop || Math.abs(deltaY) > mScaledTouchSlop) {
//                    dragging = true;
////                    if (!RECState.isIsREC()) {
////                        mExitView.show();
////                    }
//                    if (layoutParams.x <= mScaledTouchSlop && x <= mTouchStartX) {
//                        downAnim();
//                    } else if (mScreenWidth - layoutParams.x - DisplayUtil.dp2px(MIN_LENGTH) <= mScaledTouchSlop && x >= mTouchStartX) {
//                        downAnim();
//                    } else {
//                        showFloatIcon();
//                    }
//                    mainHandler.removeCallbacks(longPressRunnable);
//                }
//                /** 移动到了删除控件的范围内 **/
//                if (mExitView.isTouchPointInView(x, y)) {
//                    // 同时震动一下
//                    if (!isVbirated) {
//                        Utils.vibrate(context);
//                    }
//                    isVbirated = true;
//                    isGoToDelete = true;
//                    int[] center = new int[2];
//                    mExitView.getCenterXY(center);
//                    layoutParams.x = center[0] - layoutParams.width / 2;
//                    layoutParams.y = center[1] - layoutParams.height / 2;
//                    mWindowManager.updateViewLayout(iconFloatFl, layoutParams);
//                } else {
//                    isVbirated = false;
//                    isGoToDelete = false;
//                    updateViewPosition(x - iconFloatFl.getWidth() / 2, y - iconFloatFl.getHeight());
//                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                arcManager.motionUp();


                break;
        }
        return true;
    }


    boolean portrait; // 默认竖屏
    private float density;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight; // 状态栏高度

    /**
     * 初始化屏幕尺寸
     */
    private void initMetrics(Context context) {
        mTouchTolerance = MetricsUtil.dipToPx(context, TOUCH_TOLERANCE);


        DisplayMetrics metrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;

        // TODO 改为试一试
//        mScreenWidth = metrics.widthPixels;
//        mScreenHeight = metrics.heightPixels;

        Point point = new Point();
        wManager.getDefaultDisplay().getSize(point);
        mScreenWidth = point.x;
        mScreenHeight = point.y;

        mStatusBarHeight = MetricsUtil.getStatusBarHeight(context);
    }

    private boolean movingtoEdge;

    /**
     * TODO 未整理的
     * 靠边动画，触发一：手势滑动、触发二：屏幕旋转
     */
    public void moveToEdge() {
//        // ??? 无用
//        int rotation = wManager.getDefaultDisplay().getRotation();
//        portrait = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180;

//        WindowManager.LayoutParams wlp = arcFloat.getWindowLayoutParams();

        boolean moveLeft = wlp.x <= mScreenWidth / 2;

        movingtoEdge = true;



        arcManager.moveEdge(movingtoEdge);
    }

    public void movingToEdge(boolean moveLeft) {
//        boolean moveLeft = (boolean) msg.obj;
//        WindowManager.LayoutParams wlp = arcFloat.getWindowLayoutParams();
        wlp.x = moveLeft ? (int) (wlp.x - density * 10)
                : (int) (wlp.x + density * 10);
        if (wlp.x < 0 || wlp.x > mScreenWidth) {
            movingtoEdge = false;
            wlp.x = wlp.x < 0 ? 0 : mScreenWidth;
        }

//        arcFloat.setWindowLayoutParams(wlp.x, wlp.y);
        arcManager.movingEdge(moveLeft, movingtoEdge);
    }


    private AnimatorSet animSet;

    /**
     * TODO 未整理的
     * CSP: 靠边N秒缩小
     */
    public void reduceArcFloat() {
        float moveX = 0;
        int position = getArcPosition();
        if (position == ArcMenu.MenuPosition.RIGHT_TOP ||
                position == ArcMenu.MenuPosition.RIGHT_BOTTOM ||
                position == ArcMenu.MenuPosition.RIGHT_CENTER) {
            moveX = 42f;
        } else {
            moveX = -42f;
        }
        Animator translationX = ObjectAnimator.ofFloat(this, "translationX", this.getTranslationX(), moveX);

        Animator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.5f);
        Animator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.8f);
        Animator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.8f);

        animSet = new AnimatorSet();
        animSet.playTogether(alpha, scaleX, scaleY, translationX);
        animSet.setDuration(200);
        animSet.start();
    }


    /**
     * TODO 未整理的
     * CSP: 貌似是自动折叠后的现场还原？
     */
    public void downAnim() {
//        LogCat.e("downAnim");

        this.setScaleX(1.0f);
        this.setScaleY(1.0f);
        this.setAlpha(1.0f);
        this.setTranslationX(0);
    }


    private static float MAX_LENGTH = 146;
    private static float MIN_LENGTH = 45;

    /**
     * TODO 未整理的
     * CSP: 获取 Arc 的位置
     */
    private int getArcPosition() {
        int wmX = wlp.x;
        int wmY = wlp.y;
        int position = ArcMenu.MenuPosition.RIGHT_CENTER;
//        Point point = new Point();
//        mWindowManager.getDefaultDisplay().getSize(point);
//        mScreenWidth = point.x;
//        mScreenHeight = point.y;
        final int appletHeight = mScreenHeight - mStatusBarHeight;

        int toppx = (int) MetricsUtil.dipToPx(getContext(), (MAX_LENGTH - MIN_LENGTH) / 2);
        int bottompx = (int) MetricsUtil.dipToPx(getContext(), (MAX_LENGTH + MIN_LENGTH) / 2);


//        mScreenHeight = mScreenHeight - mStatusBarHeight;
        if (wmX <= appletHeight / 3) {//左边  竖区域
            if (wmY <= toppx) {
                position = ArcMenu.MenuPosition.LEFT_TOP;//左上
            } else if (wmY > appletHeight - bottompx) {
                position = ArcMenu.MenuPosition.LEFT_BOTTOM;//左下
            } else {
                position = ArcMenu.MenuPosition.LEFT_CENTER;//左中
            }
        } else if (wmX >= appletHeight * 2 / 3) {//右边竖区域
            if (wmY <= toppx) {
                position = ArcMenu.MenuPosition.RIGHT_TOP;//右上
            } else if (wmY > appletHeight - bottompx) {
                position = ArcMenu.MenuPosition.RIGHT_BOTTOM;//右下
            } else {
                position = ArcMenu.MenuPosition.RIGHT_CENTER;//右中
            }
        }
        return position;
    }

}
