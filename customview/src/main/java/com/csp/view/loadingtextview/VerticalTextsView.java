package com.csp.view.loadingtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.csp.utils.android.classutil.GravityUtl;
import com.csp.utils.android.MetricsUtil;
import com.csp.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Application
 * Create Date: 2018/05/14
 * Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCustomVied 1.0.0
 */
@SuppressWarnings({"unused", "SameParameterValue"})
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class VerticalTextsView extends View {
    List<String> mContents = new ArrayList<>();

    protected TextPaint mTextPaint;
    protected Paint.FontMetrics mFontMetrics;

    protected float mLineSpace;
    protected int mLineHeight;
    protected int mLineWidth;
    protected int mGravity;

    public List<String> getContents() {
        return mContents;
    }

    public void setContents(List<String> contents) {
        if (contents == null)
            mContents.clear();
        else
            mContents.addAll(contents);

        refreshContent(true);
    }

    public void addContent(String content) {
        mContents.add(content);

        refreshContent(true);
    }

    public void replaceContent(int index, String content) {
        mContents.remove(index);
        mContents.add(content);

        refreshContent(true);
    }

    public void refreshContent(boolean invalidate) {
        measureLineWidth();

        if (invalidate) {
            requestLayout();
            invalidateView();
        }
    }

    public VerticalTextsView(Context context) {
        this(context, null, 0);
    }

    public VerticalTextsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalTextsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(
                attrs, R.styleable.VerticalTextsView, defStyleAttr, 0);

        init(array);

        array.recycle();
    }

    private void init(TypedArray array) {
        mTextPaint = new TextPaint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);

        ColorStateList textColor = null;
        int textSize = 15;
        int gravity = Gravity.START | Gravity.BOTTOM;
        float lineSpacing = 0;

        if (array != null) {
            textColor = array.getColorStateList(R.styleable.VerticalTextsView_textColor);
            textSize = array.getDimensionPixelSize(R.styleable.VerticalTextsView_textSize, textSize);
            gravity = array.getInt(R.styleable.VerticalTextsView_gravity, gravity);
            lineSpacing = array.getDimension(R.styleable.VerticalTextsView_lineSpacing, lineSpacing);
        }

        setLineSpacing(lineSpacing, false);
        setRawTextSize(textSize, false);
        setTextColor(textColor != null ? textColor : ColorStateList.valueOf(0xFF000000));
        setGravity(gravity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            int widthSize = mLineWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            int heightSize = (int) (mLineHeight * mContents.size() - mLineSpace + mFontMetrics.bottom + 0.5);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    @SuppressLint("RtlHardcoded")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = mContents.size();
        if (size < 1)
            return;

        float baseLineX = getStartBaseLineX();
        float startBaseLineY = getStartBaseLineY();

        extraOperateBefore();

        float baseLineY;
        int showTop = -mLineHeight;
        int showBottom = getMeasuredHeight() + mLineHeight;
        for (int i = 0; i < size; i++) {
            baseLineY = startBaseLineY + i * mLineHeight + getExtraBaseLineY();
            if (baseLineY <= showTop || baseLineY >= showBottom)
                continue;

            if (extraOperateForChild(baseLineY))
                canvas.drawText(mContents.get(i), baseLineX, baseLineY, mTextPaint);
        }
    }

    protected int getExtraBaseLineY() {
        return 0;
    }

    protected void extraOperateBefore() {
    }

    protected boolean extraOperateForChild(float baseLineY) {
        return true;
    }

    @SuppressLint("RtlHardcoded")
    protected float getStartBaseLineX() {
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();

        float baseLineX;
        int gravity = GravityUtl.getHorizontalGravity(mGravity);
        switch (gravity) {
            case Gravity.RIGHT:
                baseLineX = getWidth() - paddingEnd;
                break;
            case Gravity.CENTER_HORIZONTAL:
                baseLineX = (getWidth() - paddingStart - paddingEnd) * 0.5f + paddingStart;
                break;
            default:
                baseLineX = paddingStart;
        }
        return baseLineX;
    }

    // TODO 尚未完成支持 Gravity.TOP、Gravity.CENTER_VERTICAL
    protected float getStartBaseLineY() {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int size = mContents.size();

        float baseLineY;
        int gravity = GravityUtl.getVerticalGravity(mGravity);
        switch (gravity) {
            case Gravity.TOP:
                baseLineY = paddingTop - mFontMetrics.top;
                break;
            case Gravity.CENTER_VERTICAL:
                baseLineY = getMeasuredHeight() * 0.5f;
                break;
            default:
                baseLineY = getMeasuredHeight() - paddingBottom - mFontMetrics.bottom - (size - 1) * mLineHeight;
        }
        return baseLineY;
    }

    private void measureLineHeight() {
        Rect bound = new Rect();
        mTextPaint.getTextBounds("测试", 0, "测试".length(), bound);
        mLineHeight = (int) (bound.height() + mLineSpace + 0.5f);
    }

    private void measureLineWidth() {
        Rect bound = new Rect();
        int width = 0;
        for (String content : mContents) {
            mTextPaint.getTextBounds(content, 0, content.length(), bound);
            width = Math.max(bound.width(), width);
        }
        mLineWidth = width;
    }

    public void setTextSize(int unit, float size) {
        setRawTextSize(MetricsUtil.toPx(getContext(), unit, size), true);
    }

    private void setRawTextSize(float size, boolean invalidate) {
        if (size == mTextPaint.getTextSize())
            return;

        mTextPaint.setTextSize(size);
        mFontMetrics = mTextPaint.getFontMetrics();
        measureLineHeight();
        measureLineWidth();

        if (invalidate) {
            requestLayout();
            invalidate();
        }
    }

    public void setTextColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }
        mTextPaint.setColor(colors.getColorForState(getDrawableState(), 0));
        invalidateView();
    }

    @SuppressLint("RtlHardcoded")
    public void setGravity(int gravity) {
        gravity = GravityUtl.formatGravity(gravity, false, false);
        if (mGravity == gravity)
            return;

        mGravity = gravity;

        Paint.Align align;
        switch (GravityUtl.getHorizontalGravity(mGravity)) {
            case Gravity.RIGHT:
                align = Paint.Align.RIGHT;
                break;
            case Gravity.CENTER_HORIZONTAL:
                align = Paint.Align.CENTER;
                break;
            default:
                align = Paint.Align.LEFT;
        }
        mTextPaint.setTextAlign(align);
        invalidateView();
    }

    public void setLineSpacing(int unit, float size) {
        setRawTextSize(MetricsUtil.toPx(getContext(), unit, size), true);
    }

    private void setLineSpacing(float lineSpacing, boolean invalidate) {
        if (lineSpacing == mLineSpace)
            return;

        mLineSpace = lineSpacing;
        measureLineHeight();

        if (invalidate) {
            requestLayout();
            invalidateView();
        }
    }

    protected void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper())
            invalidate();
        else
            postInvalidate();
    }
}
