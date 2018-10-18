package com.csp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 显示全部数据的列表，避免列表内滑动
 * Created by csp on 2018/09/29.
 * Modified by csp on 2018/09/29.
 *
 * @version 1.0.0
 */
public class ShowAllListView extends ListView {
    public ShowAllListView(Context context) {
        super(context);
    }

    public ShowAllListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowAllListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = 1 << 30 - 1;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
