package com.project.day1028.fragment;

import com.common.android.control.util.IntentUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;
import com.project.day1028.fragment.FragmentCommActivity.OnFragmentInteractionListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * FragmentManager
 * 1) [Fragment]通过[FragmentManager]管理, 不是[Activity]
 * 2) 获取[FragmentManager]
 *   a) 使用[android.app.*]:            getFragmentManager()
 *   b) 使用[android.support.v4.app.*]: getSupportFragmentManager()
 *   c) 除非使用静态[Fragment], 否则一律使用[android.support.v4.app.*]
 * 
 * 静态注册[Fragment]
 * 1) [layout]文件使用<fragment>
 *   a) 必须填写: class 属性, 指向一个[Fragment]类
 * 
 * 动态注册[Fragment]
 * 1) 启动[FragmentTransaction]事务
 * 2) 追加指定[Fragment]到[布局]
 *   a) 注意: 统一选择的包, 即[Fragment]所属包要与[FragmentTransaction]一致
 * 3) 将[Fragment]追加至回退栈
 * 4) 提交事务
 * 
 * [Fragment]生命周期
 * 1) 可见
 *    onAttach() // Fragment依附于Activity时调用
 *    onCreate() // new Fragment时调用
 *    onCreateView()
 *    onActivityCreated() // 只有一次
 *    onStart()
 *    onResume()
 * 2) 不可见
 *    onPause()
 *    onStop()
 *    onDestroyView()
 *    onDestroy()
 *    onDetach()
 * 3) 添加与移除(使用事务)
 *   a) 添加/移除整个[Fragment]:  add(), remove()
 *   b) 添加/移除仅[View]:       attach(), detach()
 *   c) 显示/隐藏整个[Fragment]:  show(), hide()
 * 4) 查找[Fragment]
 *   a) fm.findFragmentByTag(String fragmentName)
 * 5) 添加/移除[Fragment], 添加/移除[View]过程不再主线程执行
 * 
 * [Fragment][Activity]生命周期
 * ...
 * 
 * ViewPager + Fragment 应用
 * 1) PagerAdapter 使用[FragmentPagerAdapter]即可
 * 2) 需要传入[FragmentManager]参数
 * 
 * Activity -> Fragment 通讯
 * 1) 提供静态有参实例化方法(传递数据)
 *   a) 目的: 实例化对象的同时, 绑定数据到对象上
 *   b) 方法: static Fragment newInstance(Object... params)
 *   c) 绑定: Fragment.setArguments(Bundle args)
 * 2) Fragment 中获取数据
 *   a) 方法: Bundle bundle = getArguments();
 * 
 * Fragment -> Activity 通讯
 * 1) 添加并初始化观察者对象(添加自定义监听器)
 * 2) [Fragment]中控件事件回调监听事件
 * 
 * 多个[Fragment]间通讯
 * ...
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-29 14:23:38
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class Activity1028_Fragment extends FragmentActivity implements OnFragmentInteractionListener {
	public TextView	txt;
	public ImageView	img;
	public ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[FunctionGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("静态注册[Fragment]", "staticFragment", false);
		fgv.addData("动态注册[Fragment]", "dynamicFragment", false);
		fgv.addData("[Fragment]生命周期", "fragmentLifeCycle", false);
		fgv.addData("[Fragment][Activity]生命周期", "fragmentActivityLifeCycle", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("RadioGroup + Fragment 应用", "fragmentGroup", false);
		fgv.addData("ViewPager + Fragment 应用", "fragmentPager", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("[Fragment]与[Activity]通讯", "commFragmentActivity", false);
		fgv.addData("多个[Fragment]间通讯", "commFragments", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	/**
	 * 静态注册[Fragment]
	 * 1) [layout]文件使用<fragment>
	 *   a) 必须填写: class 属性, 指向一个[Fragment]类
	 */
	public void staticFragment() {
		IntentUtil.startActivity(this, ActivityStaticFragment.class);
	}

	/**
	 * 动态注册[Fragment]
	 * 1) 启动[FragmentTransaction]事务
	 * 2) 追加指定[Fragment]到[布局]
	 *   a) 注意: 统一选择的包, 即[Fragment]所属包要与[FragmentTransaction]一致
	 * 3) 将[Fragment]追加至回退栈
	 * 4) 提交事务
	 */
	public void dynamicFragment() {
		// 启动[FragmentTransaction]事务
		// android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		// 替换[布局]为指定的[Fragment]
		// ft.replace(R.id.layFraOptions, new Fragment_app_txt(), "Fragment_TXT");
		ft.add(R.id.layFraOptions, new Fragment_v4_txt_LifeCycle(), "Fragment_TXT");

		// 将[Fragment]追加至回退栈
		ft.addToBackStack("fragment01");

		// 提交事务
		ft.commit();
	}

	/**
	 * [Fragment]生命周期
	 * 1) 可见
	 *    onAttach()
	 *    onCreate()
	 *    onCreateView()
	 *    onActivityCreated()
	 *    onStart()
	 *    onResume()
	 * 2) 不可见
	 *    onPause()
	 *    onStop()
	 *    onDestroyView()
	 *    onDestroy()
	 *    onDetach()
	 * 3) 添加与移除(使用事务)
	 *   a) 添加/移除整个[Fragment]:  add(), remove()
	 *   b) 添加/移除仅[View]:       attach(), detach()
	 *   c) 显示/隐藏整个[Fragment]:  show(), hide()
	 * 4) 查找[Fragment]
	 *   a) fm.findFragmentByTag(String fragmentName)
	 * 5) 添加/移除[Fragment], 添加/移除[View]过程不再主线程执行
	 */
	public void fragmentLifeCycle() {
		final String[] frgName = new String[] { "frg01", "frg02" };
		final FragmentManager fm = getSupportFragmentManager();

		for (int i = 0; i < frgName.length; i++) {
			// 启动事务
			FragmentTransaction ft = fm.beginTransaction();

			// 检测是否有此Fragment
			Fragment frg = fm.findFragmentByTag(frgName[i]);

			if (frg == null) {
				// 添加整个[Fragment]
				frg = Fragment_v4_txt_LifeCycle.newInstance(i);
				ft.add(R.id.layFraOptions, frg, frgName[i]);
			} else {
				// 仅添加[View]
				ft.attach(frg);

				// 仅显示隐藏的[Fragment]
				// ft.show(frg);
			}

			// 提交事务
			ft.commit();
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < frgName.length; i++) {
					// 启动事务
					FragmentTransaction ft = fm.beginTransaction();

					// 检测是否有此Fragment
					Fragment frg = fm.findFragmentByTag(frgName[i]);

					if (frg != null) {
						// 移除整个[Fragment]
						ft.remove(frg);

						// 仅移除[View]
						// ft.detach(frg);

						// 仅隐藏[Fragment]
						// ft.hide(frg);
					}

					// 提交事务
					ft.commit();
				}
			}
		}, 1000);

		txt.setText("请查看源代码说明, 添加与移除共有3种方式!");
	}

	/**
	 * [Fragment][Activity]生命周期
	 */
	public void fragmentActivityLifeCycle() {
		txt.setText("待补充!");
	}
	
	/**
	 * TODO
	 */
	private Fragment lastFragment = null;
	public void fragmentGroup() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		// 关闭旧的[Fragment]显示
		if (lastFragment != null) {
			ft.detach(lastFragment);
		}
		
		// 显示新的[Fragment]
		String frgTag = "要显示的Fragment TAG";
		Fragment frg = fm.findFragmentByTag(frgTag);
		if (frg == null) {
			frg = Fragment_v4_txt_LifeCycle.newInstance(1);
			ft.add(R.id.layFraOptions, frg , frgTag);
		} else {
			ft.attach(frg);
		}
		ft.commit();

		lastFragment = frg;
		
		txt.setText("待补充");
	}

	/**
	 * ViewPager + Fragment 应用
	 */
	public void fragmentPager() {
		IntentUtil.startActivity(this, ActivityFragmentViewPager.class);
		txt.setText("请注意生命周期!");
	}

	/**
	 * [Fragment]与[Activity]通讯
	 */
	public void commFragmentActivity() {
		// 启动事务
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		// 实例化[Fragment]
		String tip = "以下是Fragment, 信息与图片由Activity传入";
		Fragment frg = FragmentCommActivity.newInstance(tip, R.drawable.ic_launcher);
		
		// 追加[Fragment]
		ft.add(R.id.layFraOptions, frg);

		// 提交事务
		ft.commit();
	}

	@Override
	public void onFragmentInteraction(View view, Bundle bundle) {
		if (view.getId() == R.id.btnTempImgTxtBtn) {
			String tip = bundle.getString(FragmentCommActivity.ONE_KEY);
			int resId = bundle.getInt(FragmentCommActivity.TWO_KEY);
			txt.setText(tip);
			img.setImageResource(resId);
		}
	}
	
	/**
	 * 多个[Fragment]间通讯
	 */
	public void commFragments() {
		txt.setText("待补充!");
	}
}
