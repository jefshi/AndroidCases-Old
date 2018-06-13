package com.csp.cases.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.csp.cases.base.adapter.ItemAdapter;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.base.BaseLibraryActivity;

import java.util.List;

/**
 * Description: Base Activity
 * <p>Create Date: 2017/8/9
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public abstract class BaseActivity extends BaseLibraryActivity implements ItemInterface {
    protected Bundle bundle;
    private TextView txtDescription;
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTxtDescription(TextView txtDescription) {
        this.txtDescription = txtDescription;
    }

    @Override
    public void initBundle() {
        if (getIntent() != null)
            bundle = getIntent().getExtras();
    }

    @Override
    public void initViewEvent() {
    }

    @Override
    public void initViewContent() {
        String caseDescription = null;
        if (bundle != null)
            caseDescription = bundle.getString(ItemAdapter.KEY_DESCRIPTION);

        if (description != null)
            caseDescription += '\n' + description;

        txtDescription.setText(caseDescription);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    @SuppressWarnings("RedundantCast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setAbsListView(AbsListView view, List<ItemInfo> objects) {
        final ItemAdapter adapter = new ItemAdapter(this, objects);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAdapter(adapter);
        } else if (view instanceof ListView) {
            ((ListView) view).setAdapter(adapter);
        } else {
            ((GridView) view).setAdapter(adapter);
        }

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemview, int position, long id) {
                description = null;
                adapter.onItemClick(parent, itemview, position, id);

                String text = adapter.getItem(position).getDescription()
                        + (description == null ? "" : '\n' + description);
                txtDescription.setText(text);
            }
        });
    }
}
