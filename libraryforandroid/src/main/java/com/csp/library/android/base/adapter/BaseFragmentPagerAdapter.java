package com.csp.library.android.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: ViewPager + Fragment 适配器
 * Create Date: 2016-10-31
 * Modify Date: 2016-06-13
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments; // 数据源

	// ========================================
	// 构造方法，Getter，Setter
	// ========================================
	public BaseFragmentPagerAdapter(FragmentActivity fa, List<Fragment> fragments) {
		super(fa.getSupportFragmentManager());
		this.fragments = new ArrayList<>();

		addFragment(-1, fragments, false);
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	// ========================================
	// 数据源操作方法
	// ========================================

	/**
	 * 追加数据源(不刷新UI)
	 *
	 * @param position 添加位置, -1: 添加在末尾
	 * @param objects  数据
	 * @param clear    true: 清空数据后，再设置数据源
	 */
	public void addFragment(int position, List<Fragment> objects, boolean clear) {
		if (clear)
			this.fragments.clear();

		if (objects == null || objects.isEmpty())
			return;

		if (position < 0)
			this.fragments.addAll(objects);
		else
			this.fragments.addAll(position, objects);
	}

	/**
	 * @see #addFragment(int, List, boolean)
	 */
	public void addFragment(List<Fragment> objects, boolean clear) {
		addFragment(-1, objects, clear);
	}

	/**
	 * @see #addFragment(int, List, boolean)
	 */
	public void addFragment(int position, Fragment object, boolean clear) {
		if (clear)
			this.fragments.clear();

		if (object == null)
			return;

		if (position < 0)
			this.fragments.add(object);
		else
			this.fragments.add(position, object);
	}

	/**
	 * @see #addFragment(int, Fragment, boolean)
	 */
	public void addFragment(Fragment object) {
		addFragment(-1, object, false);
	}

	/**
	 * 删除数据源
	 */
	public void removeFragment(Fragment object) {
		fragments.remove(object);
	}

	/**
	 * 清空数据源
	 */
	public void clearFragment() {
		fragments.clear();
	}
}
