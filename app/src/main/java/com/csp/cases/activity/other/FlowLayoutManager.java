package com.csp.cases.activity.other;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 参考：https://github.com/xiangcman/LayoutManager-FlowLayout/blob/master/library/src/main/java/com/library/flowlayout/FlowLayoutManager.java
 */
public class FlowLayoutManager extends RecyclerView.LayoutManager {

    //    final FlowLayoutManager self = this;
    private static final String TAG = FlowLayoutManager.class.getSimpleName();

    protected int width, height;
    private int left, top, right;

    private int usedMaxWidth; //最大容器的宽度

    private int verticalScrollOffset = 0; // 竖直方向上的偏移量

    //计算显示的内容的高度
    protected int totalHeight = 0;
    private Row row = new Row();
    private List<Row> lineRows = new ArrayList<>();

    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); // TODO ？？？
    }

    /**
     * 该方法主要用来获取每一个item在屏幕上占据的位置
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "onLayoutChildren");
        totalHeight = 0;
        int cuLineTop = top;
        //当前行使用的宽度
        int cuLineWidth = 0;
        int itemLeft;
        int itemTop;
        int maxHeightItem = 0;
        row = new Row();
        lineRows.clear();
        allItemFrames.clear();
        removeAllViews();
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            verticalScrollOffset = 0;
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);
        if (getChildCount() == 0) {
            width = getWidth();
            height = getHeight();
            left = getPaddingLeft();
            right = getPaddingRight();
            top = getPaddingTop();
            usedMaxWidth = width - left - right;
        }

        for (int i = 0; i < getItemCount(); i++) {
            Log.d(TAG, "index:" + i);
            View childAt = recycler.getViewForPosition(i);
            if (View.GONE == childAt.getVisibility()) {
                continue;
            }
            measureChildWithMargins(childAt, 0, 0);
            int childWidth = getDecoratedMeasuredWidth(childAt);
            int childHeight = getDecoratedMeasuredHeight(childAt);
            int childUseWidth = childWidth;
            int childUseHeight = childHeight;
            //如果加上当前的item还小于最大的宽度的话
            if (cuLineWidth + childUseWidth <= usedMaxWidth) {
                itemLeft = left + cuLineWidth;
                itemTop = cuLineTop;
                Rect frame = allItemFrames.get(i);
                if (frame == null) {
                    frame = new Rect();
                }
                frame.set(itemLeft, itemTop, itemLeft + childWidth, itemTop + childHeight);
                allItemFrames.put(i, frame);
                cuLineWidth += childUseWidth;
                maxHeightItem = Math.max(maxHeightItem, childUseHeight);
                row.addViews(new Item(childUseHeight, childAt, frame));
                row.setCuTop(cuLineTop);
                row.setMaxHeight(maxHeightItem);
            } else {
                //换行
                formatAboveRow();
                cuLineTop += maxHeightItem;
                totalHeight += maxHeightItem;
                itemTop = cuLineTop;
                itemLeft = left;
                Rect frame = allItemFrames.get(i);
                if (frame == null) {
                    frame = new Rect();
                }
                frame.set(itemLeft, itemTop, itemLeft + childWidth, itemTop + childHeight);
                allItemFrames.put(i, frame);
                cuLineWidth = childUseWidth;
                maxHeightItem = childUseHeight;
                row.addViews(new Item(childUseHeight, childAt, frame));
                row.setCuTop(cuLineTop);
                row.setMaxHeight(maxHeightItem);
            }
            //不要忘了最后一行进行刷新下布局
            if (i == getItemCount() - 1) {
                formatAboveRow();
                totalHeight += maxHeightItem;
            }

        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());
        Log.d(TAG, "onLayoutChildren totalHeight:" + totalHeight);
        fillLayout(recycler, state);
    }

    /**
     * 竖直方向需要滑动的条件
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 监听竖直方向滑动的偏移量
     *
     * @return 负数表示继续滑（貌似）
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d("TAG", "totalHeight:" + totalHeight);

//        if (mOrientation == HORIZONTAL) {
//            return 0;
//        }

        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {//限制滑动到顶部之后，不让继续向上滑动了
            travel = -verticalScrollOffset;//verticalScrollOffset=0
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;//verticalScrollOffset=totalHeight - getVerticalSpace()
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;
        // 平移容器内的item
        offsetChildrenVertical(-travel);
        fillLayout(recycler, state);

        return travel; // super.scrollVerticallyBy(dy, recycler, state);
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }


    //对出现在屏幕上的item进行展示，超出屏幕的item回收到缓存中
    private void fillLayout(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }

        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(getPaddingLeft(), getPaddingTop() + verticalScrollOffset,
                getWidth() - getPaddingRight(), verticalScrollOffset + (getHeight() - getPaddingBottom()));

        //对所有的行信息进行遍历
        for (int j = 0; j < lineRows.size(); j++) {
            Row row = lineRows.get(j);
            float lineTop = row.cuTop;
            float lineBottom = lineTop + row.maxHeight;
            //如果该行在屏幕中，进行放置item
            if (lineTop < displayFrame.bottom && displayFrame.top < lineBottom) {
                List<Item> views = row.views;
                for (int i = 0; i < views.size(); i++) {
                    View scrap = views.get(i).view;
                    measureChildWithMargins(scrap, 0, 0);
                    addView(scrap);
                    Rect frame = views.get(i).rect;
                    //将这个item布局出来
                    layoutDecoratedWithMargins(scrap,
                            frame.left,
                            frame.top - verticalScrollOffset,
                            frame.right,
                            frame.bottom - verticalScrollOffset);
                }
            } else {
                //将不在屏幕中的item放到缓存中
                List<Item> views = row.views;
                for (int i = 0; i < views.size(); i++) {
                    View scrap = views.get(i).view;
                    removeAndRecycleView(scrap, recycler);
                }
            }
        }
    }
}
