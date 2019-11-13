package com.project.day1028.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.csp.project.common.base.BaseActivity;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

public class ActivityLauchMode extends BaseActivity {
	public TextView	txt;
	public ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("standard", "runStandard", false);
		fgv.addData("singletop", "runSingletop", false);
		fgv.addData("singletask", "runSingletask", false);
		fgv.addData("singleInstance", "runSingleInstance", false);
		fgv.addData("--------------------", "", false);
		fgv.addData("其他组件启动Activity(singletask)(通讯)", "runByOtherComponent", false);
		fgv.addData("--------------------", "", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 业务
		String logStr = getLogString("");

		String tip = "\n";
		tip += "\n4种启动模式";
		tip += "\n清单配置文件: <application> -> <activity> -> \"android:launchMode\"";
		tip += "\nstandard(标准模式，默认，每次启动activity都会重新创建新的对象)";
		tip += "\nsingletop(栈顶模式，仅当此activity处于栈顶时, 再次启动此activity才不会创建新的对象)";
		tip += "\nsingletask(单任务模式，此activity的实例在任务栈中只能有一份, 且中途调用显示该对象会导致其他栈顶元素被出栈)";
		tip += "\nsingleInstance(单实例模式，此activity对象会独享一个任务栈)";
		tip += "\n";
		tip += "\n其他详见笔记, 如上述模式的应用场景, [Service]调用[Activity]注意事项等";
		tip += "\n";
		tip += "\n亲族设置与启动模式说明, 下述执行修改代码验证";
		tip += "\n0) 配置文件: <application> -> <activity> -> android:taskAffinity";
		tip += "\n1) singleTask, 亲族与默认(application)不同, 则创建新栈, 且后续[Activity]也添加在该栈中";
		tip += "\n2) singleInstance, 亲族与默认(同上)不同, 不影响";
		tip += "\n3) singleTop, 亲族与默认(同上)不同, 导致";
		tip += "\n   i) 在未创建新栈(singleTask, singleInstance 导致)时, 不创建新栈";
		tip += "\n  ii) 在(singleInstance)创建新栈后, 且不运行[singleTask]的情况下, 创建新栈";
		tip += "\n4) standard, 同[singleTop]";
		tip += "\n5) singleTop 亲族与[standard]不同不会有影响";

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		txt.setText("请查看LogCat:\n" + logStr + tip);
	}

}
