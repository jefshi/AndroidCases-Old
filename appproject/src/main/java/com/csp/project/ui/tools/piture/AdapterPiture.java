package com.project.tools.piture;

import java.util.List;

import com.common.android.control.base.adapter.BaseListAdapter;
import com.common.android.control.util.LogUtil;
import com.common.android.control.viewutil.loadimage.ImageUtil;
import com.project.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public class AdapterPiture extends BaseListAdapter<Animal> {

	// ========================================
	// setViewEvent()
	// ========================================
	/**
	 * 元素对象的事件绑定
	 */
	protected void setViewEvent(ViewHolder vHolder) {
		
	}

	// ========================================
	// setViewContent()
	// ========================================
	/**
	 * 元素对象内容设置
	 * @param object 内容数据
	 */
	protected void setViewContent(ViewHolder vHolder, Animal object) {
		LogUtil.logInfo(vHolder.getPosition() + " : " + object.getName());
		
		TextView txtTempImgTxt = vHolder.txtTempImgTxt;
		ImageView imgTempImgTxt = vHolder.imgTempImgTxt;

		txtTempImgTxt.setText(object.getName());
		ImageUtil.getInstance().loadBitmap(imgTempImgTxt, object.getPhoto(), vHolder.getPosition());
	}

	// ========================================
	// 构造方法
	// ========================================
	public AdapterPiture(Context context, List<Animal> object) {
		super(context, object, R.layout.template_lay_img_txt);
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
		public TextView		txtTempImgTxt;
		public ImageView	imgTempImgTxt;

		public ViewHolder(View view) {
			super(view);
		}

		@Override
		protected void initView(View view) {
			txtTempImgTxt = (TextView) view.findViewById(R.id.txtTempImgTxt);
			imgTempImgTxt = (ImageView) view.findViewById(R.id.imgTempImgTxt);
		}

		@Override
		protected void initViewEvent() {
			AdapterPiture.this.setViewEvent(this);
		}

		@Override
		protected void setViewContent(Animal object) {
			AdapterPiture.this.setViewContent(this, object);
		}
	}

	@Override
	protected BaseViewHolder getNewViewHolder(View view) {
		return new ViewHolder(view);
	}
}
