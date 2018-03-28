package com.csp.project.ui;

import android.widget.GridView;

import com.csp.project.R;
import com.csp.project.common.ProjectBaseActivity;
import com.csp.project.common.showitem.ShowGridItem;
import com.csp.project.dto.base.ItemInfo;
import com.csp.project.ui.view.ViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ProjectBaseActivity {

    /**
     * 获取布局资源文件ID
     *
     * @return 布局资源文件ID
     */
    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }

    /**
     * 设置各个[View]内容, 只运行一次
     */
    @Override
    public void initViewContent() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo("View", "", ViewActivity.class));
        itemInfos.add(new ItemInfo("欢迎页面", "5s倒计时，动画跳转页面", MainActivity.class));


        GridView grv = (GridView) findViewById(R.id.grvMain);
        ShowGridItem.showGridView(this, grv, itemInfos);
    }

    /**
     * 设置各个[View]事件, 只运行一次
     */
    @Override
    public void initViewEvent() {
    }
}
