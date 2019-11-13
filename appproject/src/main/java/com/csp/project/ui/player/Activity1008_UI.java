package com.project.day1008.player;

import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity1008_UI extends Activity {
	private TextView	txt;
	private ImageView	img;
	private ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[FunctionGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("ViewStub", "connectDB", true);
		fgv.addData("ListView", "closeDB", false);
		fgv.addData("GridView", "closeDB", false);
		fgv.addData("Spinner", "closeDB", false);
		fgv.addData("AutoCompleteTextView", "closeDB", false);
		fgv.addData("SearchView", "closeDB", false);
		fgv.addData("[ListView]的[Adapter]编写", "closeDB", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}
}
