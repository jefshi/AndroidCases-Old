package com.csp.cases.activity.view.arcmenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.library.android.util.display_metrics.DisplayMetricsUtil;
import com.csp.library.android.util.log.LogCat;

/**
 * Created by chenshp on 2018/3/29.
 */

public class ArcMenu extends ArcLayout {
    private WindowManager mWManager;
    private WindowManager.LayoutParams mWlp;

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

        init(context);
    }

    private void init(Context context) {
        mWManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWlp = new WindowManager.LayoutParams();
        showItem = true;
        initParams(context);
    }

    // TODO ???
    private void initParams(Context context) {
        mWlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        mWlp.format = PixelFormat.TRANSLUCENT;
        mWlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        mWlp.width = DisplayMetricsUtil.dipToPx(context, 50);
        mWlp.height = DisplayMetricsUtil.dipToPx(context, 30);
        mWlp.gravity = Gravity.LEFT | Gravity.TOP;
        mWlp.x = 0;
        mWlp.y = 0;
    }

    @Override
    protected void setInherentViewsEvent(Context context, View... inherentViews) {
        View view = inherentViews[0];
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showItem = !showItem;
                requestLayout();
            }
        });
    }

    @Override
    protected View[] addExtraInherentViews(Context context) {
        FrameLayout lfr = new FrameLayout(context);
        lfr.setBackgroundResource(R.color.mask);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(lfr, -1, lp);

        View[] views = new View[1];
        views[0] = lfr;
        return views;
    }

    private boolean showItem;
    private final Drawable background = getBackground();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (showItem)
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 平移


//        TranslateAnimation translate = new TranslateAnimation(0, 200, 0, 200);
//        translate.setInterpolator(new DecelerateInterpolator());
//        translate.setStartOffset(4000);

        if (showItem) {
            setBackgroundResource(R.color.mask);
        } else {
            setBackground(background);
        }

        for (int i = getInherentViewsNum(); i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setVisibility(showItem ? View.VISIBLE : View.GONE);
        }



//        float fromX = showItem ? getRadius() : 0;
//        float endY = showItem ? 0 : getRadius();
//        for (int i = getInherentViewsNum(); i < getItemCount(); i++) {
//            View child = getChildAt(i);
//
//            ObjectAnimator translationX = ObjectAnimator
//                    .ofFloat(child, "translationX", fromX, endX);
//
//            ObjectAnimator translationY = ObjectAnimator
//                    .ofFloat(child, "translationY", fromY, endY);
//
//
//            // Set 集合
//             AnimatorSet set = new AnimatorSet();
//            set.play(translationX)
//                    .with(translationY);
//            set.setDuration(300);
//            set.addListener(newAnimatorListener());
//            set.start();
//
//
//
//
//            child.startAnimation(translate);
//        }
    }


}
