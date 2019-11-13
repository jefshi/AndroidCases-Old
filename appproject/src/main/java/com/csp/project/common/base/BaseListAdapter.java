package com.csp.project.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.csp.project.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * [ListView]一类列表适配器
 * Created by csp on 2017/3/2 002.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    /**
     * 基本数据
     */
    private Context mContext;
    private List<T> mObject; // 数据源
    private int[] mResource; // 布局资源文件
    private LayoutInflater mInflater; // 解析布局XML文件用

    // ========================================
    // 构造方法
    // ========================================
    public BaseListAdapter(Context context, int[] resId) {
        mContext = context;
        mObject = new ArrayList<T>();
        mResource = resId;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // mInflater = LayoutInflater.from(mContext); // 第二种方法
    }

    public BaseListAdapter(Context context, int resId) {
        this(context, new int[]{resId});
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

    // ========================================
    // 数据源操作方法
    // ========================================

    /**
     * 追加数据源
     *
     * @param position 添加位置, -1: 添加在末尾
     * @param objects  数据源
     * @param append   是否追加
     */
    public void addObject(int position, List<T> objects, boolean append) {
        if (objects == null || objects.isEmpty()) {
            return;
        }

        // 清空数据源
        if (!append) {
            mObject.clear();
        }

        // 追加数据
        // TODO 测试空列表的情况下，插入数据到第5位
        if (position < 0) {
            mObject.addAll(objects);
        } else {
            mObject.addAll(position, objects);
        }

        // 刷新UI
        notifyDataSetChanged();
    }

    /**
     * 追加数据源
     *
     * @param objects 数据源
     * @param append  是否追加
     */
    public void addObject(List<T> objects, boolean append) {
        addObject(-1, objects, append);
    }

    /**
     * 追加数据源
     *
     * @param position 添加位置, -1: 添加在末尾
     * @param object   数据源
     * @param append   是否追加
     */
    public void addObject(int position, T object, boolean append) {
        List<T> objects = new ArrayList<>(1);
        objects.add(object);
        addObject(position, objects, append);
    }

    /**
     * 追加数据源
     *
     * @param object 数据源
     * @param append 是否追加
     */
    public void addObject(T object, boolean append) {
        addObject(-1, object, append);
    }

    /**
     * 删除数据源
     *
     * @param object 数据源
     */
    public void removeObject(T object) {
        mObject.remove(object);
        notifyDataSetChanged();
    }

    /**
     * 删除数据源
     *
     * @param position 位置
     */
    public void removeObject(int position) {
        removeObject(getItem(position));
    }

    /**
     * 清空数据源
     */
    public void removeAllObject() {
        mObject.clear();
        notifyDataSetChanged();
    }

    // ========================================
    // 其他方法
    // ========================================

    /**
     * 获取[values]字符串资源
     *
     * @param resId 字符串资源ID
     * @return 字符串
     */
    public String getString(int resId) {
        return mContext.getString(resId);
    }

    // ========================================
    // getView() 等父类方法
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

    /**
     * 当前[Item]的布局选择
     *
     * @param position 位置
     */
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * 构建[Item]
     * 注：列表初始化时，该方法执行四遍，所以可能会有空指针隐患
     * <p>
     * 1) 饺子皮: 获取当前[Item]对象, 并更新绑定的子节点对象
     * 2) 饺子馅: 获取当前[Item]的数据
     * 3) 包饺子: 设置当前[Item]对象的内容
     *
     * @param position    当前[Item]在[ListView]中的绝对位置
     * @param convertView 当前[Item]对象(旧, 被复用前)
     * @param parent      [ListView]的父节点[ViewGroup]对象
     * @return View 当前[Item]对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        // 饺子皮: 初始化[convertView]
        convertView = initConvertView(position, convertView, parent);

        // 饺子馅: 获取数据
        T object = getItem(position);

        // 包饺子: 绑定数据
        BaseViewHolder vHolder = (BaseViewHolder) convertView.getTag();
        vHolder.setPosition(position);
        vHolder.bindViewData(object);

        return convertView;
    }

    /**
     * 饺子皮: 初始化[convertView]
     *
     * @param position    当前[Item]在[ListView]中的绝对位置
     * @param convertView 当前[Item]对象(旧, 被复用前)
     * @param parent      [ListView]的父节点[ViewGroup]对象
     * @return View [convertView]对象
     */
    private View initConvertView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // 初始化[convertView]
            int viewType = getItemViewType(position);
            convertView = mInflater.inflate(mResource[viewType], parent, false);

            // 初始化[ViewHolder]对象
            BaseViewHolder vHolder = createViewHodler(viewType);
            vHolder.initView(convertView);
            vHolder.initViewEvent();

            // 绑定[convertView]与[ViewHolder]的关联
            convertView.setTag(vHolder);
        }
        return convertView;
    }

    // ========================================
    // [Item]数据绑定: BaseViewHolder 封装
    // ========================================

    /**
     * [Item]数据绑定
     */
    public abstract class BaseViewHolder {
        private int position = 0;

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        /**
         * 初始化布局的控件对象
         *
         * @param view 布局View
         */
        public abstract void initView(View view);

        /**
         * 初始化布局控件的事件
         */
        public abstract void initViewEvent();

        /**
         * 绑定数据到布局上
         *
         * @param object 数据
         */
        public abstract void bindViewData(T object);
    }

    /**
     * 新建[ViewHolder]对象
     *
     * @param viewType 布局类型序号
     * @return [ViewHolder]对象
     */
    protected abstract BaseViewHolder createViewHodler(int viewType);
}
