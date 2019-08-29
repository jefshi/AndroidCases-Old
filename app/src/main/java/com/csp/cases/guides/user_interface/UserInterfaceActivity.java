package com.csp.cases.guides.user_interface;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.guides.user_interface.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


public class UserInterfaceActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("Settings", SettingsActivity.class, "广播案例"));
        return items;
    }
}
