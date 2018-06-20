package com.csp.library.android.base.adapter.recycle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.csp.library.java.EmptyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView.Adapter
 * Created by csp on 2018/06/19.
 * Modified by csp on 2018/06/19.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class MultiItemAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mData;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    private SparseArray<IViewHolder> mViewHolderManager;

    public void addData(Collection<T> data, boolean append) {
        if (!append)
            mData.clear();

        if (EmptyUtil.isEmpty(data))
            return;

        mData.addAll(data);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        // ViewGroup parent = null;
        // if (!parent.isLongClickable()) {
        //     parent.setLongClickable(true);
        // }
        mOnItemLongClickListener = listener;
    }

    public MultiItemAdapter addViewHolder(int viewType, IViewHolder<T> viewHolder) {
        mViewHolderManager.put(viewType, viewHolder);
        return this;
    }

    public MultiItemAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();

        mViewHolderManager = new SparseArray<>();
        addMultiViewHolders();
    }

    public MultiItemAdapter(Context context, Collection<T> data) {
        this(context);

        addData(data, false);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = mViewHolderManager.get(viewType).getLayoutId();
        View view = mInflater.inflate(layoutId, parent, false);
        ViewHolder holder = ViewHolder.createViewHolder(mContext, view);
        onCreateViewHolder(holder);
        setOnClickListener(parent, holder);
        return holder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        IViewHolder viewHolder = mViewHolderManager.get(viewType);
        viewHolder.convert(holder, mData.get(position), position);
    }

    protected void onCreateViewHolder(ViewHolder holder) {
    }

    protected void setOnClickListener(final ViewGroup parent, final ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();

        if (mOnItemClickListener != null) {
            viewHolder.getConvertView().setOnClickListener((view) ->
                    mOnItemClickListener.onItemClick(parent, view, viewHolder, position, -1)
            );
        }

        if (mOnItemLongClickListener != null) {
            viewHolder.getConvertView().setOnClickListener((view) ->
                    mOnItemLongClickListener.onItemLongClick(parent, view, viewHolder, position, -1)
            );
        }
    }

    public interface IViewHolder<T> {

        int getLayoutId();

        void convert(ViewHolder holder, T datum, int offset);
    }

    /**
     * @see AdapterView.OnItemClickListener
     */
    public interface OnItemClickListener {

        void onItemClick(ViewGroup parent, View view, RecyclerView.ViewHolder viewHolder, int position, long id);
    }

    /**
     * @see AdapterView.OnItemLongClickListener
     */
    public interface OnItemLongClickListener {

        void onItemLongClick(ViewGroup parent, View view, RecyclerView.ViewHolder viewHolder, int position, long id);
    }

    /**
     * 添加布局
     *
     * @see #addViewHolder(int, IViewHolder)
     */
    public abstract void addMultiViewHolders();
}
