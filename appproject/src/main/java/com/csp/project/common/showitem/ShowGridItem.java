package com.csp.project.common.showitem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.csp.project.R;
import com.csp.project.common.base.BaseListAdapter;
import com.csp.project.common.base.InitialUi;
import com.csp.project.common.util.LogUtil;
import com.csp.project.dto.base.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by csp on 2017/4/12 012.
 */
public class ShowGridItem {

    public static void showGridView(Context context, GridView grv, List<ItemInfo> itemInfos) {
        if (itemInfos == null) {
            itemInfos = new ArrayList<>();
        }

        grv.setAdapter(new ProjectAdapter(context, itemInfos));
        grv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectAdapter adapter = (ProjectAdapter) parent.getAdapter();
                ItemInfo itemInfo = adapter.getItem(position);

                Context context = adapter.getContext();
                context.startActivity(new Intent(context, itemInfo.getJumpClass()));
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
             * @param view 布局View
             */
            @Override
            public void initView(View view) {
                ButterKnife.bind(this, view);
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
