package com.csp.cases.base.activity;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.csp.cases.R;

/**
 * Description: ListView Item列表
 * <p>Create Date: 2016-06-14
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public abstract class BaseListActivity extends BaseActivity {
    private ListView lsvItem;
    protected FrameLayout lfrItem;
    protected ImageView imgItem;
    protected TextView txtItem;

    @Override
    protected void setContentView() {
        setContentView(R.layout.ac_list_item);
    }

    @Override
    public void initView() {
        setTxtDescription((TextView) findViewById(R.id.txtDescription));

        lsvItem = findView(R.id.lsvItem);
        lfrItem = findView(R.id.lfrItem);
        imgItem = findView(R.id.imgItem);
        txtItem = findView(R.id.txtItem);
    }

    @Override
    public void initViewContent() {
        super.initViewContent();
        setAbsListView(lsvItem, getItemInfos());
    }
}
