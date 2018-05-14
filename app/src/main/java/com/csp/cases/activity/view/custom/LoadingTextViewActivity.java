package com.csp.cases.activity.view.custom;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.csp.cases.R;
import com.csp.view.loadingtextview.LoadingTextsView;

import java.util.ArrayList;
import java.util.List;

public class LoadingTextViewActivity extends Activity implements View.OnClickListener {

    private LoadingTextsView mLoadingTextsView;

    private Handler mHandler;
    private static int mShowContentIndex;
    private List<String> mContents = new ArrayList<>();

    {
        mContents.add("正在连接次元连接主服务器…完毕");
        mContents.add("正在智能选择最佳路线01…完毕");
        mContents.add("正在智能选择最佳路线02…完毕");
        mContents.add("正在智能选择最佳路线03…完毕");
        mContents.add("正在智能选择最佳路线04…完毕");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_custom_loadingtextview);

        mLoadingTextsView = findViewById(R.id.loading);
        mHandler = new Handler();

        resetContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_scroll:
                resetScroll();
                break;
            case R.id.btn_reset_content:
                resetContent();
                break;
        }
    }

    private void resetScroll() {
        if (mLoadingTextsView.getContents().isEmpty()) {
            mShowContentIndex = 0;
            mLoadingTextsView.setContents(mContents);
            mLoadingTextsView.startScroll();
        } else {
            mLoadingTextsView.resetScroll();
        }
    }

    private void resetContent() {
        mLoadingTextsView.setOnScrollListener(() ->
                mHandler.postDelayed(() -> {
                    if (mShowContentIndex >= mContents.size())
                        return;

                    mLoadingTextsView.addContent(mContents.get(mShowContentIndex));
                    mLoadingTextsView.startScroll();
                    mShowContentIndex++;
                }, 100)
        );

        mShowContentIndex = 0;
        mLoadingTextsView.setContents(null);
        mHandler.postDelayed(() -> {
            mLoadingTextsView.addContent(mContents.get(mShowContentIndex));
            mLoadingTextsView.startScroll();
            mShowContentIndex++;
        }, 1000);
    }
}