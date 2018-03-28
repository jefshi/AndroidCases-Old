package com.csp.library.android.base.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Description: ListView 适配器 Demo
 * <p>Create Date: 2016-10-31
 * <p>Modify Date: 2017-07-04
 *
 * @author csp
 * @version 1.0.1
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public class DemoListAdapter extends BaseListAdapter<Object> {
	public DemoListAdapter(Context context) {
		this(context, null);
	}

	public DemoListAdapter(Context context, List<Object> objects) {
		// super(context, objects, R.layout.item);
		super(context, objects, 0);
	}

	private void refreshViewContent(ViewHolder vHolder, Object object) {
		// TODO 设置[item]内容
	}

	private void setViewEvent(final ViewHolder vHolder) {
		// TODO 设置[item]事件
	}

	// ========================================
	// Item 内容设置: ViewHolder 封装
	// ========================================
	private class ViewHolder extends BaseViewHolder {
		// private TextView txt;
		// private ImageView img;

		private ViewHolder(View view) {
			super(view);
		}

		@Override
		protected void initView() {
			// txt = findViewById(R.id.txt);
			// img = findViewById(R.id.img);
		}

		@Override
		protected void initViewEvent() {
			setViewEvent(this);
		}

		@Override
		protected void onRefreshViewContent(Object object) {
			refreshViewContent(this, object);
		}
	}

	@Override
	protected BaseViewHolder getNewViewHolder(View view) {
		return new ViewHolder(view);
	}
}
