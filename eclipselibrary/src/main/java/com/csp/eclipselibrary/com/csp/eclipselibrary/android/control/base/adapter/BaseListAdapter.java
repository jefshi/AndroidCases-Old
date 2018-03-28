package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.adapter;

import java.util.ArrayList;
import java.util.List;

import com.common.android.aInterface.ICustom.OnCheckAllListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import butterknife.ButterKnife;

public abstract class BaseListAdapter<T> extends BaseAdapter {
	// Adapter 基础数据
	private Context				mContext;			// 上下文对象
	private List<T>				mObject;			// 数据源
	private int[]				mResource;			// 模板文件: R.layout
	private LayoutInflater		mInflater;			// 解析XML(一般是Layout), 并初始化对象

	// 全选模式
	private boolean				isChooseMode;		// 是否是选择模式
	private boolean				isChoseAll;			// 是否全选
	private List<Integer>		choseItemIndex;		// 选中项位置集合
	private OnCheckAllListener	checkAllListener;	// 全选监听器

	// ========================================
	// 构造方法
	// ========================================
	/**
	 * 构造方法
	 * @param context  需要[Adapter]的对象所在的上下文
	 * @param object   数据
	 * @param layoutId 模板文件(多个)
	 */
	public BaseListAdapter(Context context, List<T> object, int[] layoutId) {
		// Adapter 基础数据
		mContext = context;
		mObject = new ArrayList<T>();
		mResource = layoutId;

		addObject(-1, object); // 添加数据

		// 解析XML文件
		// 方法01、方法02
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// mInflater = LayoutInflater.from(context);

		// 全选模式
		isChooseMode = false;
		isChoseAll = false;
		choseItemIndex = null; // 内存优化, 调用方法[setChooseMode(boolean)]后初始化
		checkAllListener = null;
	}

	/**
	 * 构造方法
	 * @param context  需要[Adapter]的对象所在的上下文
	 * @param object   数据
	 * @param layoutId 模板文件(单个)
	 */
	public BaseListAdapter(Context context, List<T> object, int layoutId) {
		this(context, object, new int[] { layoutId });
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

	public int[] getResource() {
		return mResource;
	}

	public LayoutInflater getInflater() {
		return mInflater;
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
	 * 追加数据源
	 * @param position 添加位置, -1: 添加在末尾
	 * @param mObject  数据源
	 * @param append   是否追加
	 */
	public void addObject(int position, List<T> objects, boolean append) {
		if (!append) {
			mObject.clear();
		}

		addObject(position, objects);
		notifyDataSetChanged();
	}

	/**
	 * 追加数据源
	 * @param mObject 数据源
	 * @param append  是否追加
	 */
	public void addObject(List<T> objects, boolean append) {
		addObject(-1, objects, append);
	}

	/**
	 * 追加数据源
	 * @param position 添加位置, -1: 添加在末尾
	 * @param mObject  数据源
	 * @param append   是否追加
	 */
	public void addObject(int position, T object, boolean append) {
		if (!append) {
			mObject.clear();
		}

		if (object != null) {
			if (position < 0) {
				mObject.add(object);
			} else {
				mObject.add(position, object);
			}
		}

		notifyDataSetChanged();
	}

	/**
	 * 追加数据源
	 * @param mObject 数据源
	 * @param append  是否追加
	 */
	public void addObject(T object, boolean append) {
		addObject(-1, object, append);
	}

	/**
	 * 删除数据源
	 */
	public void removeObject(T object) {
		mObject.remove(object);
		notifyDataSetChanged();
	}

	/**
	 * 清空数据源
	 */
	public void clear() {
		mObject.clear();
		notifyDataSetChanged();
	}

	// ========================================
	// 全选模式方法
	// ========================================
	/**
	 * 获取当前模式是否是选择模式
	 */
	public boolean isChooseMode() {
		return isChooseMode;
	}

	/**
	 * 获取是否全选
	 */
	public boolean isChoseAll() {
		return isChoseAll;
	}

	/**
	 * 获取选中项位置集合
	 */
	public List<Integer> getChoseItemIndex() {
		return choseItemIndex;
	}

	/**
	 * 获取选中项
	 */
	public List<T> getChoseItem() {
		List<T> choseItem = new ArrayList<T>();
		for (int position : choseItemIndex) {
			choseItem.add(getItem(position));
		}
		return choseItem;
	}

	/**
	 * 设置全选监听器
	 * @param listener 监听器
	 */
	public void setOnCheckAllListener(OnCheckAllListener listener) {
		checkAllListener = listener;
	}

	/**
	 * 设置是否是选择模式
	 * @param canChoose 是否是选择模式
	 */
	public void setChooseMode(boolean canChoose) {
		isChooseMode = canChoose;
		isChoseAll = false;
		if (choseItemIndex == null) {
			choseItemIndex = new ArrayList<Integer>();
		} else {
			choseItemIndex.clear();
		}
		notifyDataSetChanged();
	}

	/**
	 * 设置全选
	 * @param choseAll 是否全选
	 */
	public void setChoseAll(boolean choseAll) {
		isChoseAll = choseAll;

		// 设置被选中项
		choseItemIndex.clear();
		if (choseAll) {
			for (int i = 0; i < getCount(); i++) {
				choseItemIndex.add(i);
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * 设置被选中项
	 * @param position 选中项位置
	 */
	public void setChoseItem(int position) {
		if (choseItemIndex.contains((Integer) position)) {
			choseItemIndex.remove((Integer) position);
		} else {
			choseItemIndex.add(position);
		}

		// 是否全选
		if (choseItemIndex.size() == getCount()) {
			isChoseAll = true;
		} else {
			isChoseAll = false;
		}
		notifyDataSetChanged();

		// 全选监听器
		if (checkAllListener != null) {
			checkAllListener.onCheckAll(isChoseAll);
		}
	}

	// ========================================
	// 其他方法
	// ========================================
	/**
	 * 获取[values]字符串资源
	 */
	public String getString(int resId) {
		return mContext.getString(resId);
	}

	// ========================================
	// getView()
	// ========================================
	@Override
	public int getCount() {
		return mObject.size();
	}

	@Override
	public T getItem(int position) {
		return mObject.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return mResource.length;
	}

	@Override
	public int getItemViewType(int position) {
		return getBaseItemViewType(position);
	}

	/**
	 * 注: 首屏显示数据时, [getView()]方法会执行四遍, 而不是一遍, 这可能会导致空指针错误
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent);
	}

	/**
	 * 构建[Item]
	 * 1) 饺子皮: 获取当前[Item]对象, 并更新绑定的子节点对象
	 * 2) 饺子馅: 获取当前[Item]的数据
	 * 3) 包饺子: 设置当前[Item]对象的内容
	 * 
	 * @param position    当前[Item]在[ListView]中的绝对位置
	 * @param convertView 当前[Item]对象(旧, 被复用前)
	 * @param parent      [ListView]的父节点[ViewGroup]对象
	 * @return 当前[Item]对象
	 */
	protected View createViewFromResource(int position, View convertView, ViewGroup parent) {
		// 1) 饺子皮: 获取当前[Item]对象, 并更新绑定的子节点对象
		convertView = setViewData(position, convertView, parent);

		// 2) 饺子馅: 获取当前[Item]的数据
		T object = getItem(position);

		// 3) 包饺子: 设置当前[Item]对象的内容
		BaseViewHolder vHolder = getViewHolder(convertView);
		if (vHolder != null) {
			vHolder.setPosition(position);
			vHolder.setViewContent(object);
		}

		return convertView;
	}

	/**
	 * 获取当前[Item]对象, 并更新绑定的子节点对象
	 */
	protected View setViewData(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 非常耗时与麻烦
			// 方法01
			int type = getItemViewType(position);
			convertView = getInflater().inflate(mResource[type], parent, false);

			// 方法02
			// view = View.inflate(mContext, resource, parent);

			// ViewHolder vHolder = new ViewHolder(convertView);
			BaseViewHolder vHolder = getNewViewHolder(convertView);
			bindViewHolder(convertView, vHolder);
		}
		return convertView;
	}

	/**
	 * 在当前[Item]上绑定子节点对象集[ViewHolder]
	 * @param view    当前[Item]
	 * @param vHolder 需要绑定的子节点对象集
	 */
	protected void bindViewHolder(View view, BaseViewHolder vHolder) {
		view.setTag(vHolder);
	}

	/**
	 * 获取[Item]上绑定的子节点对象集[ViewHolder]
	 * @param view 当前[Item]
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T getViewHolder(View view) {
		return (T) view.getTag();
	}

	// ========================================
	// Item 内容设置: ViewHolder 封装
	// ========================================
	/**
	 * [Item]的子元素设置
	 * 缺点: 内存占用加大，用空间换时间
	 */
	public abstract class BaseViewHolder {
		private int		position;
		private boolean	autoBind;	// ButterKnife, 自动绑定成功标记

		public BaseViewHolder(View view) {
			position = 0;
			try {
				ButterKnife.bind(this, view); // ButterKnife 初始化
				autoBind = true;
			} catch (Exception e) {
				autoBind = false;
			}
			initView(view); // 元素对象初始化
			initViewEvent(); // 元素对象事件绑定
		}

		/**
		 * 元素对象初始化
		 */
		protected void initView(View view) {
		}

		/**
		 * 元素对象的事件绑定
		 */
		protected abstract void initViewEvent();

		/**
		 * 元素对象内容设置
		 * @param object 内容数据
		 */
		protected abstract void setViewContent(T object);

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public boolean isAutoBind() {
			return autoBind;
		}

		/**
		 * 多个模板布局时, 获取当前使用的模板布局顺序
		 */
		public int getItemViewType() {
			return BaseListAdapter.this.getItemViewType(position);
		}
	}

	/**
	 * 相当于重写父类的[getItemViewType()]方法
	 */
	public abstract int getBaseItemViewType(int position);

	/**
	 * 获取[ViewHolder]对象
	 * @param view 当前[Item]
	 */
	protected abstract BaseViewHolder getNewViewHolder(View view);
}
