package com.csp.view.loadingtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.csp.utils.android.classutil.BitmapUtil;
import com.csp.utils.android.classutil.GravityUtl;
import com.csp.view.R;

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
public class LoadingTextsView extends VerticalTextsView {
    protected int mRaw;
    protected int mOneLineDuration;
    private boolean mNeedGradient;

    private Scroller mScroller;
    private int mHadScrollCount = 0;
    private boolean mScrolling;
    // private int mScrollX;
    private int mScrollY;

    private boolean mAutoRaw;

    private int mColorBackground;
    private RectF mShelterRectF = new RectF();
    private Paint mShelterPaint = new Paint();

    private int mStartAlpha;
    private int mEndAlpha;
    private float mGradientShowY;
    private float mGradientTotal;

    public int getRaw() {
        return mRaw;
    }

    public int getOneLineDuration() {
        return mOneLineDuration;
    }

    public boolean isNeedGradient() {
        return mNeedGradient;
    }

    @Override
    public void setContents(List<String> contents) {
        mHadScrollCount = 0;
        if (contents == null)
            mScrollY = 0;
        else
            mScrollY = (contents.size() - mHadScrollCount) * mLineHeight;

        super.setContents(contents);
    }

    @Override
    public void addContent(String content) {
        if (mContents.isEmpty()) {
            mHadScrollCount = 0;
            mScrollY = mLineHeight;
        } else {
            mScrollY = (mContents.size() + 1 - mHadScrollCount) * mLineHeight;
        }

        super.addContent(content);
    }

    @Override
    public void refreshContent(boolean invalidate) {
        if (mAutoRaw)
            setRaw(mContents.size(), false);

        super.refreshContent(invalidate);
    }

    public LoadingTextsView(Context context) {
        this(context, null, 0);
    }

    public LoadingTextsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(
                attrs, R.styleable.LoadingTextsView, defStyleAttr, 0);

        init(context, array);

        array.recycle();
    }

    private void init(Context context, TypedArray array) {
        mScroller = new Scroller(context, new LinearInterpolator());

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
        mColorBackground = typedValue.data;

        int raw = -1;
        int oneLineDuration = 0;

        mAutoRaw = true;
        boolean needGradient = false;
        float startAlpha = 1.0f;
        float endAlpha = 0.0f;

        if (array != null) {
            oneLineDuration = array.getInt(R.styleable.LoadingTextsView_oneLineDuration, oneLineDuration);
            raw = array.getInt(R.styleable.LoadingTextsView_raw, raw);
            needGradient = array.getBoolean(R.styleable.LoadingTextsView_needGradient, false);
            startAlpha = array.getFraction(R.styleable.LoadingTextsView_startAlpha, 1, 1, startAlpha);
            endAlpha = array.getFraction(R.styleable.LoadingTextsView_endAlpha, 1, 1, endAlpha);

            if (raw >= 0)
                mAutoRaw = false;
        }

        setRaw(raw, false);
        setNeedGradient(needGradient);
        setGradientAlpha((int) (startAlpha * 255 + 0.5), (int) (endAlpha * 255 + 0.5));
        setOneLineDuration(oneLineDuration);
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
            int heightSize = (int) (mLineHeight * mRaw - mLineSpace + mFontMetrics.bottom + 0.5);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        }

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = mContents.size();

        float showY = getStartBaseLineY();
        if (size >= mRaw)
            showY += (size - mRaw) * mLineHeight;

        mShelterRectF.set(0, 0, getMeasuredWidth(), showY + mFontMetrics.top);
        mShelterPaint.setAntiAlias(true);
        Drawable background = getBackground();
        if (background != null) {
            canvas.drawBitmap(BitmapUtil.toBitmap(background), null, mShelterRectF, mShelterPaint);
        } else {
            mShelterPaint.setColor(mColorBackground);
            canvas.drawRect(mShelterRectF, mShelterPaint);
        }
    }

    @Override
    protected int getExtraBaseLineY() {
        return mScrollY;
    }

    @Override
    protected void extraOperateBefore() {
        super.extraOperateBefore();

        mGradientShowY = getGradientShowY();
        mGradientTotal = mRaw * mLineHeight - mLineSpace - mFontMetrics.top;
    }

    @Override
    protected boolean extraOperateForChild(float baseLineY) {
        if (!mNeedGradient)
            return super.extraOperateForChild(baseLineY);

        if (baseLineY < (mGradientShowY - mGradientTotal))
            return false;

        if (baseLineY > mGradientShowY) {
            mTextPaint.setAlpha(mStartAlpha);
            return true;
        }

        int alpha = (int) (Math.abs(mStartAlpha - mEndAlpha)
                * (mGradientTotal + baseLineY - mGradientShowY) / mGradientTotal
                + mEndAlpha);
        mTextPaint.setAlpha(alpha);
        return true;
    }

    // TODO 尚未完成支持 Gravity.TOP、Gravity.CENTER_VERTICAL
    private float getGradientShowY() {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        float gradientShowY;
        int gravity = GravityUtl.getVerticalGravity(mGravity);
        switch (gravity) {
            case Gravity.TOP:
                gradientShowY = paddingTop - mFontMetrics.top;
                break;
            case Gravity.CENTER_VERTICAL:
                gradientShowY = getMeasuredHeight() * 0.5f;
                break;
            default:
                gradientShowY = getMeasuredHeight() - paddingBottom - mFontMetrics.bottom;
        }
        return gradientShowY;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {
            // mScrollX = mScroller.getCurrX();
            mScrollY = mScroller.getCurrY();
            invalidateView();
        } else if (mScrolling) {
            mHadScrollCount = mContents.size();
            mScrolling = false;
            if (mListener != null)
                mListener.endScroll();
        }
    }

    private OnScrollListener mListener;

    public void setOnScrollListener(OnScrollListener listener) {
        mListener = listener;
    }

    public interface OnScrollListener {
        void endScroll();
    }

    public void startScroll() {
        if (mContents.size() < 1 || mScroller.computeScrollOffset())
            return;

        int shouldSize = mContents.size() - mHadScrollCount;
        int scrollY = shouldSize * mLineHeight;
        int duration = mOneLineDuration * shouldSize;

        mScrolling = true;
        mScroller.startScroll(0, scrollY, 0, -scrollY, duration);
    }

    public void resetScroll() {
        mHadScrollCount = 0;
        startScroll();
    }

    public void setRaw(int raw) {
        if (raw >= 0)
            mAutoRaw = false;

        setRaw(raw, true);
    }

    private void setRaw(int raw, boolean invalidate) {
        if (raw < 0 || raw == mRaw)
            return;

        mRaw = raw;
        if (invalidate)
            invalidateView();
    }

    private void setOneLineDuration(int oneLineDuration) {
        if (oneLineDuration == mOneLineDuration)
            return;

        mOneLineDuration = oneLineDuration < 0 ? 0 : oneLineDuration;
    }

    public void setNeedGradient(boolean needGradient) {
        if (mNeedGradient == needGradient)
            return;

        this.mNeedGradient = needGradient;
        invalidateView();
    }

    public void setGradientAlpha(int startAlpha, int endAlpha) {
        if (mStartAlpha == startAlpha && mEndAlpha == endAlpha)
            return;

        mStartAlpha = startAlpha;
        mEndAlpha = endAlpha;
        invalidateView();
    }
}
