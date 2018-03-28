package com.csp.library.android.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Description: ViewPager 适配器 Demo
 * Create Date: 2016-10-31
 * Modify Date: 2017-11-28
 *
 * @author csp
 * @version 1.0.1
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public class DemoPagerAdapter extends BasePagerAdapter<Integer> {

	public DemoPagerAdapter(Context context, List<Integer> object) {
		super(context, object);
	}

	@Override
	protected View getView(ViewGroup container, int position) {
		// TODO 创建[item]对象，并设置相应内容事件等
		Integer id = getItem(position);

		// ImageView view = (ImageView) View.inflate(getContext(), R.layout.img, null);
		ImageView view = new ImageView(getContext());
		view.setImageResource(id);
		return view;
	}
}
