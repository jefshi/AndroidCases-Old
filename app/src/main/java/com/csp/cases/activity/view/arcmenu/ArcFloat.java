package com.csp.cases.activity.view.arcmenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.csp.cases.R;
import com.csp.cases.activity.view.other.RECState;
import com.csp.utils.android.DisplayMetricsUtil;

/**
 * Created by chenshp on 2018/3/30.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcFloat extends FrameLayout implements View.OnClickListener {
    private ImageView imgRecordFloat;
    private Chronometer chronometer;
    private ArcMenu arcMenu;
    private ArcExitView arcExitView;
    private RelativeLayout arcTipView;

    private WindowManager wManager;
    private WindowManager.LayoutParams wlp;
    private Handler mainHandler;

    // 滑动
    private float lastX;
    private float lastY;
    private static final float TOUCH_TOLERANCE = 6;

    public ArcFloat(@NonNull Context context) {
        this(context, null, 0, 0);
    }


    public ArcFloat(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ArcFloat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcFloat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    public ArcFloat(@NonNull Context context, ArcMenu arcMenu, ArcExitView arcExitView, RelativeLayout arcTipView) {
        super(context, null, 0, 0);

        this.arcMenu = arcMenu;
        this.arcExitView = arcExitView;
        this.arcTipView = arcTipView;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_arc_float, this, true);
//        ButterKnife.bind(view);

        imgRecordFloat = (ImageView) findViewById(R.id.img_record_float);
        chronometer = (Chronometer) findViewById(R.id.chronometer_float);
        arcMenu = arcMenu == null ? new ArcMenu(context) : arcMenu;
        arcExitView = arcExitView == null ? new ArcExitView(context) : arcExitView;
//        arcTipView = arcTipView == null ? new ArcTipView(context) : arcTipView;


        refreshChronometer(false);
        arcMenu.switchShow(false);

        setOnClickListener(this);

//        imgRecordFloat.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arcMenu.switchShow();
//            }
//        });

        initWindowManager(context);
        initMetrics();

//


        portrait = true;


        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                synchronized (ArcFloat.this) {
                    switch (msg.what) {
                        case MOVE_TO_EDGE:
                            boolean moveLeft = (boolean) msg.obj;
                            wlp.x = moveLeft ? (int) (wlp.x - density * 10)
                                    : (int) (wlp.x + density * 10);
                            if (wlp.x < 0 || wlp.x > mScreenWidth) {
                                movingtoEdge = false;
                                wlp.x = wlp.x < 0 ? 0 : mScreenWidth;
                            }


//                            if (desX == 0) {
//                                // 往左边靠
//                                wlp.x = (int) (wlp.x - density * 10);
//                                if (wlp.x < 0) {
//                                    wlp.x = desX;
//                                }
//                            } else {
//                                // 往右边靠
//                                wlp.x = (int) (wlp.x + density * 10);
//                                if (wlp.x > desX) {
//                                    wlp.x = desX;
//                                }
//                            }
                            updateViewLayout();
                            if (movingtoEdge)
                                mainHandler.sendMessageDelayed(
                                        mainHandler.obtainMessage(MOVE_TO_EDGE, moveLeft),
                                        10);


//                            if (wlp.x != desX) {
//                                mainHandler.sendMessageDelayed(mainHandler.obtainMessage(MOVE_TO_EDGE, desX), 10);
//                            } else {
//                                isMovingToEdge = false;
//                                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
//                                    SharedPreferencesUtils.put(context, MenuPosition.FLOAT_VIEW_PORT_X, layoutParams.x);
//                                    SharedPreferencesUtils.put(context, MenuPosition.FLOAT_VIEW_PORT_Y, layoutParams.y);
//                                } else {
//                                    SharedPreferencesUtils.put(context, MenuPosition.FLOAT_VIEW_LAND_X, layoutParams.x);
//                                    SharedPreferencesUtils.put(context, MenuPosition.FLOAT_VIEW_LAND_Y, layoutParams.y);
//                                }
//                            }
                            break;
                        case HIDE_TO_EDGE:
                            reduceArcFloat();
                            break;
                        case HIDE_TO_ARC_MENU:



                            arcMenu.switchShow(false);
                            downAnim();
                            mainHandler.sendEmptyMessageDelayed(HIDE_TO_EDGE, 2000);
//                            if (!isShowIcon) {
//                                showFloatImageView();
//                            }
                            break;
                    }
                }
            }
        };
    }

    private float density;

    /**
     * 初始化屏幕尺寸
     */
    private void initMetrics() {
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

        mStatusBarHeight = DisplayMetricsUtil.getStatusBarHeight(getContext());
    }

    /**
     * initialize WindowManager and WindowManager.LayoutParams
     */
    private void initWindowManager(Context context) {
        wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

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

    /**
     * 显示并附着在悬浮窗上
     *
     * @param attach true: 显示并附着在悬浮窗上
     */
    public void attach(boolean attach) {
        if (attach) {
//            wManager.addView(arcTipView, new WindowManager.LayoutParams());
            wManager.addView(arcExitView, arcExitView.getWindowLayoutParams());
            wManager.addView(arcMenu, arcMenu.getWindowLayoutParams());
            wManager.addView(this, wlp);

            // TODO
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    wManager.updateViewLayout(ArcFloat.this, wlp);
                }
            }, 10);

        } else {
            wManager.removeView(arcExitView);
            wManager.removeView(arcMenu);
            wManager.removeView(this);
            arcExitView = null;
            arcMenu = null;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO 调整数据,使它对准
        arcMenu.switchShow(wlp.x + wlp.width / 2, wlp.y + wlp.height / 2);
    }

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
                if (Math.abs(deltaX) > TOUCH_TOLERANCE || Math.abs(deltaY) > TOUCH_TOLERANCE) {
                    wlp.x += deltaX;
                    wlp.y += deltaY;
                    lastX = x;
                    lastY = y;
                    wManager.updateViewLayout(this, wlp);
                    arcMenu.switchShow(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                arcExitView.hide();


                moveToEdge();
                break;
        }
        return true;
    }


    boolean portrait; // 默认竖屏
    private int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight; // 状态栏高度

//    private Point moveToEdgePoint; // 靠边坐标

    private static final int MOVE_TO_EDGE = 10010; // 移动靠边
    private static final int HIDE_TO_EDGE = 10011; // 靠边N秒缩小
    private static final int HIDE_TO_ARC_MENU = 10012; // 自动折叠

    boolean movingtoEdge;

    /**
     * TODO 未整理的
     * 靠边动画，触发一：手势滑动、触发二：屏幕旋转
     */
    private void moveToEdge() {
        // ??? 无用
        int rotation = wManager.getDefaultDisplay().getRotation();
        portrait = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180;

//        if (moveToEdgePoint == null)
//            moveToEdgePoint = new Point();
//
//        moveToEdgePoint.x = wlp.x;
//        moveToEdgePoint.y = wlp.y;

//        int x = moveToEdgePoint.x;
//        int y = moveToEdgePoint.y;


        // 终点坐标，确定左边还是右边
//        int desX = 0;
//        if (wlp.x > mScreenWidth / 2) {
//            desX = mScreenWidth; // 往右边靠
//        } else {
//            desX = 0;
//        }
        boolean moveLeft = wlp.x <= mScreenWidth / 2;


        movingtoEdge = true;
        mainHandler.sendMessage(mainHandler.obtainMessage(MOVE_TO_EDGE, moveLeft));
//
//        mainHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                isMovingToEdge = true;
//
//                int x = 0, y = 0;
//
//                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
//                    x = SharedPreferencesUtils.getInt(context, MenuPosition.FLOAT_VIEW_PORT_X, layoutParams.x);
//                    y = SharedPreferencesUtils.getInt(context, MenuPosition.FLOAT_VIEW_PORT_Y, layoutParams.y);
//                } else {
//                    x = SharedPreferencesUtils.getInt(context, MenuPosition.FLOAT_VIEW_LAND_X, layoutParams.x);
//                    y = SharedPreferencesUtils.getInt(context, MenuPosition.FLOAT_VIEW_LAND_Y, layoutParams.y);
//                }
//                layoutParams.x = x;
//                layoutParams.y = y;
//                int desX = 0;
//                if (layoutParams.x > mScreenWidth / 2) {
//                    desX = mScreenWidth;
//                } else {
//                    desX = 0;
//                }
//                mainHandler.sendMessage(mainHandler.obtainMessage(MOVE_TO_EDGE, desX));
//            }
//        });
    }

    /**
     * TODO 未整理的
     * CSP: 更新位置
     */
    private void updateViewLayout() {
//        LogCat.d("updateViewPosition");

//        wlp.x = (int) (x);
//        wlp.y = (int) (y);
//
//        if (wlp.x < 0) {
//            wlp.x = 0;
//        }
//        if (wlp.y < 0) {
//            wlp.y = 0;
//        }

//        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
//        try {
        wlp.x = wlp.x < 0 ? 0 : wlp.x;
        wlp.y = wlp.y < 0 ? 0 : wlp.y;
        wManager.updateViewLayout(this, wlp);
//        } catch (Throwable e) {
//        }
    }

    private AnimatorSet animSet;

    /**
     * TODO 未整理的
     * CSP: 靠边N秒缩小
     */
    private void reduceArcFloat() {
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
    private void downAnim() {
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

        int toppx = DisplayMetricsUtil.dipToPx(getContext(), (MAX_LENGTH - MIN_LENGTH) / 2);
        int bottompx = DisplayMetricsUtil.dipToPx(getContext(), (MAX_LENGTH + MIN_LENGTH) / 2);


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
