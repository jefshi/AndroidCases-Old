package com.csp.cases.activity.component;

import com.csp.cases.activity.component.broadcast.BroadcastActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 组件/控件使用案例，包括广播、对话框、通知栏、widget 等
 * <p>Create Date: 2018/04/23
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ComponentActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("Broadcast", BroadcastActivity.class, "广播案例"));
        items.add(new ItemInfo("Dialog", DialogActivity.class, "对话框案例"));
        items.add(new ItemInfo("Notification", NotificationActivity.class, "状态栏通知案例"));
        items.add(new ItemInfo("Toast", ToastActivity.class, "Toast 通知案例"));
        return items;
    }
}
