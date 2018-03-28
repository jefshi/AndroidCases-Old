package com.csp.library.android.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: ViewPager 适配器
 * Create Date: 2016-10-31
 * Modify Date: 2017-06-13
 *
 * @author csp
 * @version 1.0.1
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BasePagerAdapter<T> extends PagerAdapter {
	private Context context;
	private List<T> objects;    // 数据源

	// ========================================
	// 构造方法，Getter，Setter
	// ========================================
	public BasePagerAdapter(Context context, List<T> objects) {
		this.context = context;
		this.objects = new ArrayList<>();

		addObject(-1, objects, false);
	}

	public Context getContext() {
		return context;
	}

	public List<T> getObjects() {
		return objects;
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
	public void addObject(int position, List<T> objects, boolean clear) {
		if (clear)
			this.objects.clear();

		if (objects == null || objects.isEmpty())
			return;

		if (position < 0)
			this.objects.addAll(objects);
		else
			this.objects.addAll(position, objects);
	}

	/**
	 * @see #addObject(int, List, boolean)
	 */
	public void addObject(List<T> objects, boolean clear) {
		addObject(-1, objects, clear);
	}

	/**
	 * @see #addObject(int, List, boolean)
	 */
	public void addObject(int position, T object, boolean clear) {
		if (clear)
			this.objects.clear();

		if (object == null)
			return;

		if (position < 0)
			this.objects.add(object);
		else
			this.objects.add(position, object);
	}

	/**
	 * @see #addObject(int, Object, boolean)
	 */
	public void addObject(T object) {
		addObject(-1, object, false);
	}

	/**
	 * 删除数据源
	 */
	public void removeObject(T object) {
		objects.remove(object);
	}

	/**
	 * 清空数据源
	 */
	public void clearObject() {
		objects.clear();
	}

	/**
	 * @see android.widget.BaseAdapter#getItem(int)
	 */
	public T getItem(int position) {
		return objects.get(position);
	}

	// ========================================
	// instantiateItem(), 以[key / valuew], 形式存储数据
	// ========================================

	/**
	 * 判断是否显示[item]，true: 表示[item]与[key]为同一键值对，显示该[item]
	 *
	 * @param view [item]对象
	 * @param key  [item]的[key]
	 * @return boolean true: 显示[item]
	 */
	@Override
	public boolean isViewFromObject(View view, Object key) {
		return key == view;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	/**
	 * 创建[item/value], 并添加到[ViewPager], 同时返回[key]
	 *
	 * @param container 指[ViewPager]
	 * @param position  [item]的位置
	 * @return Object [item]的[key]
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = getView(container, position);
		container.addView(view);
		return view;
	}

	/**
	 * 销毁[item]
	 *
	 * @param container 指[ViewPager]
	 * @param position  当前[item]的位置
	 * @param key       当前[item]的Key
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object key) {
		container.removeView((View) key);
	}

	/**
	 * 创建[item]对象
	 */
	protected abstract View getView(ViewGroup container, int position);
}
