package com.project.day1013.uicomponents;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

/**
 * Menu 种类
 * 1) 选项菜单(Option Menu)
 *   a) XML: 
 *   a) onCreateOptionsMenu() + menu.XML
 *   b) 事件: onOptionsItemSelected()
 * 2) 上下文菜单(Context Menu)
 * 3) 弹出式菜单 (Popu Menu)
 * 4) 子菜单 (Sub Menu)
 * 
 * 选项菜单(Option Menu)
 * 1) XML: res/menu/***.xml
 * 2) 初始化: onCreateOptionsMenu()
 * 3) 触发事件: onOptionsItemSelected()
 * 4) 获取菜单选项: MenuItem item = Menu.findItem(R.id.menuCopy)
 * 
 * 上下文菜单(Context Menu)
 * 1) XML: res/menu/***.xml
 * 2) 初始化: onCreateOptionsMenu()
 * 3) 触发事件: onOptionsItemSelected()
 * 4) 注册到控件上: registerForContextMenu(View)
 * 
 * 弹出式菜单 (Popu Menu)
 * 1) XML: res/menu/***.xml
 * 2) 创建菜单: new PopupMenu(Context, View)
 * 3) 初始化: PopupMenu.inflate(***.xml);
 * 4) 触发事件:
 *   a) setOnMenuItemClickListener()
 *   b) setOnDismissListener()
 * 5) 显示: PopupMenu.show()
 * 
 * 子菜单 (Sub Menu)
 * 1) 详见menu/***.XML, <item> -> <menu>
 * 
 * 显示浮动菜单(又称溢出式菜单)(反射)
 * @see #showOverFlowMenu() 详见源代码
 * @see #onMenuOpened() 详见源代码
 * 
 * 其他
 * 1) startActivityForResult(), 启动一个页面后，当该页面关闭时，调用[onActivityResult()]
 */
public class Activity1013_Menu extends Activity {
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
		fgv.addData("选项菜单(即[ActionBar]右上角)", "showOptionMenu", false);
		fgv.addData("上下文菜单", "showContextMenu", false);
		fgv.addData("弹出式菜单", "showPopuMenu", false);
		fgv.addData("子菜单", "showSubMenu", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("显示浮动/溢出式菜单(反射)", "showOverFlowMenu", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	// ========================================
	// 菜单点击事件处理
	// ========================================
	/**
	 * 菜单点击事件处理
	 */
	private void clickMenuEvent(MenuItem item) {
		int id = item.getItemId();
		// 拨打电话
		if (id == R.id.menuCall) {
			Intent intent = new Intent(Intent.ACTION_DIAL);
			Uri uri = Uri.parse("tel:18211126975");
			intent.setData(uri);
			startActivity(intent);
		}

		// 照相
		if (id == R.id.menuMedia) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 100);
		}

		// 拷贝文本
		if (id == R.id.menuCopy) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/*");
			intent.putExtra(Intent.EXTRA_TEXT, txt.getText().toString());
			startActivity(Intent.createChooser(intent, "分享"));
		}
	}

	/**
	 * 调用[startActivityForResult()], 则页面返回后, 调用该方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtil.logInfo("onActivityResult()");
		if (requestCode == 100) {
			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
			img.setImageBitmap(bitmap);
		}
	}

	// ========================================
	// 选项菜单(Option Menu)
	// ========================================
	public void showOptionMenu() {
		txt.setText("实现以及事件实现, 详见源代码类说明");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		LogUtil.logInfo("onCreateOptionsMenu()");
		getMenuInflater().inflate(R.menu.menu1012_ui_components, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		LogUtil.logInfo("onOptionsItemSelected()");
		clickMenuEvent(item);
		return super.onOptionsItemSelected(item);
	}

	// ========================================
	// 上下文菜单(Context Menu)
	// ========================================
	public void showContextMenu() {
		registerForContextMenu(txt);
		txt.setText("上下文菜单注册在这里, 长按弹出, 实现详见源代码");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		LogUtil.logInfo("onCreateContextMenu()");
		getMenuInflater().inflate(R.menu.menu1012_ui_components, menu);
		MenuItem item = menu.findItem(R.id.menuCopy);
		item.setVisible(true);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		LogUtil.logInfo("onContextItemSelected()");
		clickMenuEvent(item);
		return super.onContextItemSelected(item);
	}

	// ========================================
	// 弹出式菜单 (Popu Menu)
	// ========================================
	public void showPopuMenu() {
		final String tip = "弹出式菜单注册在这里, 实现详见源代码, 点击任意项触发事件, 关闭菜单触发事件";
		txt.setText(tip);

		PopupMenu pMenu = new PopupMenu(this, txt);
		pMenu.inflate(R.menu.menu1012_ui_components);
		pMenu.show();
		pMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				txt.setText(tip + "\n菜单点击事件触发成功(添加图片)");
				img.setImageResource(R.drawable.ic_launcher);
				clickMenuEvent(item);
				return true;
			}
		});
		pMenu.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(PopupMenu menu) {
				txt.setText(tip + "\n关闭菜单事件触发成功");
			}
		});
	}
	
	// ========================================
	// 子菜单 (Sub Menu)
	// ========================================
	public void showSubMenu() {
		txt.setText("详见源代码的类说明");
	}

	// ========================================
	// 显示浮动/溢出式菜单(反射)
	// ========================================
	// AcitonBar 显示浮动/溢出式菜单(反射)
	public void showOverFlowMenu() {
		txt.setText("须重新进入该页面, 才会有效果");

		ViewConfiguration vcf = ViewConfiguration.get(this);
		try {
			Field field = vcf.getClass().getDeclaredField("sHasPermanentMenuKey");
			field.setAccessible(true);
			field.set(vcf, false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 选项菜单显示图标(反射)
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		LogUtil.logInfo("onMenuOpened");
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			// 开发用, 查询类的所有方法
			// Method[] methods = menu.getClass().getDeclaredMethods();
			// for (Method method : methods) {
			// 	LogUtil.logInfo(method.getName().toString());
			// 	LogUtil.logInfo(method.toString());
			// }
			if ("MenuBuilder".equals(menu.getClass().getSimpleName())) {
				try {
					Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
					method.setAccessible(true);
					method.invoke(menu, true);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
