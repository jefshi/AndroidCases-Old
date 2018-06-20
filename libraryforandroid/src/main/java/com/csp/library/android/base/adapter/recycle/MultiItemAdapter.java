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
 * RecyclerView.Adapter - 多布局
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

    /**
     * 追加数据源
     *
     * @param data   数据
     * @param append false: 重置数据
     */
    public void addData(Collection<T> data, boolean append) {
        if (!append)
            mData.clear();

        if (EmptyUtil.isEmpty(data))
            return;

        mData.addAll(data);
    }

    /**
     * @see AdapterView#setOnItemClickListener(AdapterView.OnItemClickListener)
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * @see AdapterView#setOnItemLongClickListener(AdapterView.OnItemLongClickListener)
     */
    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener listener) {
        // ViewGroup parent = null;
        // if (!parent.isLongClickable()) {
        //     parent.setLongClickable(true);
        // }
        mOnItemLongClickListener = listener;
    }

    /**
     * 追加布局
     *
     * @see #onBindViewHolder(ViewHolder, int)
     */
    protected MultiItemAdapter addViewHolder(int viewType, IViewHolder<T> viewHolder) {
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

        viewHolder.getConvertView().setOnClickListener((view) -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(parent, view, viewHolder, position, -1);
        });

        viewHolder.getConvertView().setOnClickListener((view) -> {
            if (mOnItemLongClickListener != null)
                mOnItemLongClickListener.onItemLongClick(parent, view, viewHolder, position, -1);
        });
    }

    public interface IViewHolder<T> {

        /**
         * @return ViewHolder 对应布局
         */
        int getLayoutId();

        /**
         * ViewHolder 数据填充
         *
         * @param holder ViewHolder
         * @param datum  对应数据
         * @param offset 数据偏移量
         */
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
    protected abstract void addMultiViewHolders();
}
