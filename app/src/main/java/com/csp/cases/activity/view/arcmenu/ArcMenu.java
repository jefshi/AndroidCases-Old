package com.csp.cases.activity.view.arcmenu;


import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.csp.cases.R;

/**
 * Created by chenshp on 2018/3/29.
 */

public class ArcMenu extends ArcLayout implements IWindowLayoutParams{
    private WindowManager.LayoutParams wlp;
    private boolean showMenu = true;

    @Override
    public WindowManager.LayoutParams getWindowLayoutParams() {
        return wlp;
    }

    @Override
    public void setWindowLayoutParams(int x, int y) {
        wlp.x = x;
        wlp.y = y;
    }

    public ArcMenu(Context context) {
        this(context, null, 0, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {
        showMenu = getVisibility() == VISIBLE;
        initWindowLayoutParams();
    }

    /**
     * initialize WindowManager.LayoutParams
     */
    @Override
    public void initWindowLayoutParams() {
        if (wlp != null)
            return;

        wlp = new WindowManager.LayoutParams();

        wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wlp.gravity = Gravity.START | Gravity.TOP;
        wlp.x = 0;
        wlp.y = 0;
    }

    @Override
    protected View[] addExtraInherentViews(Context context) {
        FrameLayout lfr = new FrameLayout(context);
        lfr.setBackgroundResource(R.color.mask);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(lfr, -1, lp);

        return new View[]{lfr};
    }

    @Override
    protected void setInherentViews(Context context, View... inherentViews) {
//        inherentViews[0].setVisibility(GONE);

        inherentViews[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchShow(mCenterX, mCenterY);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec)
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        wlp.width = getWidth();
        wlp.height = getHeight();
    }

    @Override
    protected void onLayoutInherentViews(boolean changed, int l, int t, int r, int b) {
        super.onLayoutInherentViews(changed, l, t, r, b);

        getChildAt(1).layout(l, t, r, b);
    }

    /**
     * 切换显示隐藏
     */
    public void switchShow(boolean show) {
        switchShow(show, mCenterX, mCenterY);
    }

    /**
     * 切换显示隐藏
     */
    public void switchShow(int centerX, int centerY) {
        switchShow(!showMenu, centerX, centerY);
    }

    /**
     * 切换显示隐藏
     */
    public void switchShow(boolean show, int centerX, int centerY) {
        showMenu = show;
        if (show && (centerX != mCenterX || centerY != mCenterY)) {
            mCenterX = centerX;
            mCenterY = centerY;
            requestLayout();
        }
        setVisibility(showMenu ? VISIBLE : GONE);
    }

    public interface MenuPosition {
        int LEFT_TOP = 1;
        int CENTER_TOP = 2;
        int RIGHT_TOP = 3;
        int LEFT_CENTER = 4;
        int CENTER = 5;
        int RIGHT_CENTER = 6;
        int LEFT_BOTTOM = 7;
        int CENTER_BOTTOM = 8;
        int RIGHT_BOTTOM = 9;
    }


    public int position;

    public void setPosition(int position) {
        this.position = position;

        switch (position) {
            case MenuPosition.LEFT_TOP:
                setDegrees(0, 90);
                break;
            case MenuPosition.RIGHT_TOP:
                setDegrees(180, 90);
                break;
            case MenuPosition.LEFT_CENTER:
                setDegrees(-90, 90);
                break;
            case MenuPosition.RIGHT_CENTER:
                setDegrees(-90, 90);
                break;
            case MenuPosition.LEFT_BOTTOM:
                setDegrees(-90, 0);
                break;
            case MenuPosition.RIGHT_BOTTOM:
                setDegrees(-90, -180);
                break;
        }
    }
}
