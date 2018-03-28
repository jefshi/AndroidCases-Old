package com.csp.cases.activity.uicomponent;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.base.activity.BaseGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Toast 通知案例
 * <p>Create Date: 2017/8/7
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class ToastActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("Toast通知：自定义View", "toast01", ""));

        return items;
    }

    /**
     * Toast通知：自定义View
     */
    private void toast01() {
        // 自定义View
        TextView txt = new TextView(this);
        txt.setText("自定义View");
        txt.setBackgroundColor(Color.BLACK);
        txt.setTextColor(Color.WHITE);

        // Toast 创建
        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(txt);
        toast.show();
    }
}
