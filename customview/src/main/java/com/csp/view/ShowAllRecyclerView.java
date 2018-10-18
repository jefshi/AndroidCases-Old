package com.csp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


/**
 * 显示全部数据的列表，避免列表内滑动
 * Created by csp on 2018/09/29.
 * Modified by csp on 2018/09/29.
 *
 * @version 1.0.0
 */
public class ShowAllRecyclerView extends RecyclerView {

    public ShowAllRecyclerView(Context context) {
        super(context);
    }

    public ShowAllRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowAllRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = 1 << 30 - 1;
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
