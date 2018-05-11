package com.csp.cases.activity.view;

import android.content.res.TypedArray;
import android.util.TypedValue;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/5/11.
 */

public class StyleActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("Theme", "theme", "获取主题样式，如：背景色等"));

        return items;
    }

    private void theme() {
        TypedArray array = obtainStyledAttributes(new int[]{
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });

        int backgroundColor = array.getColor(0, 0);
        int textColor = array.getColor(1, 0);
        array.recycle();

        LogCat.e(backgroundColor);
        LogCat.e(textColor);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int colorPrimary = typedValue.data;

        typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        int colorPrimaryDark = typedValue.data;

        LogCat.e(colorPrimary);
        LogCat.e(colorPrimaryDark);
    }
}
