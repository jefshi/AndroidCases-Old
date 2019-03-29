package com.csp.cases.activity.other;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csp.cases.R;
import com.csp.utils.android.MetricsUtil;
import com.csp.utils.android.log.LogCat;

import java.lang.reflect.Field;

/**
 * Created by chensp01 on 2019/3/29.
 */

public class TabActivity extends Activity {

    private TabLayout tabIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tablayout);

        findViewById(R.id.btn_teset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixTabLayoutIndicatorWidth(tabIndicator, 34);
            }
        });


        tabIndicator = findViewById(R.id.tab_indicator);
        tabIndicator.addTab(tabIndicator.newTab().setText("全部"));
        tabIndicator.addTab(tabIndicator.newTab().setText("支出"));
        tabIndicator.addTab(tabIndicator.newTab().setText("收入"));

        fixTabLayoutIndicatorWidth(tabIndicator, 34);

//        // TabLayout 与 ViewPager 结合导致标题为空
//        tabIndicator.setupWithViewPager(vpgMarketing);
//        for (int i = 0; i < tabIndicator.getTabCount(); i++) {
//            TabLayout.Tab tab = tabIndicator.getTabAt(i);
//            if (tab != null)
//                tab.setText(mMarketingType[i]);
//        }
    }

    private static void fixTabLayoutIndicatorWidth(TabLayout tabIndicator, float marginDp) {
        View view = tabIndicator.getChildAt(0);
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
            float margin = MetricsUtil.dipToPx(tabIndicator.getContext(), marginDp);
            lp.width = width;
            lp.leftMargin = lp.rightMargin = (int) margin;
            tabView.invalidate();
        }
    }
}
