package com.csp.cases.activity;

import com.csp.cases.activity.other.TabActivity;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/6/15.
 */

public class OtherCaseActivity extends BaseListActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("轮播", "carousel", "轮播（RecycleView）"));
        items.add(new ItemInfo("流式布局：", "flowLayout", "使用 RecyclerView.LayoutManager 完成流式布局"));
        items.add(new ItemInfo("tablayout", TabActivity.class, "[其他]案例"));
        return items;
    }

    private void carousel() {
//        if (Build.VERSION.SDK_INT > 22) {
//            // RecyclerView view;
//        }

    }

    private void flowLayout() {

    }
}
