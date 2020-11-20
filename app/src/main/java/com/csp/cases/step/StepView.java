package com.csp.cases.step;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csp.cases.R;
import com.csp.utils.android.log.LogCat;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义 View：通用步骤 View
 * Created by csp on 2020/9/28
 * Modified by csp on 2020/9/28
 *
 * @version 1.0.0
 */
public class StepView extends ViewGroup {

    @BindView(R.id.img_line_01_left)
    View mImgLine01Left;
    @BindView(R.id.cbx_step_01)
    ImageView mCbxStep01;
    @BindView(R.id.txt_step_01)
    TextView mTxtStep01;
    @BindView(R.id.img_line_02)
    View mImgLine02;
    @BindView(R.id.img_line_02_left)
    View mImgLine02Left;
    @BindView(R.id.cbx_step_02)
    ImageView mCbxStep02;
    @BindView(R.id.txt_step_02)
    TextView mTxtStep02;
    @BindView(R.id.img_line_03)
    View mImgLine03;
    @BindView(R.id.img_line_03_left)
    View mImgLine03Left;
    @BindView(R.id.cbx_step_03)
    ImageView mCbxStep03;
    @BindView(R.id.txt_step_03)
    TextView mTxtStep03;
    @BindView(R.id.img_line_04)
    View mImgLine04;
    @BindView(R.id.img_line_04_left)
    View mImgLine04Left;
    @BindView(R.id.cbx_step_04)
    ImageView mCbxStep04;
    @BindView(R.id.txt_step_04)
    TextView mTxtStep04;
    @BindView(R.id.img_line_05)
    View mImgLine05;
    @BindView(R.id.cbx_step_05)
    ImageView mCbxStep05;
    @BindView(R.id.txt_step_05)
    TextView mTxtStep05;

    private View[] mCbxs;
    private View[] mLines;
    private View[] mLeftLines;
    private TextView[] mTxtTitles;

    private int mStepIndex;
    private int mStepCount;
    private String[] mTitles;

    public void setStepIndex(int stepIndex) {
        mStepIndex = Math.max(1, Math.min(mStepCount, stepIndex));
    }

    public void setStepCount(int stepCount) {
        mStepCount = Math.min(mTxtTitles.length, Math.max(2, stepCount)); // mTxtTitles 初始化就有了
    }

    public StepView(@NonNull Context context) {
        this(context, null, 0);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(
                attrs, R.styleable.StepView, defStyleAttr, 0);

        init(context, array);
        array.recycle();
    }

    /**
     * 绘制步骤视图
     */
    private void init(Context context, TypedArray array) {
        View viewStep = LayoutInflater.from(context)
                .inflate(R.layout.view_step, this, true);
        ButterKnife.bind(viewStep);

        if (viewStep.getLayoutParams() == null)
            viewStep.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (array == null)
            return;

        int stepIndex = array.getInt(R.styleable.StepView_step_index, 1);
        int stepCount = array.getInt(R.styleable.StepView_step_count, 5);
        String[] titles = new String[]{
                array.getString(R.styleable.StepView_title1),
                array.getString(R.styleable.StepView_title2),
                array.getString(R.styleable.StepView_title3),
                array.getString(R.styleable.StepView_title4),
                array.getString(R.styleable.StepView_title5)
        };

        mCbxs = new View[5];
        mCbxs[0] = mCbxStep01;
        mCbxs[1] = mCbxStep02;
        mCbxs[2] = mCbxStep03;
        mCbxs[3] = mCbxStep04;
        mCbxs[4] = mCbxStep05;

        mLines = new View[5];
        mLines[0] = null;
        mLines[1] = mImgLine02;
        mLines[2] = mImgLine03;
        mLines[3] = mImgLine04;
        mLines[4] = mImgLine05;

        mLeftLines = new View[5];
        mLeftLines[0] = mImgLine01Left;
        mLeftLines[1] = mImgLine02Left;
        mLeftLines[2] = mImgLine03Left;
        mLeftLines[3] = mImgLine04Left;
        mLeftLines[4] = null;

        mTxtTitles = new TextView[5];
        mTxtTitles[0] = mTxtStep01;
        mTxtTitles[1] = mTxtStep02;
        mTxtTitles[2] = mTxtStep03;
        mTxtTitles[3] = mTxtStep04;
        mTxtTitles[4] = mTxtStep05;

        mTitles = titles;
        setStepCount(stepCount);
        setStepIndex(stepIndex);
        refreshStepCount(mStepCount);
        refreshStepIndex(mStepIndex);
    }

    /**
     * @return true：指定步骤显示（不受 app:step_count 影响）
     */
    private boolean isShowed(int index) {
        return index == 4 || index + 1 < mStepCount;
    }

    /**
     * @return true：指定步骤显示（不受 app:step_count 影响）
     */
    private boolean isChosen(int index) {
        return index + 1 <= mStepIndex
                || index == mTxtTitles.length - 1 && mStepIndex >= mStepCount;
    }

    /**
     * 变更步骤数量，建议继续之后继续调用 {@link #refreshStepIndex(int)}
     *
     * @param stepCount 步骤数量，从 1 开始
     */
    public void refreshStepCount(int stepCount) {
        setStepCount(stepCount);
        setStepIndex(mStepIndex);

        // 正向遍历
        int titleIndex = 0;
        for (int i = 0; i < mTxtTitles.length; i++) {
            boolean showed = isShowed(i);
            int show = showed ? View.VISIBLE : View.GONE;

            mTxtTitles[i].setText(showed ? mTitles[titleIndex++] : null);
            mTxtTitles[i].setVisibility(show);

            mCbxs[i].setVisibility(show);

            View view = mLines[i];
            if (view != null)
                view.setVisibility(show);

            view = mLeftLines[i];
            if (view != null)
                view.setVisibility(show);
        }
    }

    /**
     * 变更步骤索引
     *
     * @param stepIndex 步骤索引，从 1 开始
     */
    public void refreshStepIndex(int stepIndex) {
        setStepIndex(stepIndex);

        // 反向遍历
        int start = mTxtTitles.length - 1;
        int lastShowIndex = start; // 上个有显示的步骤索引
        for (int i = start; i > -1; i--) {
            boolean showed = isShowed(i);
            if (!showed)
                continue;

            boolean chosen = isChosen(i);

            mCbxs[i].setSelected(chosen);

            View view = mLines[i];
            if (view != null)
                view.setSelected(chosen);

            view = mLeftLines[i];
            if (view != null)
                view.setSelected(isChosen(lastShowIndex));

            lastShowIndex = i;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        LogCat.i("onLayout：step", changed + ", " + Arrays.toString(new int[]{l, t, r, b}));
        LogCat.i("onLayout：step padding", Arrays.toString(new int[]{paddingStart, paddingEnd, paddingTop, paddingBottom}));

        final int count = getChildCount();
        if (count == 0)
            return;

        android.view.View child = getChildAt(0);
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();

        int left = paddingStart;
        int top = paddingTop;
        int right = paddingStart + childWidth;
        int bottom = paddingTop + childHeight;
        child.layout(left, top, right, bottom);

        LogCat.i("onLayout：child measure", Arrays.toString(new int[]{childWidth, childHeight}));
        LogCat.i("onLayout：child layout", Arrays.toString(new int[]{left, top, right, bottom}));

        child.requestLayout();
        // child.invalidate();
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

        LogCat.i("onMeasure：step mode", Arrays.toString(new int[]{widthMode, sizeWidth, heightMode, sizeHeight}));
        LogCat.i("onMeasure：step padding", Arrays.toString(new int[]{paddingStart, paddingEnd, paddingTop, paddingBottom}));

        final int count = getChildCount();
        if (count == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        android.view.View child = getChildAt(0);
        LogCat.i("onMeasure child before", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));

        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        LogCat.i("onMeasure child after", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));

        int width = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth : sizeWidth;
        int height = (heightMode == MeasureSpec.EXACTLY)
                ? sizeHeight : paddingTop + paddingBottom + child.getMeasuredHeight();

        setMeasuredDimension(width, height);
        LogCat.i("onMeasure child before", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));
    }
}
