package com.project.day1013.drawerlayout;

import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * [DrawerLayout]
 * 
 * XML
 * 1) <android.support.v4.widget.DrawerLayout>
 * 2) 子元素只能是两个[ViewGroup]
 *   a) 其中拥有[android:layout_gravity]的为侧滑菜单内容
 *   b) 一般第二个子元素为侧滑菜单内容
 * 
 * 
 * 
 */
public class Activity1013_DrawerLayout extends Activity implements OnItemClickListener {
	private final String[] drwMenuItem = new String[] {
			"关闭侧滑菜单", "与主界面通讯"
	};

	public TextView			txt;
	public ImageView		img;
	public ListView			lsv;
	private DrawerLayout	drw;

	private ListView				lsvDrawer;
	private ArrayAdapter<String>	adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_1013_drawerlayout);

		
		
		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("弹出侧滑菜单", "showDrawer", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
		drw = (DrawerLayout) findViewById(R.id.drwMenu);

		// [初始化侧滑菜单的内容][ListView]
		lsvDrawer = (ListView) findViewById(R.id.lsvDrawer);
		adapter = new ArrayAdapter<String>(this, R.layout.template_txt, drwMenuItem);
		lsvDrawer.setAdapter(adapter);
		lsvDrawer.setOnItemClickListener(this);
		
		showDrawer();
	}

	private ActionBarDrawerToggle drawerToggle;

	public void showDrawer() {
		getActionBar().setDisplayHomeAsUpEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(
				this,
				drw, R.drawable.ic_launcher,
				R.string.pro0928_exam,
				R.string.pro1014_view_paint) {
			@Override
			public void onDrawerClosed(View drawerView) {
				setTitle(R.string.pro0928_exam);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				setTitle(R.string.pro1014_view_paint);
			}
		};
		drw.setDrawerListener(drawerToggle);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		drawerToggle.syncState();
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			return drawerToggle.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 关闭侧滑菜单, 关闭方向必须与XML的[android:layout_gravity]相同
		if (position == 0) {
			drw.closeDrawer(Gravity.END);
		}

		// 与主界面通讯
		if (position == 1) {
			txt.setText(drwMenuItem[position] + "成功");
		}
	}
}
