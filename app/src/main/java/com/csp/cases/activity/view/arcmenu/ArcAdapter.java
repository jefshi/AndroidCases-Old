package com.csp.cases.activity.view.arcmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.csp.library.android.util.display_metrics.DisplayMetricsUtil;
import com.csp.library.android.util.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: AbsListView 适配器
 * <p>Create Date: 2016-10-31
 * <p>Modify Date: 2017-11-28
 *
 * @author csp
 * @version 1.0.2
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArcAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] objects; // 数据源
//    private int[] resources; // 模板文件: R.selector_item_background
    private LayoutInflater inflater; // 解析XML布局资源的填充器

    // ========================================
    // 构造方法，Getter，Setter
    // ========================================
    public ArcAdapter(Context context, Integer[] objects) {
        this.mContext = context;
        this.objects = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 添加数据
//        addObject(-1, objects, false);
    }

//    public List<Integer> getObjects() {
//        return objects;
//    }

    // ========================================
    // 数据源操作方法
    // ========================================

//    /**
//     * 追加数据源(不刷新UI)
//     *
//     * @param position 添加位置, -1: 添加在末尾
//     * @param objects  数据
//     * @param clear    true: 清空数据后，再设置数据源
//     */
//    public void addObject(int position, List<Integer> objects, boolean clear) {
//        if (clear)
//            this.objects.clear();
//
//        if (objects == null || objects.isEmpty())
//            return;
//
//        if (position < 0)
//            this.objects.addAll(objects);
//        else
//            this.objects.addAll(position, objects);
//    }
//
//    /**
//     * @see #addObject(int, List, boolean)
//     */
//    public void addObject(List<Integer> objects, boolean clear) {
//        addObject(-1, objects, clear);
//    }
//
//    /**
//     * @see #addObject(int, List, boolean)
//     */
//    public void addObject(int position, Integer object, boolean clear) {
//        if (clear)
//            this.objects.clear();
//
//        if (object == null)
//            return;
//
//        if (position < 0)
//            this.objects.add(object);
//        else
//            this.objects.add(position, object);
//    }
//
//    /**
//     * @see #addObject(int, Integer, boolean)
//     */
//    public void addObject(Integer object) {
//        addObject(-1, object, false);
//    }
//
//    /**
//     * 删除数据源
//     */
//    public void removeObject(Integer object) {
//        objects.remove(object);
//    }
//
//    /**
//     * 清空数据源
//     */
//    public void clearObject() {
//        objects.clear();
//    }

    // ========================================
    // getView()
    // ========================================
    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Integer getItem(int position) {
        return objects[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 构建[Item]
     * 注: 首屏显示数据时, [getView()]方法会执行四遍, 而不是一遍, 这可能会导致空指针错误
     *
     * @param position    当前[Item]在[ListView]中的绝对位置
     * @param convertView 当前[Item]对象(旧, 被复用前)
     * @param parent      [ListView]的父节点[ViewGroup]对象
     * @return 当前[Item]对象
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1) 获取当前[Item]对象
        convertView = getConvertView(position, convertView, parent);

        // 2) 获取当前[Item]的数据
        Integer object = getItem(position);

        if (convertView instanceof ImageView) {
            ((ImageView) convertView).setImageResource(object);
        }

//        // 3) 绑定当前[Item]内容对象
//        BaseViewHolder vHolder = getViewHolder(convertView);
//        if (vHolder == null) {
//            vHolder = getNewViewHolder(convertView);
//            bindViewHolder(convertView, vHolder);
//        }
//
//        // 4) 设置当前[Item]内容对象
//        vHolder.setPosition(position);
//        vHolder.onRefreshViewContent(object);
        return convertView;
    }

    /**
     * 获取当前[Item]对象
     * <p>重复利用，防止反复创建的资源消耗
     */
    protected View getConvertView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int radius = DisplayMetricsUtil.dipToPx(mContext, 40);
            WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
            wlp.height = wlp.width = radius;

            ImageView img = new ImageView(mContext);
            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            img.setLayoutParams(wlp);
            return img;
        }
        return convertView;
    }

//    /**
//     * 当前[Item]对象绑定对应的内容对象[ViewHolder]
//     *
//     * @param view    当前[Item]对象
//     * @param vHolder 当前[Item]内容对象
//     */
//    private void bindViewHolder(View view, BaseViewHolder vHolder) {
//        view.setTag(vHolder);
//    }
//
//    /**
//     * 获取当前[Item]上绑定的内容对象[ViewHolder]
//     *
//     * @param view 当前[Item]
//     */
//    @SuppressWarnings("unchecked")
//    public <V extends BaseViewHolder> V getViewHolder(View view) {
//        return (V) view.getTag();
//    }
//
//    // ========================================
//    // Item 内容设置: ViewHolder 封装
//    // ========================================
//
//    /**
//     * [Item]内容对象
//     */
//    protected abstract class BaseViewHolder {
//        private View view; // 当前[Item]对象
//        private int position;
//
//        protected BaseViewHolder(View view) {
//            this.view = view;
//            position = 0;
//            initView();
//            initViewEvent();
//        }
//
//        public int getPosition() {
//            return position;
//        }
//
//        private void setPosition(int position) {
//            this.position = position;
//        }
//
//        /**
//         * [Item]对象的初始化
//         */
//        protected abstract void initView();
//
//        /**
//         * [Item]对象的事件绑定
//         */
//        protected abstract void initViewEvent();
//
//        /**
//         * [Item]对象的内容设置
//         *
//         * @param object 内容数据
//         */
//        protected abstract void onRefreshViewContent(Integer object);
//
//        /**
//         * @link View.findViewById(int)
//         */
//        @SuppressWarnings("unchecked")
//        protected <V> V findViewById(int resId) {
//            return (V) view.findViewById(resId);
//        }
//    }
//
//    /**
//     * 获取[ViewHolder]对象
//     *
//     * @param view 当前[Item]
//     */
//    protected abstract BaseViewHolder getNewViewHolder(View view);
}
