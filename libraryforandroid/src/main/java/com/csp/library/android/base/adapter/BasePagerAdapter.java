package com.csp.library.android.base.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * ViewPager 适配器，弃用，改用 RecyclerView + PagerSnapHelper
 * <p>
 * Created by csp on 2016/10/31.
 * Modified by csp on 2018/06/19.
 *
 * @version 1.0.3
 */
@Deprecated
@SuppressWarnings("unused")
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected Context mContext;
    protected List<T> mData;    // 数据源

    public Context getContext() {
        return mContext;
    }

    @Deprecated
    public List<T> getData() {
        return mData;
    }

    public BasePagerAdapter(Context context, List<T> data) {
        mContext = context;
        mData = new ArrayList<>();

        addData(data, false);
    }

    /**
     * @see #addData(int, Collection, boolean)
     */
    public void addData(Collection<T> data, boolean append) {
        addData(-1, data, append);
    }

    /**
     * @see #addData(int, Collection, boolean)
     */
    public void addData(T[] data, boolean append) {
        List<T> dataList = Arrays.asList(data);
        addData(-1, dataList, append);
    }

    /**
     * @see #addData(int, Collection, boolean)
     */
    public void addData(T datum, boolean append) {
        addData(-1, datum, append);
    }

    /**
     * 追加数据源
     *
     * @param position 添加位置, -1: 添加在末尾
     * @param data     数据
     * @param append   是否追加到列表末尾。false: 重置数据
     */
    public void addData(int position, Collection<T> data, boolean append) {
        if (!append)
            mData.clear();

        if (data != null && !data.isEmpty()) {
            if (position < 0)
                mData.addAll(data);
            else
                mData.addAll(position, data);
        }
        onDataChanged();
    }

    /**
     * @see #addData(int, Collection, boolean)
     */
    public void addData(int position, T datum, boolean append) {
        if (!append)
            mData.clear();

        if (datum != null) {
            if (position < 0)
                mData.add(datum);
            else
                mData.add(position, datum);
        }
        onDataChanged();
    }

    /**
     * @see Collection#remove(Object)
     */
    public void removeData(T datum) {
        mData.remove(datum);
        onDataChanged();
    }

    /**
     * @see Collection#clear()
     */
    public void clearData() {
        mData.clear();
        onDataChanged();
    }

    /**
     * 数据变化时回调
     */
    public void onDataChanged() {
    }

    /**
     * @see android.widget.BaseAdapter#getItem(int)
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object key) {
        return key == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object key) {
        container.removeView((View) key);
    }

    /**
     * 创建[item]对象
     *
     * @param container 指 ViewPager
     * @param position  当前 item 的位置
     * @return item 对象
     */
    protected abstract View getView(ViewGroup container, int position);
}