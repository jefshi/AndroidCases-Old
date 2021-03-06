package com.csp.cases.activity.component;

import com.csp.cases.activity.component.broadcast.BroadcastActivity;
import com.csp.cases.activity.component.camerademo.Camera01Activity;
import com.csp.cases.activity.component.camerademo.Camera02Activity;
import com.csp.cases.activity.component.camerademo.CameraMixActivity;
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
        items.add(new ItemInfo("Dialog", DialogActivity.class, "对话框、PopWindow 案例"));
        items.add(new ItemInfo("Notification", NotificationActivity.class, "状态栏通知案例"));
        items.add(new ItemInfo("Toast", ToastActivity.class, "Toast 通知案例"));
        items.add(new ItemInfo("---", "---", "---"));
        items.add(new ItemInfo("Camera max", CameraMixActivity.class, "Camera 案例"));
        items.add(new ItemInfo("Camera 1", Camera01Activity.class, "Camera2Activity 通知案例"));
        items.add(new ItemInfo("Camera 2", Camera02Activity.class, "Camera2Activity 通知案例"));
        return items;
    }
}
