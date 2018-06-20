package com.csp.library.android.base.adapter.recycle;

import android.content.Context;

import java.util.Collection;

public abstract class SingleAdapter<T> extends MultiItemAdapter<T> {
    private int mLayoutId;

    public SingleAdapter(Context context, int layoutId) {
        super(context);

        mLayoutId = layoutId;
    }

    public SingleAdapter(Context context, int layoutId, Collection<T> data) {
        super(context);

        mLayoutId = layoutId;
        addData(data, false);
    }

    @Override
    public void addMultiViewHolders() {
        addViewHolder(0, new IViewHolder<T>() {
            @Override
            public int getLayoutId() {
                return mLayoutId;
            }

            @Override
            public void convert(ViewHolder holder, T datum, int offset) {
                SingleAdapter.this.convert(holder, datum, offset);
            }
        });
    }

    public abstract void convert(ViewHolder holder, T datum, int offset);
}
