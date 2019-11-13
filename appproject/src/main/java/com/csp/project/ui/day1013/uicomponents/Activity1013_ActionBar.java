package com.project.day1013.uicomponents;

import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 切换显示[AcitonBar]
 * @see #switchActionBar() 详见源代码
 * 
 * ActionBar 大体可分为
 * 1) Home
 *   a) 导航图标, "<"
 *   b) Home: Icon, Logo(只能二选一)
 *   c) Title
 * 2) Label
 * 3) Menu Item
 * 
 * XML设置
 * 1) Home, [android:actionBarStyle/android:displayOptions]属性
 *   a) showTitle|showHome|useLogo
 *   b) showHome: 是否显示Icon, Logo
 * 2) 其他见[styles.xml]
 * 
 * [Home]部分
 * @see #setActionBarHome() 详见源代码
 * 
 * [Label]部分
 * 1) 添加自定义[View]
 *   @see #showCustomView() 详见源代码
 * 2) 添加导航 Tab
 *   @see #showNavTab() 详见源代码
 * 
 */
public class Activity1013_ActionBar extends Activity implements TabListener {
	protected TextView	txt;
	protected ImageView	img;
	protected ListView	lsv;

	protected ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("切换显示[AcitonBar]", "switchActionBar", false);
		fgv.addData("设置[AcitonBar]的[Home]部分", "setActionBarHome", false);
		fgv.addData("自定义[View]", "showCustomView", false);
		fgv.addData("添加导航 Tab", "showNavTab", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("styles.xml 设置ActionBar", "setStylesByXML", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);

		actionBar = getActionBar();
	}

	/**
	 * 切换显示[AcitonBar]
	 */
	public void switchActionBar() {
		actionBar = getActionBar();
		if (actionBar.isShowing()) {
			actionBar.hide();
		} else {
			actionBar.show();
		}
	}

	/**
	 * 设置[AcitonBar]的[Home]部分
	 */
	public void setActionBarHome() {
		String tip = null;
		
		// 显示导航, 即添加"<"
		actionBar.setDisplayHomeAsUpEnabled(true);
		tip = "导航图标\"<\"的点击事件有些问题, 具体见源代码";

		// 设置标题
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("我是主标题");
		actionBar.setSubtitle("我是副标题");

		// 设置Logo, Icon
		actionBar.setDisplayShowHomeEnabled(true);

		actionBar.setIcon(R.drawable.png_01);

		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(R.drawable.png_02);
		
		txt.setText(tip);
	}

	/**
	 * 导航"<", 点击事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			// 获取当前[Activiity]的[ParentActivity](将跳转至该页面)
			// 详见清单配置文件中该[Activiity]的[android:parentActivityName]属性
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			LogUtil.logInfo(upIntent);

			// 判定上述两个[Activity]是否在同一任务栈
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				// TODO
				
				// 不在同一个任务栈中, 新建一个任务栈, 并启动它
				TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				// TODO
				
				// 跳转到[ParentActivity]
				upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 自定义View
	 */
	public void showCustomView() {
		actionBar.setDisplayShowCustomEnabled(true); // 显示自定义View
		LayoutParams layoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
		TextView txt = new TextView(this);
		txt.setText("自定义[View]");
		actionBar.setCustomView(txt, layoutParams);
		// actionBar.setCustomView(R.layout.template_txt);
	}

	/**
	 * 添加导航 Tab
	 */
	public void showNavTab() {
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText("直播").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("短片").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("MV").setTabListener(this));
	}
	
	/**
	 * 添加导航 Tab 的事件处理(三个)
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
	
	/**
	 * styles.xml 设置ActionBar
	 */
	public void setStylesByXML() {
		txt.setText("详见清单配置文件与[styles.xml]文件");
	}
}
