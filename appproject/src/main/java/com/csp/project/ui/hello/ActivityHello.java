package com.project.hello;

import com.project.R;
import com.project.common.grideview.IntentGridView;
import com.project.day0928.exam.Activity0928_Exam;
import com.project.day0929.login.Activity0929_Login;
import com.project.day1001.view.Activity1001_View;
import com.project.day1027.broadcast.Activity1027_Broadcast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

@SuppressLint("NewApi")
public class ActivityHello extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * activity -> phonewindow -> view
		 *   1) 底层根据资源ID, 获取对应的资源文件
		 *   2) 底层借助[pull], 解析XML, 获取元素内容
		 *   3) 底层根据反射构建元素对象
		 *   4) 底层将元素对象添加到activity窗口
		 *   5) 注意: 该方法不会导致页面显示, 该方法只是相当于[new]一个对象
		 *   6) 注意: Activity执行完: onStart()、onResume()
		 */
		setContentView(R.layout.activity_option_none);
	}
}
