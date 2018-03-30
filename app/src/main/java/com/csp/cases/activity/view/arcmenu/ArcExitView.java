package com.csp.cases.activity.view.arcmenu;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.csp.library.android.util.display_metrics.DisplayMetricsUtil;
import com.csp.library.android.util.log.LogCat;

/**
 * Created by chenshp on 2018/3/30.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcExitView extends android.support.v7.widget.AppCompatImageView {
    private WindowManager.LayoutParams wlp;

    public WindowManager.LayoutParams getWindowLayoutParams() {
        return wlp;
    }

    public ArcExitView(Context context) {
        this(context, null, 0);
    }

    public ArcExitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcExitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
//        setVisibility(GONE);
        initWindowLayoutParams();
    }

    /**
     * initialize WindowManager.LayoutParams
     */
    private void initWindowLayoutParams() {
        wlp = new WindowManager.LayoutParams();

        wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wlp.gravity = Gravity.START | Gravity.TOP;
        wlp.x = 0;
        wlp.y = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogCat.e(MeasureSpec.EXACTLY,
                MeasureSpec.getMode(widthMeasureSpec),
                MeasureSpec.getMode(heightMeasureSpec),
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));

        int size = DisplayMetricsUtil.dipToPx(getContext(), 80);
        setMeasuredDimension(size, size);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        setMeasuredDimension();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        wlp.width = getWidth();
        wlp.height = getHeight();
    }
}
