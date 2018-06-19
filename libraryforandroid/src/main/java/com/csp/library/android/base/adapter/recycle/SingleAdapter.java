package com.csp.library.android.base.adapter.recycle;

import android.content.Context;

public abstract class SingleAdapter<T> extends MultiItemAdapter<T> {

    public SingleAdapter(Context context, int layoutId) {
        super(context);
    }

    @Override
    public void addMultiViewHolders() {
        addViewHolder(0, new IViewHolder<T>() {
            @Override
            public int getLayoutId() {
                return getLayoutId();
            }

            @Override
            public void convert(ViewHolder holder, T datum, int offset) {

            }
        });
    }

    public abstract int getLayoutId();

    public abstract void convert(ViewHolder holder, T datum, int offset);
}
