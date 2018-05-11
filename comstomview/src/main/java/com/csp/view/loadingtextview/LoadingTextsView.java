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
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.csp.utils.android.BitmapUtil;
import com.csp.utils.android.log.LogCat;
import com.csp.view.R;

import java.util.Arrays;

/**
 * Created by chenshp on 2018/5/11.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class LoadingTextsView extends VerticalTextsView {

    private Scroller mScroller;
    private int mHadScrollCount = 0;
    private int mScrollX;
    private int mScrollY;

    private boolean mAutoRaw;
    protected int mRaw;

    private int mColorBackground;
    private RectF mShelterRectF = new RectF();
    private Paint mShelterPaint = new Paint();

    @Override
    public void refreshContent(boolean invalidate) {
        if (mAutoRaw)
            setRaw(mContents.size(), false);

        mScrollY = (mContents.size() - mHadScrollCount) * mLineHeight;

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

        if (array != null) {
            oneLineDuration = array.getInt(R.styleable.LoadingTextsView_oneLineDuration, oneLineDuration);
            raw = array.getInt(R.styleable.LoadingTextsView_raw, raw);

            if (raw >= 0)
                mAutoRaw = false;
        }

        setRaw(raw, false);
        setOneLineDuration(oneLineDuration, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogCat.e("onDraw");




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

    private boolean mScrolling;

    @Override
    public void computeScroll() {
        super.computeScroll();

        LogCat.e(mScroller.computeScrollOffset() + ", " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {
            mScrollX = mScroller.getCurrX();
            mScrollY = mScroller.getCurrY();
            invalidateView();
        } else if (mScrolling) {
            LogCat.e(mHadScrollCount);
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
        if (mHadScrollCount == 0)
            scrollY -= mLineHeight;

        mScrolling = true;
        mScroller.startScroll(0, scrollY, 0, -scrollY, duration);
        LogCat.e(Arrays.toString(new int[]{scrollY, duration}));
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
        setOneLineDuration(oneLineDuration, true);
    }

    private void setOneLineDuration(int oneLineDuration, boolean invalidate) {
        if (oneLineDuration < 0 || oneLineDuration == mOneLineDuration)
            return;

        mOneLineDuration = oneLineDuration;

        if (invalidate)
            invalidateView();
    }
}
