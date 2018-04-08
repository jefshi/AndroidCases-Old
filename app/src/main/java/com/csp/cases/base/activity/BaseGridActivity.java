package com.csp.cases.base.activity;

import android.widget.GridView;
import android.widget.TextView;

import com.csp.cases.R;

/**
 * Description: GridView Item列表
 * <p>Create Date: 2016-06-14
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0
 * @since AndroidCases 1.0
 */
public abstract class BaseGridActivity extends BaseActivity {
	private GridView grvItem;

	@Override
	protected void setContentView() {
		setContentView(R.layout.ac_grid_item);
	}

	@Override
	public void initView() {
		setTxtDescription((TextView) findViewById(R.id.txtDescription));

		grvItem = findView(R.id.grvItem);
	}

	@Override
	public void initViewContent() {
		super.initViewContent();
		setAbsListView(grvItem, getItemInfos());
	}
}
