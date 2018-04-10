package com.csp.cases.activity.view.arcmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.utils.android.DisplayMetricsUtil;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ArcExitView extends FrameLayout implements IWindowLayoutParams {
//    @BindView(R.id.delete_iv)
    ImageView mDeleteIv;
    private AnimatorSet mShowAnimSet;
    private AnimatorSet mHideAnimSet = new AnimatorSet();
    private int marginBottom;
    private int stateBarHeight;
    public ArcExitView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ArcExitView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcExitView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_arc_exit, this, true);
//        ButterKnife.bind(view);

        mDeleteIv = view.findViewById(R.id.delete_iv);


        marginBottom = DisplayMetricsUtil.dipToPx(context, 100);
        setMargins(mDeleteIv,0,0,0,marginBottom);
        stateBarHeight = isSamsungS8() ? DisplayMetricsUtil.getStatusBarHeight(context):0;
    }
    public void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //横竖屏切换后关闭在打开刷新
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            marginBottom = DisplayMetricsUtil.dipToPx(getContext(), 100);
            setMargins(mDeleteIv,0,0,0,marginBottom);
        }else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            marginBottom = DisplayMetricsUtil.dipToPx(getContext(), 5);
            setMargins(mDeleteIv,0,0,0,marginBottom);
        }
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    private String getSystemModel() {
        return android.os.Build.MODEL;
    }
    private boolean isSamsungS8(){
        return getSystemModel().equals("SM-G950F");
    }
    public void getCenterXY(int[] array){

        int width = getWidth();
        int height = getHeight();
        array[0] = width / 2;
        array[1] = height - (marginBottom + mDeleteIv.getHeight() / 2 + stateBarHeight);
    }

    //(x,y)是否在view的区域内
    public boolean isTouchPointInView(float x, float y) {
        if (mDeleteIv == null) {
            return false;
        }
        int[] location = new int[2];
        mDeleteIv.getLocationOnScreen(location);
        float left = location[0];
        float top = location[1];
        float right = left + mDeleteIv.getMeasuredWidth();
        float bottom = top + mDeleteIv.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }

    public void show() {
        if (mShowAnimSet == null) {
            mShowAnimSet = new AnimatorSet();
            mDeleteIv.setAlpha(0f);
            mDeleteIv.setVisibility(View.VISIBLE);
            Animator mAlphaAnim = ObjectAnimator.ofFloat(mDeleteIv, "alpha", 0f, 1f);
            Animator mTranslationYAnim = ObjectAnimator.ofFloat(mDeleteIv, "translationY", 800f, 0f);
            mShowAnimSet.setInterpolator(new AccelerateInterpolator());
            mShowAnimSet.playTogether(mTranslationYAnim, mAlphaAnim);
            mShowAnimSet.setDuration(200);
            mShowAnimSet.start();
        }
    }

    public void hide() {

        mDeleteIv.setAlpha(1f);
        Animator mAlphaAnim = ObjectAnimator.ofFloat(mDeleteIv, "alpha", 1f, 0f);
        Animator mTranslationYAnim = ObjectAnimator.ofFloat(mDeleteIv, "translationY", 0f, 800f);
        mHideAnimSet.setInterpolator(new AccelerateInterpolator());
        mHideAnimSet.playTogether(mTranslationYAnim, mAlphaAnim);
        mHideAnimSet.setDuration(250);
        mHideAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mDeleteIv.setVisibility(View.INVISIBLE);
                mShowAnimSet = null;
            }
        });
        mHideAnimSet.start();
    }

    @Override
    public void initWindowLayoutParams() {
        if (wlp != null)
            return;


        wlp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
//                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.TRANSLUCENT);
        wlp.gravity = Gravity.TOP | Gravity.START;
    }

    private WindowManager.LayoutParams wlp;

    @Override
    public WindowManager.LayoutParams getWindowLayoutParams() {
        return wlp;
    }

    @Override
    public void setWindowLayoutParams(int x, int y) {
        wlp.x = x;
        wlp.y = y;
    }
}
