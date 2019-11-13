package com.project.day1121.widget;

import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.appwidget.AppWidgetProvider;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 创建小组件步骤
 * 1) res/layout: 小组件布局文件
 * 2) res/xml: 小组件描述对象, 定义小组件原生数据信息
 *   a) 根节点:  <appwidget-provider>
 *   b) 更新频率: updatePeriodMillis
 *   c) 初始布局: initialLayout
 *   d) 组件高宽: minHeight, minWidth
 * 3) java 代码
 *   a) extends AppWidgetProvider
 *   b) 重写: onUpdate(), 小组件首次创建时运行
 *   c) 重写: onReceive(), 广播接收器
 * 4) 清单配置文件中进行配置: <receiver>
 *   <receiver android:name="com.chen.tiantian.control.widget.SmallWidget" >
 *       <intent-filter>
 *           <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
 *       </intent-filter>
 *   
 *       <meta-data
 *           android:name="android.appwidget.provider"
 *           android:resource="@xml/widget_info_small" />
 *   </receiver>
 *   a) 上述除了[<receiver> android:name][android:resource]需要变更外, 其余均不可变更
 *   b) <intent-filter> 中追加额外接收的广播Action
 */
public class Activity1121_Widget extends Activity {
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
		fgv.addData("创建小组件步骤", "creatWidget", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	public void creatWidget() {
		txt.setText("请从天天播放器上寻找代码, ");
	}
}
