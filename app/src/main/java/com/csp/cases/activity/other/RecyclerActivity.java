package com.csp.cases.activity.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.csp.cases.R;
import com.csp.utils.android.MetricsUtil;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chensp01 on 2019/3/29.
 */

public class RecyclerActivity extends Activity {

    private TabLayout tabIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recycler);

        Context context = this;
        RecyclerAdapter adapter = new RecyclerAdapter(this);

        RecyclerView rcv = findViewById(R.id.rcv);
        rcv.setLayoutManager(new GridLayoutManager(this, 4));
//        rcv.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//
//                int index = parent.indexOfChild(view);
//                int width = view.getWidth();
//                LogCat.e(index);
////                LogCat.e(width);
////                LogCat.e(state.getTargetScrollPosition());
//
//                super.getItemOffsets(outRect, view, parent, state);
//
//                DisplayMetrics metrics = MetricsUtil.getDisplayMetrics(context);
//                int widthPixels = metrics.widthPixels;
//                int space = (int) ((widthPixels - MetricsUtil.dipToPx(context, 72) * 3) / 2);
//
////
//                outRect.set(0, 10, index % 3 == 0 ? 0 : space, 0);
//            }
//        });
        rcv.setAdapter(adapter);

        List<String> data = new ArrayList<>();
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        adapter.addData(data, false);
    }
}
