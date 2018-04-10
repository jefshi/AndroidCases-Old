package com.csp.cases.activity.view.arcmenu;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.csp.utils.android.DisplayMetricsUtil;
import com.csp.utils.android.log.LogCat;

/**
 * Created by chenshp on 2018/3/30.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ArcExitView02 extends ViewGroup {
    private WindowManager.LayoutParams wlp;

    public WindowManager.LayoutParams getWindowLayoutParams() {
        return wlp;
    }

    public ArcExitView02(Context context) {
        this(context, null, 0);
    }

    public ArcExitView02(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcExitView02(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.view_arc_exit02, this, true);

//        setVisibility(GONE);
//        initWindowLayoutParams();
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        wlp.width = getWidth();
        wlp.height = getHeight();
    }
}
