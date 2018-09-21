package com.csp.cases.activity.system.appinfo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csp.cases.R;
import com.csp.library.android.base.adapter.BaseListAdapter;

import java.util.List;

/**
 * Created by chenshp on 2018/4/23.
 */

public class AppInfoAdapter extends BaseListAdapter<AppInfo> {
    public AppInfoAdapter(Context context) {
        this(context, null);
    }

    public AppInfoAdapter(Context context, List<AppInfo> appInfos) {
        super(context, appInfos, R.layout.item_appinfo);
    }

    private void refreshViewContent(ViewHolder vHolder, AppInfo appInfo) {
        vHolder.imgIcon.setImageDrawable(appInfo.getIcon());
        vHolder.txtLable.setText(appInfo.getLabel());
        vHolder.txtPackagename.setText(appInfo.getPackageName());
        vHolder.txtVersionCode.setText(appInfo.getVersionCode());
    }

    private void setViewEvent(final ViewHolder vHolder) {
    }

    // ========================================
    // Item 内容设置: ViewHolder 封装
    // ========================================
    private class ViewHolder extends BaseViewHolder {
        private ImageView imgIcon;
        private TextView txtLable;
        private TextView txtPackagename;
        private TextView txtVersionCode;

        private ViewHolder(View view) {
            super(view);
        }

        @Override
        protected void initView() {
            imgIcon = findViewById(R.id.imgIcon);
            txtLable = findViewById(R.id.txtLable);
            txtPackagename = findViewById(R.id.txtPackagename);
            txtVersionCode = findViewById(R.id.txtVersionCode);
        }

        @Override
        protected void initViewEvent() {
            setViewEvent(this);
        }

        @Override
        protected void onRefreshViewContent(AppInfo appInfo) {
            refreshViewContent(this, appInfo);
        }
    }

    @Override
    protected BaseViewHolder getNewViewHolder(View view) {
        return new ViewHolder(view);
    }
}
