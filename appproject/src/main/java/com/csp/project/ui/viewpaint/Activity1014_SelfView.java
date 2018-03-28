package com.project.day1014.viewpaint;

import com.common.android.control.util.LogUtil;
import com.common.android.view.my.TabPageView;
import com.project.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Activity1014_SelfView extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1014_selfview);

		// 取得控件[View]
		ViewPager vpg = (ViewPager) findViewById(R.id.vpg1014Guide);
		TabPageView tpv = (TabPageView) findViewById(R.id.tpv1014Guide);
		
		// ViewPager 实现
		setViewPager(vpg, tpv);
		
		// TabPageView 实现
		tpv.setCount(vpg.getAdapter().getCount());
		tpv.setShape(TabPageView.RECTANGLE);
		tpv.refresh();
		
		LogUtil.logInfo("OK-------------------------");
	}

	/**
	 * ViewPager 实现
	 */
	private void setViewPager(ViewPager vpg, View... views) {
		// ViewPager 数据源
		Integer[] images = new Integer[] {
				R.drawable.aha, R.drawable.ahb, R.drawable.ahc,
				R.drawable.ahd, R.drawable.ahe, R.drawable.ahf,
				R.drawable.ahg
		};

		// 以页面作为数据源
		//List<View> views = new ArrayList<View>();
		//LayoutInflater inflater = this.getLayoutInflater();
		//views.add(inflater.inflate(R.layout.activity_hello, null));
		//views.add(inflater.inflate(R.layout.activity_template_textview, null));
		//views.add(inflater.inflate(R.layout.activity_template_imageview, null));

		// ViewPager 适配器
		PagerAdapter adapter = getPagerAdater(this, images);
		vpg.setAdapter(adapter);

		// ViewPager 事件
		vpg.setOnPageChangeListener(getPageChangeListener(views[0]));
	}

	/**
	 * ViewPager 适配器
	 * @param context 数据[item]载体的上下文
	 * @param objects 数据源
	 * @return PagerAdapter
	 */
	private PagerAdapter getPagerAdater(final Context context, final Object[] objects) {
		return new PagerAdapter() {

			@Override
			/**
			 * 根据[View]是否与对应的Key是否是一个键值对，true的话显示[View]
			 * @param arg0 要显示的[View]
			 * @param arg1 要显示的[View]的Key
			 * @return boolean [false]则ViewPager不显示
			 */
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			/**
			 * @return int 数据个数([View]个数)
			 */
			public int getCount() {
				return objects.length;
			}

			@Override
			/**
			 * 创建[View](以Key/Valuew, 形式存储数据), 添加[Value]到[ViewPager], 同时返回[Key]
			 * @param container 指[ViewPager]
			 * @param position [View]的位置
			 * @return Object [View]的Key
			 */
			public Object instantiateItem(ViewGroup container, int position) {
				// 获取[item]载体
				//View view = (View) objects[position];
				
				View view = View.inflate(context, R.layout.template_img, null);
				((ImageView) view).setImageResource((Integer) objects[position]);

				// 添加[item]、返回[Key]
				container.addView(view);
				return view;
			}

			@Override
			/**
			 * 销毁[View]
			 * @param container 指[ViewPager]
			 * @param position [View]的位置
			 * @param object [View]的Key
			 */
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}
		};
	}
	
	/**
	 * 设置[ViewPager]事件
	 */
	private OnPageChangeListener getPageChangeListener(final View view) {
		return new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				TabPageView tpv = (TabPageView) view;
				tpv.setPosition(position);
				tpv.setOffset(positionOffset);
				tpv.refresh();
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
