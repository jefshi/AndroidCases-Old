package com.csp.cases.activity.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.csp.cases.R;
import com.csp.utils.android.log.LogCat;

import java.util.Arrays;

public class LetterIndexBar extends View {

    private Paint mPaint;
    private int mChosenIndex = -1; // 选中字母

    //侧边栏字母显示
    private String[] mAlphabet = {
            "#", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    private OnIndexChangedListener mChangedListener;

    public void setChangedListener(OnIndexChangedListener changedListener) {
        mChangedListener = changedListener;
    }

    public LetterIndexBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public LetterIndexBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setTextSize(44);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        LogCat.e("onMeasure：bar mode", Arrays.toString(new int[]{widthMode, sizeWidth, heightMode, sizeHeight}));
        LogCat.e("onMeasure：bar padding", Arrays.toString(new int[]{paddingStart, paddingEnd, paddingTop, paddingBottom}));

        // 宽度不为 wrap_content
        if (widthMode == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

//        // 区域等分
//        int height = sizeHeight - paddingTop - paddingBottom;
//        int alphabetLength = mAlphabet.length;
//        float letterHeight = height * 1.0f / alphabetLength;
//
//        // 计算字体大小
//        int textSize = (int) (height * 0.7f / alphabetLength);
//        mPaint.setTextSize(textSize);
//        mPaint.setTypeface(Typeface.DEFAULT_BOLD);

        // 计算宽度
        float width = mPaint.measureText(mAlphabet[0]) + 10 + paddingStart + paddingEnd;
        setMeasuredDimension((int) width, sizeHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        float letterHeight = height * 1.0f / mAlphabet.length;

        int x = width / 2;
        for (int i = 0; i < mAlphabet.length; i++) {
            boolean chosen = mChosenIndex == i;
            int colorRes = chosen ? R.color.color_388bf5 : R.color.color_374554;
            mPaint.setFakeBoldText(chosen);
            mPaint.setColor(getResources().getColor(colorRes));

            float y = letterHeight * (i + 1);
            canvas.drawText(mAlphabet[i], x, y, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int preChosenIndex = mChosenIndex;
        int chosenIndex = (int) (y / getHeight() * mAlphabet.length);

        int action = event.getAction();
        LogCat.e("action = %s, y = %s", getMotionEventName(action), y);

        if (chosenIndex != preChosenIndex
                && chosenIndex >= 0
                && chosenIndex < mAlphabet.length) {

            LogCat.e("letter change", mAlphabet[chosenIndex]);

            mChosenIndex = chosenIndex;
            invalidate();

            if (mChangedListener != null)
                mChangedListener.onIndexChanged(mAlphabet[chosenIndex], chosenIndex);
        }
        return true;
    }

    private String getMotionEventName(int action) {
        switch (action) {
            case MotionEvent.ACTION_MASK:
                return "ACTION_MASK";
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            case MotionEvent.ACTION_OUTSIDE:
                return "ACTION_OUTSIDE";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP";
            case MotionEvent.ACTION_HOVER_MOVE:
                return "ACTION_HOVER_MOVE";
            case MotionEvent.ACTION_SCROLL:
                return "ACTION_SCROLL";
            case MotionEvent.ACTION_HOVER_ENTER:
                return "ACTION_HOVER_ENTER";
            case MotionEvent.ACTION_HOVER_EXIT:
                return "ACTION_HOVER_EXIT";
            case MotionEvent.ACTION_BUTTON_PRESS:
                return "ACTION_BUTTON_PRESS";
            case MotionEvent.ACTION_BUTTON_RELEASE:
                return "ACTION_BUTTON_RELEASE";
            case MotionEvent.ACTION_POINTER_INDEX_MASK:
                return "ACTION_POINTER_INDEX_MASK";
            default:
                return "Other: " + action;
        }
    }

    /**
     * 导航变化监听器
     */
    public interface OnIndexChangedListener {

        /**
         * @param letter 变化后对应字母
         * @param index  变化后索引
         */
        void onIndexChanged(String letter, int index);
    }
}
