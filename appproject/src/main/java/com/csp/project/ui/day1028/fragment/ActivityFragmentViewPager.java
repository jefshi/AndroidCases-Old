package com.project.day1028.fragment;

import com.project.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * ViewPager + Fragment 应用
 * 1) PagerAdapter 使用[FragmentPagerAdapter]即可
 * 2) 需要传入[FragmentManager]参数
 * @version 1.0
 * @author tarena
 * <p style='font-weight:bold'>Date:</p> 2016年11月1日 上午8:17:41
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ActivityFragmentViewPager extends FragmentActivity implements OnPageChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day1028_fragment_viewpager);
		
		// [ViewPager]设置
		ViewPager vPager = (ViewPager) findViewById(R.id.vpg1028_fragment);
		FragmentPagerAdapter adapter = getFragmentPagerAdapter(getSupportFragmentManager());
		vPager.setAdapter(adapter);
		vPager.setOnPageChangeListener(this);
	}
	
	public FragmentPagerAdapter getFragmentPagerAdapter(FragmentManager fm) {
		return new FragmentPagerAdapter(fm) {
			
			@Override
			public int getCount() {
				return 5;
			}
			
			@Override
			public Fragment getItem(int position) {
				return Fragment_v4_txt_LifeCycle.newInstance(position);
			}
		};
	}

	// ========================================
	// [ViewPager] 事件
	// ========================================
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
}
