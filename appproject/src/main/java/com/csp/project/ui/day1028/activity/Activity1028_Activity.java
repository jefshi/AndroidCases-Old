package com.project.day1028.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.android.control.util.IntentUtil;
import com.csp.project.common.base.BaseActivity;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

/**
 * 生命周期
 * 1) "SDK 目录\sources\android-17\android\app\ActivityThread.java"
 * 2) 可见
 *   onCreate()
 *   TODO
 * 
 * 状态保存
 * 1) 保存状态
 *   a) 重写: onSaveInstanceState(Bundle outState)
 * 2) 恢复状态
 *   b) 重写: onRestoreInstanceState(Bundle savedInstanceState)
 * 3) 使用内存, 则将数据保存在上述方法的参数[Bundle]中
 * 4) 使用外存, 折将数据保存在文件
 * 
 * 4种启动模式
 * 1) 清单配置文件: <application> -> <activity> -> android:launchMode
 * 2) standard(标准模式，默认，每次启动activity都会重新创建新的对象)
 * 3) singletop(栈顶模式，仅当此activity处于栈顶时, 再次启动此activity才不会创建新的对象)
 * 4) singletask(单任务模式，此activity的实例在任务栈中只能有一份, 且中途调用显示该对象会导致其他栈顶元素被出栈)
 * 5) singleInstance(单实例模式，此activity对象会独享一个任务栈)
 * 
 * 亲族设置与启动模式
 * 0) 配置文件: <application> -> <activity> -> android:taskAffinity
 * 1) singleTask, 亲族与默认(application)不同, 则创建新栈, 且后续[Activity]也添加在该栈中
 * 2) singleInstance, 亲族与默认(同上)不同, 不影响
 * 3) singleTop, 亲族与默认(同上)不同, 导致
 *    i) 在未创建新栈(singleTask, singleInstance 导致)时, 不创建新栈
 *   ii) 在(singleInstance)创建新栈后, 且不运行[singleTask]的情况下, 创建新栈
 * 4) standard, 同[singleTop]
 * 5) singleTop 亲族与[standard]不同不会有影响
 * 
 * 其他组件启动Activity
 * 0) Intent 显示意图, 或隐式意图均可
 * 1) [Intent]对象执行: Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 * 2) 数据传输
 *   a) 数据绑定: target.putExtra("Info", "");
 *   b) 数据获取: getIntent().getStringExtra("Info")
 * 
 * 启动第三方应用程序
 * 1) 方法01: Intent 隐式意图
 * 2) 方法02: new Intent().setComponent(new ComponentName());
 * 
 * 其他, 清单配置文件配置: <application> -> <activity>
 * 1) 强制横屏: android:screenOrientation="landscape"
 * 2) 横竖屏不创建新对象: android:configChanges="orientation|screenSize"
 * 3) startActivityForResult(), 启动一个页面后，当该页面关闭时，调用[onActivityResult()]
 */
public class Activity1028_Activity extends Activity {
	public TextView		txt;
	public ImageView	img;
	public ListView		lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		// fgv.addData("查看生命周期(跳转页面)", "ActivityLifeCycle", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("使用内存保存状态(横屏竖屏切换)", "saveStateByMemory", false);
		fgv.addData("使用外存保存状态(横屏竖屏切换)", "saveStateByFile", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("4种启动模式与亲族设置", "launcher", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("其他组件启动Activity(singletask)(通讯)", "runByOtherComponent", false);
		fgv.addData("启动第三方应用程序", "launcherOtherApp", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("其他(强制横屏, 横竖屏不创建新对象)", "otherFun", false);
		fgv.addData("其他(ListActivity类使用等)", "otherFun02", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	public void ActivityLifeCycle() {
		IntentUtil.startActivity(this, BaseActivity.class);
	}

	public void saveStateByMemory() {
		IntentUtil.startActivity(this, com.project.day1028.activity.ActivitySaveStateByMemory.class);
	}

	public void saveStateByFile() {
		txt.setText("待补充");
	}

	public void launcher() {
		IntentUtil.startActivity(this, com.project.day1028.activity.ActivityLauchMode.class);
	}

	public void runByOtherComponent() {
		startService(new Intent(this, com.project.day1028.activity.ServiceToStartActivity.class));
	}

	public void launcherOtherApp() {
		String tip = "模拟启动第三方应用程序成功";

		// 方法01: 
		// Intent target = new Intent("my.android.app.ActivityLauchModeSingletask");
		// target.putExtra("Info", tip);
		// startActivity(target);

		// 方法02:
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("my.android.app", "my.android.app.day1028.activity.ActivityLauchModeSingletask"));
		intent.putExtra("Info", tip);
		startActivity(intent);
	}

	public void otherFun() {
		String tip = "清单配置文件: <application> -> <activity>";
		tip += "\n强制横屏: android:screenOrientation=\"landscape\"";
		tip += "\n横竖屏不创建新对象: android:configChanges=\"orientation|screenSize\"";
		txt.setText(tip);
	}
	
	public void otherFun02() {
		txt.setText("自行查阅");
	}


	/**
	 * 出场入场动画01
	 */
	@SuppressLint("NewApi")
	public void enterExitAnimation01() {
		ActivityOptions ao = ActivityOptions.makeCustomAnimation(this, R.anim.rotate_1017, R.anim.translate_1017);
		Bundle options = ao.toBundle();
		IntentUtil.startActivityByOption(this, com.project.hello.ActivityHello.class, null, options);
	}

	/**
	 * 出场入场动画02
	 */
	public void enterExitAnimation02() {
		IntentUtil.startActivity(this, com.project.hello.ActivityHello.class);
		overridePendingTransition(R.anim.rotate_1017, R.anim.translate_1017);
	}
}
