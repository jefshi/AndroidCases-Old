package com.csp.utils.android;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 动画工具类
 * Created by csp on 2018/06/27.
 * Modified by csp on 2018/06/27.
 *
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class AnimUtil {

    /**
     * 设置并执行属性动画
     *
     * @param context context
     * @param view    View
     * @param resId   xml 资源ID
     */
    private static void startAnimUseObject(Context context, View view, int resId) {
        Animator animator = AnimatorInflater.loadAnimator(context, resId);
        animator.setTarget(view);
        animator.start();
    }

    /**
     * 设置并执行补间动画
     *
     * @see #startAnimUseObject(Context, View, int)
     */
    private static void startAnimUseTween(Context context, View view, int resId) {
        Animation anim = AnimationUtils.loadAnimation(context, resId);
        view.startAnimation(anim);
    }

    /**
     * 设置并执行帧动画
     *
     * @see #startAnimUseObject(Context, View, int)
     */
    private static void startAnimUseFrame(Context context, ImageView imageView, int resId) {
        Drawable drawable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? context.getDrawable(resId) : context.getResources().getDrawable(resId);

        if (drawable == null)
            return;

        imageView.setImageDrawable(drawable);
        if (drawable instanceof AnimationDrawable)
            ((AnimationDrawable) drawable).start();
    }
}
