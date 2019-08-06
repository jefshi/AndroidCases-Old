package com.csp.cases.activity.other;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csp.cases.R;
import com.csp.utils.android.MetricsUtil;
import com.csp.utils.android.widget.TabLayoutUtil;

/**
 * Created by chensp01 on 2019/3/29.
 */

public class RecyclerActivity extends Activity {

    private TabLayout tabIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recycler);

        View viewById = findViewById(R.id.btn_teset);


        findViewById(R.id.btn_teset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayoutUtil.fixTabLayoutIndicatorWidth(tabIndicator, 34);
            }
        });


        tabIndicator = findViewById(R.id.tab_indicator);
        tabIndicator.addTab(tabIndicator.newTab().setText("全部"));
        tabIndicator.addTab(tabIndicator.newTab().setText("支出"));
        tabIndicator.addTab(tabIndicator.newTab().setText("收入"));

        TabLayoutUtil.fixTabLayoutIndicatorWidth(tabIndicator, 34);

    }
}
