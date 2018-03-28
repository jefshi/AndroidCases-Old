package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.demo;

import java.util.List;

import com.common.android.control.base.adapter.BaseListAdapter;

import android.content.Context;
import android.view.View;

/**
 * 需要修改的地方
 * 1) 构造方法, 模板布局资源文件ID
 * 2) ViewHolder.initView(), 模板上控件初始化
 * 3) getBaseItemViewType(), 多个模板布局时, 选择哪个模板, 相当于重写[getItemViewType(int)]方法
 * 
 * 抽象类转实例类要修改的地方
 * 1) 上述说明中需要修改的地方
 * 2) 泛型转具体类
 * 3) 实现方法: setViewEvent(), setViewContent()
 */
public abstract class AdapterListDemo<T> extends BaseListAdapter<T> {

	// ========================================
	// setViewEvent()
	// ========================================
	/**
	 * 元素对象的事件绑定
	 */
	protected abstract void initViewEvent(ViewHolder vHolder);

	// ========================================
	// setViewContent()
	// ========================================
	/**
	 * 元素对象内容设置
	 * @param object 内容数据
	 */
	protected abstract void setViewContent(ViewHolder vHolder, T object);

	// ========================================
	// 构造方法
	// ========================================
	public AdapterListDemo(Context context) {
		super(context, null, new int[] {
				// R.layout.template_lay_img,
				// R.layout.template_lay_txt
		});
	}
	
	public AdapterListDemo(Context context, List<T> object) {
		super(context, object, new int[] {
				// R.layout.template_lay_img,
				// R.layout.template_lay_txt
		});
	}

	// ========================================
	// getItemViewType()
	// ========================================
	@Override
	public int getBaseItemViewType(int position) {
		return 0;
	}

	// ========================================
	// Item 内容设置: ViewHolder 封装
	// ========================================
	public class ViewHolder extends BaseViewHolder {
		// public TextView		txtTempLayTxt;
		// public ImageView	imgTempLayImg;

		public ViewHolder(View view) {
			super(view);
		}

		@Override
		protected void initView(View view) {
			// imgTempLayImg = (ImageView) view.findViewById(R.id.imgTempLayImg);
			// txtTempLayTxt = (TextView) view.findViewById(R.id.txtTempLayTxt);
		}

		@Override
		protected void initViewEvent() {
			AdapterListDemo.this.initViewEvent(this);
		}

		@Override
		protected void setViewContent(T object) {
			AdapterListDemo.this.setViewContent(this, object);
		}
	}

	@Override
	protected BaseViewHolder getNewViewHolder(View view) {
		return new ViewHolder(view);
	}
}
