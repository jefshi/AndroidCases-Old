package com.project.day1013.uicomponents;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.android.control.util.IntentUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

/**
 * UI Components
 * 1) 不是View
 * 2) 可以呈现View的一组对象
 * 
 * 常见类型
 * 1) Menu
 * 2) ActionBar
 * 3) Dialog
 * 4) Notification
 * 5) Toast
 */
public class Activity1012_UI_Components extends Activity {
	protected TextView	txt;
	protected ImageView	img;
	protected ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("ActionBar(TODO)", "showActionBar", false);
		fgv.addData("Menu(TODO)", "showMenu", false);


		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	/**
	 * ActionBar
	 */
	public void showActionBar() {
		IntentUtil.startActivity(this, com.project.day1013.uicomponents.Activity1013_ActionBar.class);
	}

	/**
	 * Menu
	 */
	public void showMenu() {
		IntentUtil.startActivity(this, com.project.day1013.uicomponents.Activity1013_Menu.class);
	}


}
