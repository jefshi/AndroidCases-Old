package com.csp.cases.activity.view.arcmenu;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.library.android.util.log.LogCat;

/**
 * Created by chenshp on 2018/3/28.
 */

public class ArcLayout extends ViewGroup {
    // 固有 View
    private View[] mInherentViews;

    // 子项数据
    private ArcAdapter mAdapter;
    private int mItemCount;
    protected int mChildSize; // 子菜单项大小（为保证大小相同，取最大值）
    private OnItemClickListener mOnItemClickListener;

    // 自定义属性
    private int mCenterRadius; // 中心圆大小
    private int centerResId; // 中心圆图片ID
    protected int mRadius; // 子项距离中心半径(px)
    //    private int padding; // TODO 子项距离中心半径

    // 角度
    private float mFromDegrees; // 子项起点角度
    private float mToDegrees; // 子项终点角度
    private float mPerDegrees; // 相邻子项间的角度差

    // View 相对中心坐标
    protected int mCenterX; // 中心圆相对坐标
    protected int mCenterY; // 中心圆相对坐标

    /**
     * @see AdapterView.OnItemClickListener
     */
    public interface OnItemClickListener {
        /**
         * @see AdapterView.OnItemClickListener#onItemClick(AdapterView, View, int, long)
         */
        void onItemClick(ArcLayout parent, View view, int position, long id);
    }

    public void setAdapter(ArcAdapter mAdapter) {
        LogCat.e("setAdapter");

        this.mAdapter = mAdapter;

        removeAllViews();
        for (View view : mInherentViews) {
            addView(view);
        }
        addItemViews();

        adjustPerDegrees();
        adjustCenterCoord();

        requestLayout();
    }

    public ArcAdapter getAdapter() {
        return mAdapter;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public int getItemCount() {
        return mItemCount;
    }

    public int getRadius() {
        return mRadius;
    }

    public ArcLayout setDegrees(float fromDegrees, float toDegrees) {
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;

        adjustDegrees();
        return this;
    }

    public float getFromDegrees() {
        return mFromDegrees;
    }

    public float getToDegrees() {
        return mToDegrees;
    }

    public ArcLayout(Context context) {
        this(context, null, 0, 0);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArcLayout, 0, 0);
            mRadius = (int) a.getDimension(R.styleable.ArcLayout_radius, 0);
            mFromDegrees = mRadius <= 0 ? 0 : a.getFloat(R.styleable.ArcLayout_fromDegrees, 0.0f);
            mToDegrees = mRadius <= 0 ? 0 : a.getFloat(R.styleable.ArcLayout_toDegrees, 360.0f);

            mCenterRadius = (int) a.getDimension(R.styleable.ArcLayout_centerRadius, 0);
            centerResId = a.getResourceId(R.styleable.ArcLayout_src, 0);
            a.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        addInherentViews(context);
        mItemCount = 0;
        if (mAdapter != null) {
            mItemCount = mAdapter.getCount();
            addItemViews();
        }

        adjustDegrees();
        adjustCenterCoord();
    }

    /**
     * 添加固有 View
     */
    private void addInherentViews(Context context) {
        int width, height;

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(centerResId);
        width = height = mCenterRadius * 2;
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);
        addView(imageView, -1, lp);

        View[] extra = addExtraInherentViews(context);
        mInherentViews = new View[1 + (extra == null ? 0 : extra.length)];
        mInherentViews[0] = imageView;
        if (extra != null)
            System.arraycopy(extra, 0, mInherentViews, 1, extra.length);

        setInherentViews(context, mInherentViews);
    }

    /**
     * 添加额外 View
     */
    protected View[] addExtraInherentViews(Context context) {
        return null;
    }

    /**
     * 设置固有 View 事件
     */
    protected void setInherentViews(Context context, View[] inherentViews) {
    }

    /**
     * 获取固有 View
     */
    protected View[] getInherentViews() {
        return mInherentViews;
    }

    /**
     * 固有 View 数量
     */
    protected int getInherentViewsNum() {
        return mInherentViews.length;
    }

    /**
     * 添加子项 View
     */
    private void addItemViews() {
        mItemCount = mAdapter == null ? 0 : mAdapter.getCount();
        for (int i = 0; i < mItemCount; i++) {
            View view = mAdapter.getView(i, null, this);
            ViewGroup.LayoutParams vlp = view.getLayoutParams();
            if (vlp != null) {
                int max = Math.max(vlp.width, vlp.height);
                mChildSize = mChildSize < max ? max : mChildSize;
            }
            addView(view);
        }
    }

    /**
     * 调整角度
     */
    private void adjustDegrees() {
        // 格式化角度
        while (mFromDegrees < 0) {
            mFromDegrees += 360;
            mToDegrees += 360;
        }

        while (mFromDegrees > 360) {
            mFromDegrees -= 360;
            mToDegrees -= 360;
        }

        while (Math.abs(mToDegrees - mFromDegrees) > 360) {
            if (mToDegrees < mFromDegrees)
                mToDegrees += 360;
            else
                mToDegrees -= 360;
        }

        adjustPerDegrees();
    }

    /**
     * 调整相邻子项间的角度差
     */
    private void adjustPerDegrees() {
        final float degrees = mToDegrees - mFromDegrees;
        mPerDegrees = Math.abs(degrees - 360) < 0.001
                ? degrees / mItemCount : degrees / (mItemCount - 1);
    }

    /**
     * 校准中心坐标
     */
    protected void adjustCenterCoord() {
        final int childCount = getChildCount();
        if (childCount == 0)
            mCenterX = mCenterY = 0;
        else if (childCount == getInherentViewsNum())
            mCenterX = mCenterY = mCenterRadius;
        else
            mCenterX = mCenterY = mRadius + mChildSize / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size;
        final int childrenCount = getChildCount();
        if (childrenCount == 0)
            size = 0;
        else if (childrenCount == getInherentViewsNum())
            size = mCenterRadius * 2;
        else
            size = mChildSize + mRadius * 2;

        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        onLayoutInherentViews(changed, l, t, r, b);
        onLayoutItemViews();
    }

    /**
     * 固有 View layout
     */
    protected void onLayoutInherentViews(boolean changed, int l, int t, int r, int b) {
        getChildAt(0).layout(mCenterX - mCenterRadius,
                mCenterY - mCenterRadius,
                mCenterX + mCenterRadius,
                mCenterY + mCenterRadius);
    }

    /**
     * 子项 View layout
     */
    private void onLayoutItemViews() {
        float fromDegrees = mFromDegrees;
        for (int i = getInherentViewsNum(); i < getChildCount(); i++) {
            Rect frame = computeChildFrame(fromDegrees);
            getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
            fromDegrees += mPerDegrees;
        }
    }

    /**
     * 计算子菜单项的范围
     */
    private Rect computeChildFrame(final float degrees) {
        final double childCenterX = mCenterX + mRadius * Math.cos(Math.toRadians(degrees));
        final double childCenterY = mCenterY + mRadius * Math.sin(Math.toRadians(degrees));

        return new Rect((int) (childCenterX - mChildSize / 2),
                (int) (childCenterY - mChildSize / 2),
                (int) (childCenterX + mChildSize / 2),
                (int) (childCenterY + mChildSize / 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                float deltaX = Math.abs(mCenterX - x);
                float deltaY = Math.abs(mCenterY - y);
                int radius = mCenterRadius + mChildSize / 2;
                if (radius * radius > deltaX * deltaX + deltaY * deltaY) {

                    // TODO 点击 Item 折叠
//                    int index = 0;
//                    if (mOnItemClickListener != null) {
//                        mOnItemClickListener.onItemClick(this
//                                , getChildAt(getInherentViewsNum() + index)
//                                , index
//                                , -1);
//                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
