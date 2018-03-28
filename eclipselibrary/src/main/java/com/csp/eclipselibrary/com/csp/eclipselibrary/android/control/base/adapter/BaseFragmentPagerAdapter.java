package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * [ViewPager] + [Fragment] 适配器
 * @version 1.0
 * @author tarena
 * <p style='font-weight:bold'>Date:</p> 2016年10月31日 下午5:10:11
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> frgList;

	public BaseFragmentPagerAdapter(FragmentActivity fActivity) {
		super(fActivity.getSupportFragmentManager());
		frgList = new ArrayList<Fragment>();
	}

	@Override
	public Fragment getItem(int position) {
		return frgList.get(position);
	}

	@Override
	public int getCount() {
		return frgList.size();
	}

	/**
	 * 添加[Fragment]
	 */
	public void addFragment(Fragment fragment) {
		if (fragment != null) {
			frgList.add(fragment);
		}
	}
}
