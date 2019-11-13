package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasePagerAdapter<T> extends PagerAdapter {
	private Context	mContext;	// 上下文对象
	private List<T>	mObject;	// 数据源

	// ========================================
	// 构造方法
	// ========================================
	/**
	 * 构造方法
	 * @param context 需要[Adapter]的对象所在的上下文
	 * @param object  数据
	 */
	public BasePagerAdapter(Context context, List<T> object) {
		mContext = context;
		mObject = new ArrayList<T>();

		addObject(-1, object);
	}

	/**
	 * 构造方法
	 * @param context 需要[Adapter]的对象所在的上下文
	 * @param object  数据
	 */
	public BasePagerAdapter(Context context, T[] object) {
		mContext = context;
		mObject = new ArrayList<T>();

		addObject(-1, object);
	}

	/**
	 * 构造方法
	 * @param context 需要[Adapter]的对象所在的上下文
	 */
	public BasePagerAdapter(Context context) {
		mContext = context;
		mObject = new ArrayList<T>();
	}

	// ========================================
	// get() Method
	// ========================================
	public Context getContext() {
		return mContext;
	}

	public List<T> getObject() {
		return mObject;
	}

	// ========================================
	// 数据源操作方法
	// ========================================
	/**
	 * 追加数据源(不刷新UI)
	 * @param position 添加位置, -1: 添加在末尾
	 * @param object   数据
	 */
	private void addObject(int position, List<T> objects) {
		if (objects != null && !objects.isEmpty()) {
			if (position < 0) {
				mObject.addAll(objects);
			} else {
				mObject.addAll(position, objects);
			}
		}
	}

	/**
	 * 追加数据源(不刷新UI)
	 * @param position 添加位置, -1: 添加在末尾
	 * @param object   数据
	 */
	private void addObject(int position, T[] objects) {
		if (objects != null && objects.length != 0) {
			List<T> objectList = new ArrayList<T>();
			Collections.addAll(objectList, objects);
			addObject(position, objectList);
		}
	}

	/**
	 * 追加数据源
	 * @param position 添加位置, -1: 添加在末尾
	 * @param mObject  数据源
	 * @param append   是否追加
	 */
	public void addObject(List<T> objects, boolean append) {
		if (!append) {
			mObject.clear();
		}

		addObject(-1, objects);
		notifyDataSetChanged();
	}

	/**
	 * 追加数据源
	 * @param position 添加位置, -1: 添加在末尾
	 * @param mObject  数据源
	 * @param append   是否追加
	 */
	public void addObject(T[] objects, boolean append) {
		if (!append) {
			mObject.clear();
		}

		addObject(-1, objects);
		notifyDataSetChanged();
	}

	// ========================================
	// instantiateItem()
	// ========================================
	/**
	 * 根据[View]是否与对应的Key是否是一个键值对，true的话显示[View]
	 * @param arg0 要显示的[View]
	 * @param arg1 要显示的[View]的Key
	 * @return boolean [false]则ViewPager不显示
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return mObject.size();
	}

	/**
	 * 创建[View](以Key/Valuew, 形式存储数据), 添加[Value]到[ViewPager], 同时返回[Key]
	 * @param container 指[ViewPager]
	 * @param position [View]的位置
	 * @return Object [View]的Key
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = getView(container, mObject.get(position), position);
		container.addView(view);
		return view;
	}

	/**
	 * 销毁[View]
	 * @param container 指[ViewPager]
	 * @param position  [View]的位置
	 * @param object    [View]的Key
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	/**
	 * 创建[View]
	 * View view = View.inflate(context, R.layout.activity_image, null);
	 */
	protected abstract View getView(ViewGroup container, T object, int position);
}
