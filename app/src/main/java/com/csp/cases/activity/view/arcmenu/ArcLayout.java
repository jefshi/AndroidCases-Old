package com.csp.cases.activity.view.arcmenu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.library.android.util.display_metrics.DisplayMetricsUtil;
import com.csp.library.android.util.log.LogCat;

/**
 * Created by chenshp on 2018/3/28.
 */

public class ArcLayout extends ViewGroup {
    // 子项数据
    private ArcAdapter mAdapter;
    private int mItemCount;


    // 自定义属性
    private int mCenterRadius; // 中心圆圈大小
    private int centerResId; // 中心圆圈图片ID
    private int radius; // 子项距离中心半径(px)
    //    private int padding; // 子项距离中心半径
    private float mFromDegrees; // 子项起点角度
    private float mToDegrees; // 子项终点角度

    // 自定义属性默认值
    private static final int DEFAULT_CENTER_RADIUS_DIP = 10; // 默认最小半径
    private static final int DEFAULT_MIN_RADIUS_DIP = 50; // 默认最小半径
    public static final float DEFAULT_FROM_DEGREES = 0.0f;
    public static final float DEFAULT_TO_DEGREES = 360.0f;

    public void setAdapter(ArcAdapter mAdapter) {
        this.mAdapter = mAdapter;

        // Clean
        View[] views = getInherentView();
        removeAllViews();
        for (View view : views) {
            addView(view);
        }

        mItemCount = mAdapter.getCount();
        for (int i = 0; i < mItemCount; i++) {
            View view = mAdapter.getView(i, null, this);
            addView(view);
        }

//        requestLayout();
    }


    public ArcAdapter getAdapter() {
        return mAdapter;
    }

    public ArcLayout(Context context) {
        this(context, null);
    }

    public ArcLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs);
        int minRadius = DisplayMetricsUtil.dipToPx(context, DEFAULT_MIN_RADIUS_DIP);
        int minCenterRadius = DisplayMetricsUtil.dipToPx(context, DEFAULT_CENTER_RADIUS_DIP);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ArcLayout, 0, 0);
            mFromDegrees = a.getFloat(R.styleable.ArcLayout_fromDegrees, DEFAULT_FROM_DEGREES);
            mToDegrees = a.getFloat(R.styleable.ArcLayout_toDegrees, DEFAULT_TO_DEGREES);
            radius = (int) a.getDimension(R.styleable.ArcLayout_radius, minRadius);
            mCenterRadius = (int) a.getDimension(R.styleable.ArcLayout_center_radius, minCenterRadius);
            centerResId = a.getResourceId(R.styleable.ArcLayout_src, 0);
            a.recycle();
        }

        radius = radius < minRadius ? minRadius : radius;
        mCenterRadius = mCenterRadius < minCenterRadius ? minCenterRadius : mCenterRadius;

        init(context);
    }

    private void init(Context context) {
        addView(new ImageView(context));
    }

    private View[] getInherentView() {
        return new View[]{getChildAt(0)};
    }

    private int getInherentViewNum() {
        return 1;
    }

    private void onMeasureInherentView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Measure Inherent View
        View child = getChildAt(0);
        int childMeasureSpec = MeasureSpec.makeMeasureSpec(mCenterRadius, MeasureSpec.EXACTLY);
        measureChild(child, childMeasureSpec, childMeasureSpec);

        final int childrenCount = getChildCount();
        if (childrenCount == getInherentViewNum()) {
            setMeasuredDimension(mCenterRadius, mCenterRadius);
            return;
        }

        int size = 0;
        mItemCount = mAdapter.getCount();
        for (int i = getInherentViewNum(); i < mItemCount; i++) {
            child = getChildAt(i);
            int max = Math.max(child.getMeasuredWidth(), child.getMeasuredHeight());
            size = size < max ? max : size;
        }
        LogCat.e("size: " + size);

        size += size / 2 + radius;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ((ImageView) getChildAt(0)).setImageResource(centerResId);

        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int raduis = getMeasuredWidth() / 2;



        View child = getChildAt(0);
        int centerSize = Math.max(child.getMeasuredWidth(), child.getMeasuredHeight());
        int left = (getMeasuredWidth() - centerSize) / 2;
        child.layout(left, left,mCenterRadius + left, mCenterRadius + left);

        // TODO padding

        //        final int centerX = getWidth() / 2 - mRadius;
//        final int centerY = getHeight() / 2;Lis
//        computeCenterXY(position);
        //当子菜单要收缩时radius=0，在ViewGroup坐标中心
//        final int radius = mExpanded ? mRadius : 0;

        // 添加子项视图
        final int itemCount = mItemCount;

        // 计算子项位置


//        final int childCount = getChildCount();
////        final float perDegrees =Math.abs (mToDegrees - mFromDegrees) / (childCount - 1);
//        final float perDegrees = Math.abs(mToDegrees - mFromDegrees) == 360 ? (Math.abs(mToDegrees - mFromDegrees)) / (childCount) : (Math.abs(mToDegrees - mFromDegrees)) / (childCount - 1);
//
//
//        float degrees = mFromDegrees;
//        for (int i = 0; i < childCount; i++) {
//            Rect frame = computeChildFrame(centerX, centerY, radius, degrees,
//                    mChildSize);
//            degrees += perDegrees;
//            getChildAt(i).layout(frame.left, frame.top, frame.right,
//                    frame.bottom);
//        }
    }

//    /**
//     * 计算半径
//     */
//    private static int computeRadius(final float arcDegrees,
//                                     final int childCount, final int childSize, final int childPadding,
//                                     final int minRadius) {
//        if (childCount < 2) {
//            return minRadius;
//        }
////        final float perDegrees = arcDegrees / (childCount - 1);
//
//
//        final float perDegrees = arcDegrees == 360 ? (arcDegrees) / (childCount) : (arcDegrees) / (childCount - 1);
//
//
//        final float perHalfDegrees = perDegrees / 2;
//        final int perSize = childSize + childPadding;
//
//        final int radius = (int) ((perSize / 2) / Math.sin(Math
//                .toRadians(perHalfDegrees)));
//
//        return Math.max(radius, minRadius);
//    }


//    View obtainView(int position, boolean[] outMetadata) {
//        final View updatedView = mAdapter.getView(position, null, this);
//        setItemViewLayoutParams(updatedView, position);
//
//
//
////        Trace.traceBegin(Trace.TRACE_TAG_VIEW, "obtainView");
//
////        outMetadata[0] = false;
//
//        // Check whether we have a transient state view. Attempt to re-bind the
//        // data and discard the view if we fail.
////        final View transientView = mRecycler.getTransientStateView(position);
//
//        mAdapter.getView(position, null, this);
//
//        if (transientView != null) {
//            final AbsListView.LayoutParams params = (AbsListView.LayoutParams) transientView.getLayoutParams();
//
//            // If the view type hasn't changed, attempt to re-bind the data.
//            if (params.viewType == mAdapter.getItemViewType(position)) {
//                final View updatedView = mAdapter.getView(position, transientView, this);
//
//                // If we failed to re-bind the data, scrap the obtained view.
//                if (updatedView != transientView) {
//                    setItemViewLayoutParams(updatedView, position);
//                    mRecycler.addScrapView(updatedView, position);
//                }
//            }
//
//            outMetadata[0] = true;
//
//            // Finish the temporary detach started in addScrapView().
//            transientView.dispatchFinishTemporaryDetach();
//            return transientView;
//        }
//
//        final View scrapView = mRecycler.getScrapView(position);
//        final View child = mAdapter.getView(position, scrapView, this);
//        if (scrapView != null) {
//            if (child != scrapView) {
//                // Failed to re-bind the data, return scrap to the heap.
//                mRecycler.addScrapView(scrapView, position);
//            } else if (child.isTemporarilyDetached()) {
//                outMetadata[0] = true;
//
//                // Finish the temporary detach started in addScrapView().
//                child.dispatchFinishTemporaryDetach();
//            }
//        }
//
//        if (mCacheColorHint != 0) {
//            child.setDrawingCacheBackgroundColor(mCacheColorHint);
//        }
//
//        if (child.getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
//            child.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
//        }
//
//        setItemViewLayoutParams(child, position);
//
//        if (AccessibilityManager.getInstance(mContext).isEnabled()) {
//            if (mAccessibilityDelegate == null) {
//                mAccessibilityDelegate = new ListItemAccessibilityDelegate();
//            }
//            if (child.getAccessibilityDelegate() == null) {
//                child.setAccessibilityDelegate(mAccessibilityDelegate);
//            }
//        }
//
//        Trace.traceEnd(Trace.TRACE_TAG_VIEW);
//
//        return child;
//    }
}
