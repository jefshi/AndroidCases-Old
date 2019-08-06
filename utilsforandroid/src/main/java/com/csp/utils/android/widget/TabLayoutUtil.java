package com.csp.utils.android.widget;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csp.utils.android.MetricsUtil;

/**
 * TabLayout 控件工具
 * Created by csp on 2019/03/29.
 * Modified by csp on 2019/03/29.
 *
 * @version 1.0.0
 */
public class TabLayoutUtil {

    /**
     * 修改 TabLayout 的指示线的宽度，让宽度和文本宽度相同，但是或导致 TabLayout 点击面积变小
     *
     * @param marginDp 指示器和指示器之间的距离（不用除以 2）
     */
    public static void fixTabLayoutIndicatorWidth(TabLayout tabLayout, float marginDp) {
        View view = tabLayout.getChildAt(0);
        if (!(view instanceof LinearLayout))
            return;

        LinearLayout line = (LinearLayout) view;
        for (int i = 0; i < line.getChildCount(); i++) {
            view = line.getChildAt(i);
            if (!(view instanceof ViewGroup))
                break;

            ViewGroup tabView = (ViewGroup) view;
            view = tabView.getChildCount() > 1 ? tabView.getChildAt(1) : null;
            if (view == null)
                break;

            // 获取文本宽度
            int width = view.getWidth();
            if (width == 0) {
                view.measure(0, 0);
                width = view.getMeasuredWidth();
            }

            // 源码中指示线的宽度由 TabView 的宽度决定，和 padding 无关
            tabView.setPadding(0, 0, 0, 0);
            ViewGroup.LayoutParams params = tabView.getLayoutParams();
            if (!(params instanceof LinearLayout.LayoutParams))
                break;

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) params;
            float margin = MetricsUtil.dipToPx(tabLayout.getContext(), marginDp) / 2f;
            lp.width = width;
            lp.leftMargin = lp.rightMargin = (int) margin;
            tabView.invalidate();
        }
    }
}
