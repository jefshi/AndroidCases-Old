package com.csp.eclipselibrary.com.csp.eclipselibrary.android.view.my;

import com.common.R;
import com.common.android.control.base.adapter.BasePagerAdapter;
import com.common.android.control.util.LogUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 轮播View
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016年12月13日 下午7:49:47
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class CarouselView extends FrameLayout {

	private Integer[] mResource;

	public CarouselView(Context context) {
		super(context);
		initPain(null);
	}

	public CarouselView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPain(null);
	}

	public CarouselView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPain(null);
	}

	public void initPain(int[] resId) {
		// 数据处理
		setResource(resId);

		// 添加UI
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.mybanner_layout, this, false);
		addView(view);
		initView(view);
	}

	public void setResource(int[] resId) {
		// 数据处理
		if (resId == null || resId.length == 0) {
			mResource = new Integer[] {
					R.drawable.common_ic_launcher,
					R.drawable.common_ic_launcher,
					R.drawable.common_ic_launcher
			};
		} else {
			int len = resId.length + 2;
			mResource = new Integer[len];
			mResource[0] = resId[len - 1];
			mResource[len - 1] = mResource[0];

			for (int i = 0; i < resId.length; i++) {
				mResource[i + 1] = resId[i];
			}
		}
	}

	private void initView(View view) {
		final ViewPager vpgCarousel = (ViewPager) view.findViewById(R.id.vpgCarousel);
		ImageView imgCarouselClose = (ImageView) view.findViewById(R.id.imgCarouselClose);
		LinearLayout layLinCarousel = (LinearLayout) view.findViewById(R.id.layLinCarousel);

		BasePagerAdapter<Integer> adapter = new BasePagerAdapter<Integer>(view.getContext(), mResource) {

			@Override
			protected View getView(ViewGroup container, Integer object, int position) {
				ImageView img = new ImageView(getContext());
				img.setImageResource(object);
				return img;
			}
		};
		vpgCarousel.setAdapter(adapter);
		vpgCarousel.setCurrentItem(1);
		vpgCarousel.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				int maxIndex = vpgCarousel.getAdapter().getCount() - 1;
				LogUtil.logD(maxIndex);

				if (position == 0) {
					vpgCarousel.setCurrentItem(maxIndex - 1, false);
				}
				if (position == maxIndex) {
					vpgCarousel.setCurrentItem(1, false);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
}
