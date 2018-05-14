package com.csp.cases.activity.view;

import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.csp.cases.activity.view.custom.LoadingTextViewActivity;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;
import com.csp.view.ScrollTextView;
import com.csp.view.loadingtextview.LoadingTextsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/5/10.
 */

public class CustomViewActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("跑马灯", "ScrollTextView", ""));
        items.add(new ItemInfo("LoadingTextView", "LoadingTextView", ""));
        items.add(new ItemInfo("LoadingTextViewActivity", LoadingTextViewActivity.class, ""));


        return items;
    }

    private void ScrollTextView() {
        ScrollTextView txt = new ScrollTextView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        txt.setLayoutParams(lp);
        txt.setText("banasdfhiaoulngahdopfuonwmqnej;");
        txt.setRndDuration(5000);
        lfrItem.addView(txt);

        txt.startScroll();
    }

    private void LoadingTextView() {
        LoadingTextsView loading = new LoadingTextsView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loading.setLayoutParams(lp);
        lfrItem.addView(loading);

        loading.startScroll();

        new Handler().postDelayed(() -> LogCat.e(loading.getScaleY()), 6000);
    }
}
