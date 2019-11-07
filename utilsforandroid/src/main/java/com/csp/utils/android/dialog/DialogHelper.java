package com.csp.utils.android.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
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

import com.csp.utils.android.R;

/**
 * 简单的对话框帮助类
 * Created by csp on 2019/3/29.
 */
public class DialogHelper {

    public static class Builder {

        private int mLayoutId;

        public Builder setLayoutId(int layoutId) {
            mLayoutId = layoutId;
            return this;
        }

        public HDialog create(Context context) {
            return new HDialog(context, this);
        }
    }

    public static class HDialog extends Dialog {

        private HDialog(@NonNull Context context, Builder builder) {
            super(context, R.style.dialog_custom);
            setContentView(LayoutInflater.from(context).inflate(builder.mLayoutId, null));
        }

        public HDialog setText(int viewId, CharSequence text) {
            ((TextView) findViewById(viewId)).setText(text);
            return this;
        }

        public HDialog setText(int viewId, int resId) {
            ((TextView) findViewById(viewId)).setText(resId);
            return this;
        }

        public HDialog setText(int viewId, int resId, Object... values) {
            String text = getContext().getString(resId);
            text = String.format(text, values);
            ((TextView) findViewById(viewId)).setText(text);
            return this;
        }

        public HDialog setImageResource(int viewId, int resId) {
            ImageView view = findViewById(viewId);
            view.setImageResource(resId);
            return this;
        }

        public HDialog setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = findViewById(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public HDialog setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = findViewById(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public HDialog setBackgroundColor(int viewId, int color) {
            View view = findViewById(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public HDialog setBackgroundRes(int viewId, int backgroundRes) {
            View view = findViewById(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public HDialog setTextColor(int viewId, @ColorRes int colorRes) {
            TextView view = findViewById(viewId);
            int color = getContext().getResources().getColor(colorRes);
            view.setTextColor(color);
            return this;
        }

        public HDialog setTextColorRes(int viewId, int textColorRes) {
            TextView view = findViewById(viewId);
            Context context = view.getContext();
            view.setTextColor(context.getResources().getColor(textColorRes));
            return this;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public HDialog setAlpha(int viewId, float value) {
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

        public HDialog setVisibility(int viewId, int visibility) {
            findViewById(viewId).setVisibility(visibility);
            return this;
        }

        public HDialog linkify(int viewId) {
            TextView view = findViewById(viewId);
            Linkify.addLinks(view, Linkify.ALL);
            return this;
        }

        public HDialog setTypeface(Typeface typeface, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = findViewById(viewId);
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }

        public HDialog setProgress(int viewId, int progress) {
            ProgressBar view = findViewById(viewId);
            view.setProgress(progress);
            return this;
        }

        public HDialog setProgress(int viewId, int progress, int max) {
            ProgressBar view = findViewById(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }

        public HDialog setMax(int viewId, int max) {
            ProgressBar view = findViewById(viewId);
            view.setMax(max);
            return this;
        }

        public HDialog setRating(int viewId, float rating) {
            RatingBar view = findViewById(viewId);
            view.setRating(rating);
            return this;
        }

        public HDialog setRating(int viewId, float rating, int max) {
            RatingBar view = findViewById(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }

        public HDialog setTag(int viewId, Object tag) {
            View view = findViewById(viewId);
            view.setTag(tag);
            return this;
        }

        public HDialog setTag(int viewId, int key, Object tag) {
            View view = findViewById(viewId);
            view.setTag(key, tag);
            return this;
        }

        public HDialog setChecked(int viewId, boolean checked) {
            Checkable view = findViewById(viewId);
            view.setChecked(checked);
            return this;
        }

        public HDialog setSelected(int viewId, boolean selected) {
            findViewById(viewId).setSelected(selected);
            return this;
        }

        public boolean isSelected(int viewId) {
            return findViewById(viewId).isSelected();
        }

        public HDialog setEnabled(int viewId, boolean enabled) {
            findViewById(viewId).setEnabled(enabled);
            return this;
        }

        /**
         * 关于事件的
         */
        public HDialog setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = findViewById(viewId);
            view.setOnClickListener(listener);
            return this;
        }

        public HDialog setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = findViewById(viewId);
            view.setOnTouchListener(listener);
            return this;
        }

        public HDialog setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
            View view = findViewById(viewId);
            view.setOnLongClickListener(listener);
            return this;
        }
    }
}
