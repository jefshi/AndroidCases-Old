package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.demo;

import java.util.List;

import com.common.android.control.base.adapter.BasePagerAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdapterPagerDemo extends BasePagerAdapter<Bitmap> {

	public AdapterPagerDemo(Context context, List<Bitmap> object) {
		super(context, object);
	}

	@Override
	protected View getView(ViewGroup container, Bitmap object, int position) {
		ImageView img = new ImageView(getContext());
		img.setImageBitmap(object);
		return img;
	}
}
