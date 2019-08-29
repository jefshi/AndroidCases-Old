package com.csp.cases.guides;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.guides.user_interface.UserInterfaceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * https://developer.android.google.cn/guide
 */
public class GuidesActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("User interface", UserInterfaceActivity.class, "广播案例"));
        return items;
    }
}
