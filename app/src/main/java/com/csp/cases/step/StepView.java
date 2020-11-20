package com.csp.cases.step;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @BindView(R.id.cbx_step_01)
    ImageView mCbxStep01;
    @BindView(R.id.txt_step_01)
    android.widget.TextView mTxtStep01;
    @BindView(R.id.img_line_02)
    ImageView mImgLine02;
    @BindView(R.id.cbx_step_02)
    ImageView mCbxStep02;
    @BindView(R.id.txt_step_02)
    android.widget.TextView mTxtStep02;
    @BindView(R.id.img_line_03)
    ImageView mImgLine03;
    @BindView(R.id.cbx_step_03)
    ImageView mCbxStep03;
    @BindView(R.id.txt_step_03)
    android.widget.TextView mTxtStep03;
    @BindView(R.id.img_line_04)
    ImageView mImgLine04;
    @BindView(R.id.cbx_step_04)
    ImageView mCbxStep04;
    @BindView(R.id.txt_step_04)
    android.widget.TextView mTxtStep04;
    @BindView(R.id.img_line_05)
    ImageView mImgLine05;
    @BindView(R.id.cbx_step_05)
    ImageView mCbxStep05;
    @BindView(R.id.txt_step_05)
    android.widget.TextView mTxtStep05;

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
        android.view.View view = LayoutInflater.from(context)
                .inflate(R.layout.view_step, this, true);
        ButterKnife.bind(view);

        if (view.getLayoutParams() == null)
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (array == null)
            return;

        int stepIndex = array.getInt(R.styleable.StepView_step_index, 1);
        int stepCount = array.getInt(R.styleable.StepView_step_count, 5);
        if (stepCount < 3)
            stepCount = 2;

        String[] titles = new String[]{
                array.getString(R.styleable.StepView_title1),
                array.getString(R.styleable.StepView_title2),
                array.getString(R.styleable.StepView_title3),
                array.getString(R.styleable.StepView_title4),
                array.getString(R.styleable.StepView_title5)
        };

        ImageView[] cbxs = new ImageView[5];
        cbxs[0] = mCbxStep01;
        cbxs[1] = mCbxStep02;
        cbxs[2] = mCbxStep03;
        cbxs[3] = mCbxStep04;
        cbxs[4] = mCbxStep05;

        ImageView[] lines = new ImageView[5];
        lines[0] = null; // mImgLine01;
        lines[1] = mImgLine02;
        lines[2] = mImgLine03;
        lines[3] = mImgLine04;
        lines[4] = mImgLine05;

        android.widget.TextView[] txtTitles = new android.widget.TextView[5];
        txtTitles[0] = mTxtStep01;
        txtTitles[1] = mTxtStep02;
        txtTitles[2] = mTxtStep03;
        txtTitles[3] = mTxtStep04;
        txtTitles[4] = mTxtStep05;

        int titleIndex = 0;
        for (int i = 0; i < cbxs.length; i++) {
            boolean chosen = i + 1 <= stepIndex;
            boolean showed = i == 4 || i + 1 < stepCount;
            int show = showed ? android.view.View.VISIBLE : android.view.View.GONE;

            txtTitles[i].setText(showed ? titles[titleIndex++] : null);
            txtTitles[i].setVisibility(show);

            cbxs[i].setSelected(chosen);
            cbxs[i].setVisibility(show);

            if (i != 0) {
                lines[i].setSelected(chosen);
                lines[i].setVisibility(show);
            }
        }
    }

    /**
     * 变更步骤索引
     *
     * @param stepIndex 步骤索引，从 1 开始
     */
    public void refreshStepIndex(int stepIndex) {
        stepIndex = stepIndex < 2 ? 1 : stepIndex;

        ImageView[] cbxs = new ImageView[5];
        cbxs[0] = mCbxStep01;
        cbxs[1] = mCbxStep02;
        cbxs[2] = mCbxStep03;
        cbxs[3] = mCbxStep04;
        cbxs[4] = mCbxStep05;

        ImageView[] lines = new ImageView[5];
        lines[0] = null; // mImgLine01;
        lines[1] = mImgLine02;
        lines[2] = mImgLine03;
        lines[3] = mImgLine04;
        lines[4] = mImgLine05;

        for (int i = 0; i < cbxs.length; i++) {
            boolean chosen = i + 1 <= stepIndex;
            cbxs[i].setSelected(chosen);
            if (i != 0)
                lines[i].setSelected(chosen);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        LogCat.e("onLayout：step", changed + ", " + Arrays.toString(new int[]{l, t, r, b}));
        LogCat.e("onLayout：step padding", Arrays.toString(new int[]{paddingStart, paddingEnd, paddingTop, paddingBottom}));

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

        LogCat.e("onLayout：child measure", Arrays.toString(new int[]{childWidth, childHeight}));
        LogCat.e("onLayout：child layout", Arrays.toString(new int[]{left, top, right, bottom}));

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

        LogCat.e("onMeasure：step mode", Arrays.toString(new int[]{widthMode, sizeWidth, heightMode, sizeHeight}));
        LogCat.e("onMeasure：step padding", Arrays.toString(new int[]{paddingStart, paddingEnd, paddingTop, paddingBottom}));

        final int count = getChildCount();
        if (count == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        android.view.View child = getChildAt(0);
        LogCat.e("onMeasure child before", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));

        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        LogCat.e("onMeasure child after", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));

        int width = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth : sizeWidth;
        int height = (heightMode == MeasureSpec.EXACTLY)
                ? sizeHeight : paddingTop + paddingBottom + child.getMeasuredHeight();

        setMeasuredDimension(width, height);
        LogCat.e("onMeasure child before", Arrays.toString(new int[]{child.getMeasuredWidth(), child.getMeasuredHeight()}));
    }
}
