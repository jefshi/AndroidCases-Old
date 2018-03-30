package com.csp.cases.activity.view.arcmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
import com.csp.library.android.util.log.LogCat;

/**
 * Created by chenshp on 2018/3/30.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcFloat extends FrameLayout implements View.OnClickListener {
    private WindowManager wManager;
    private WindowManager.LayoutParams wlp;


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

    public ArcFloat(@NonNull Context context, ArcMenu arcMenu, ArcExitView arcExitView) {
        super(context, null, 0, 0);

        this.arcMenu = arcMenu;
        this.arcExitView = arcExitView;
        init(context);
    }

    private ImageView imgControlHint;
    private Chronometer chronometer;

    private ArcMenu arcMenu;
    private ArcExitView arcExitView;

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_arc_float, this, true);
//        ButterKnife.bind(view);


        imgControlHint = (ImageView) findViewById(R.id.img_control_hint);
        chronometer = (Chronometer) findViewById(R.id.chronometer_float);

        arcMenu = arcMenu == null ? new ArcMenu(context) : arcMenu;
        arcMenu.switchShow(false);

        arcExitView = arcExitView == null ? new ArcExitView(context) : arcExitView;


//        imgControlHint.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arcMenu.switchShow();
//            }
//        });

        initWindowManager(context);
        refreshChronometer(false);
//
        setOnClickListener(this);


    }

    /**
     * initialize WindowManager and WindowManager.LayoutParams
     */
    private void initWindowManager(Context context) {
        wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wlp = new WindowManager.LayoutParams();

        wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wlp.gravity = Gravity.START | Gravity.TOP;
        wlp.x = 200;
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

    private void refreshChronometer(boolean start) {
        imgControlHint.setVisibility(start ? INVISIBLE : VISIBLE);
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
                break;
        }
        return true;
    }



}
