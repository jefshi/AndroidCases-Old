package com.csp.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.csp.project.common.ProjectBaseActivity;
import com.csp.project.common.base.BaseListAdapter;
import com.csp.project.dto.base.ItemInfo;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ProjectBaseActivity {

    @Bind(R.id.grvMain)
    protected GridView grv;

    protected BaseListAdapter adapter;

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
        itemInfos.add(new ItemInfo("控件", ItemInfo.class));


        adapter = new ProjectAdapter(this, itemInfos);
        grv.setAdapter(adapter);
    }

    /**
     * 设置各个[View]事件, 只运行一次
     */
    @Override
    public void initViewEvent() {
        grv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectAdapter adapter = (ProjectAdapter) parent.getAdapter();
                ItemInfo itemInfo = adapter.getItem(position);

                startActivity(new Intent(adapter.getContext(), itemInfo.getJumpClass()));
            }
        });
    }

    static class ProjectAdapter extends BaseListAdapter<ItemInfo> {

        public ProjectAdapter(Context context, List<ItemInfo> data) {
            super(context, R.layout.item_project);
            addObject(data, false);
        }

        @Override
        protected BaseViewHolder createViewHodler(int viewType) {
            return new ViewHodler();
        }

        class ViewHodler extends BaseViewHolder {
            @Bind(R.id.txtItemProjectName)
            public TextView txtItemProjectName;
            @Bind(R.id.txtItemProjectDescription)
            public TextView txtItemProjectDescription;

            /**
             * 初始化布局的控件对象
             *
             * @param convertView 布局View
             */
            @Override
            public void initView(View convertView) {
                ButterKnife.bind(convertView);
            }

            /**
             * 初始化布局控件的事件
             */
            @Override
            public void initViewEvent() {
            }

            /**
             * 绑定数据到布局上
             *
             * @param object 数据
             */
            @Override
            public void bindViewData(ItemInfo object) {
                txtItemProjectName.setText(object.getName());
                txtItemProjectDescription.setText(object.getDescription());

                if (object.isEmphasize()) {
                    txtItemProjectName.setTextColor(Color.RED);
                }
            }
        }
    }
}
