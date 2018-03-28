package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil;

import android.view.View;
import android.widget.ImageView;

public class ImageViewUtil {
	/**
	 * 设置[ImageView]的图片与可见性
	 * @param resImgId   ImageView id
	 * @param drawableId 图片资源ID, [-1]表示不可见
	 */
	public static void setImageView(View view, int resImgId, int drawableId) {
		ImageView img = (ImageView) view.findViewById(resImgId);
		if (drawableId > -1) {
			img.setImageResource(drawableId);
		} else {
			img.setVisibility(View.GONE);
		}
	}
}
