package com.csp.utils.android.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 简单的 PopupWindow 帮助类
 * Created by csp on 2019/11/03
 */
public class PopupWindowHelper {

    public static class Builder {

        private int mLayoutId;
        private View mView;

        private int mWidth;
        private int mHeight;

        private Drawable mBackground;

        public PopupWindowHelper.Builder setLayoutId(int layoutId) {
            mLayoutId = layoutId;
            return this;
        }

        public Builder setView(View view) {
            mView = view;
            return this;
        }

        public Builder setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mHeight = height;
            return this;
        }

        public Builder setBackground(Drawable background) {
            mBackground = background;
            return this;
        }

        private View getView(@NonNull Context context) {
            if (mLayoutId == 0)
                return mView;

            mView = LayoutInflater.from(context).inflate(mLayoutId, null);
            return mView;
        }

        public HPopupWindow create(Context context) {
            return new HPopupWindow(context, this);
        }
    }

    public static class HPopupWindow extends PopupWindowOptimize {

        private HPopupWindow(@NonNull Context context, PopupWindowHelper.Builder builder) {
            super(builder.mWidth, builder.mHeight);
            View view = builder.getView(context);
            setContentView(view);
            init(builder);
        }

        private void init(PopupWindowHelper.Builder builder) {
            //点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
            setBackgroundDrawable(builder.mBackground == null ? new BitmapDrawable() : builder.mBackground);
            setFocusable(true);
            setOutsideTouchable(true);

            // 设置是否允许 PopupWindow 的范围超过屏幕范围
            // 不建议设置，会导致 showAtLocation 方法 Android 7.0 及以上导航栏(虚拟按键)遮挡 PopupWindow 问题
            // Android 7.0 以下导航栏(虚拟按键)遮挡 PopupWindow 问题：https://blog.csdn.net/qq_35332786/article/details/82660587
            // Android 7.0 及以上导航栏(虚拟按键)遮挡 PopupWindow 问题：https://www.jianshu.com/p/291321305dee
            // setClippingEnabled(false);
        }

        // @Nullable
        public final <T extends View> T findViewById(@IdRes int id) {
            return getContentView().findViewById(id);
        }

        public final Context getContext() {
            return getContentView().getContext();
        }

        public HPopupWindow setText(int viewId, CharSequence text) {
            ((TextView) findViewById(viewId)).setText(text);
            return this;
        }

        public HPopupWindow setText(int viewId, int resId) {
            ((TextView) findViewById(viewId)).setText(resId);
            return this;
        }

        public HPopupWindow setText(int viewId, int resId, Object... values) {
            String text = getContext().getString(resId);
            text = String.format(text, values);
            ((TextView) findViewById(viewId)).setText(text);
            return this;
        }

        public HPopupWindow setImageResource(int viewId, int resId) {
            ImageView view = findViewById(viewId);
            view.setImageResource(resId);
            return this;
        }

        public HPopupWindow setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = findViewById(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public HPopupWindow setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = findViewById(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public HPopupWindow setBackgroundColor(int viewId, int color) {
            View view = findViewById(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public HPopupWindow setBackgroundRes(int viewId, int backgroundRes) {
            View view = findViewById(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public HPopupWindow setTextColor(int viewId, @ColorRes int colorRes) {
            TextView view = findViewById(viewId);
            int color = getContext().getResources().getColor(colorRes);
            view.setTextColor(color);
            return this;
        }

        public HPopupWindow setTextColorRes(int viewId, int textColorRes) {
            TextView view = findViewById(viewId);
            Context context = view.getContext();
            view.setTextColor(context.getResources().getColor(textColorRes));
            return this;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public HPopupWindow setAlpha(int viewId, float value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                findViewById(viewId).setAlpha(value);
            } else {
                // Pre-honeycomb hack to set Alpha value
                AlphaAnimation alpha = new AlphaAnimation(value, value);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                findViewById(viewId).startAnimation(alpha);
            }
            return this;
        }

        public HPopupWindow setVisibility(int viewId, int visibility) {
            findViewById(viewId).setVisibility(visibility);
            return this;
        }

        public HPopupWindow linkify(int viewId) {
            TextView view = findViewById(viewId);
            Linkify.addLinks(view, Linkify.ALL);
            return this;
        }

        public HPopupWindow setTypeface(Typeface typeface, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = findViewById(viewId);
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }

        public HPopupWindow setProgress(int viewId, int progress) {
            ProgressBar view = findViewById(viewId);
            view.setProgress(progress);
            return this;
        }

        public HPopupWindow setProgress(int viewId, int progress, int max) {
            ProgressBar view = findViewById(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }

        public HPopupWindow setMax(int viewId, int max) {
            ProgressBar view = findViewById(viewId);
            view.setMax(max);
            return this;
        }

        public HPopupWindow setRating(int viewId, float rating) {
            RatingBar view = findViewById(viewId);
            view.setRating(rating);
            return this;
        }

        public HPopupWindow setRating(int viewId, float rating, int max) {
            RatingBar view = findViewById(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }

        public HPopupWindow setTag(int viewId, Object tag) {
            View view = findViewById(viewId);
            view.setTag(tag);
            return this;
        }

        public HPopupWindow setTag(int viewId, int key, Object tag) {
            View view = findViewById(viewId);
            view.setTag(key, tag);
            return this;
        }

        public HPopupWindow setChecked(int viewId, boolean checked) {
            Checkable view = findViewById(viewId);
            view.setChecked(checked);
            return this;
        }

        public HPopupWindow setSelected(int viewId, boolean selected) {
            findViewById(viewId).setSelected(selected);
            return this;
        }

        public boolean isSelected(int viewId) {
            return findViewById(viewId).isSelected();
        }

        public HPopupWindow setEnabled(int viewId, boolean enabled) {
            findViewById(viewId).setEnabled(enabled);
            return this;
        }

        /**
         * 关于事件的
         */
        public HPopupWindow setOnClickListener(View.OnClickListener listener) {
            getContentView().setOnClickListener(listener);
            return this;
        }

        /**
         * 关于事件的
         */
        public HPopupWindow setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = findViewById(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        public HPopupWindow setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = findViewById(viewId);
            view.setOnTouchListener(listener);
            return this;
        }

        public HPopupWindow setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
            View view = findViewById(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }
    }
}
