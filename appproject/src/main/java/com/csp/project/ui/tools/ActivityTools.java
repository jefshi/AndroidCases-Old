package com.project.tools;

import com.common.android.control.util.IntentUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;
import com.project.tools.hello.ActivityToolsHello;
import com.project.tools.media.ReadMedia;
import com.project.tools.piture.ActivityPiture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityTools extends Activity {
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
		fgv.addData("欢迎界面(版本更新)", "helloPage", false);
		fgv.addData("多张图片的异步并发加载(使用缓存)", "showImageByCacheAsync", false);
		fgv.addData("读取手机上的多媒体文件", "getMediaInfo", false);
		fgv.addData("扫描多媒体(详见源代码)", "scanMedia", false);
		fgv.addData("ListView 分组(SectionIndexer)", "showListViewGroup", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	public void helloPage() {
		IntentUtil.startActivity(this, ActivityToolsHello.class);
	}

	public void showImageByCacheAsync() {
		IntentUtil.startActivity(this, ActivityPiture.class);
	}

	public void getMediaInfo() {
		ReadMedia.getAudioInfo(this, "info");
		ReadMedia.getImageInfo(this, "info");
		ReadMedia.getVedioInfo(this, "info");
	}

	public void scanMedia() {
		txt.setText("(详见源代码)");

		//		ScanMedia.scanFileAsync(this, "/");
		//		ScanMedia.scanDirAsync(this, "/");
		//		ScanMedia.scanMainThrea(this);
		//		ScanMedia.scanAllAsync01(this);
		//		ScanMedia.scanAllAsync02(this);
	}
	
	public void showListViewGroup() {
		txt.setText("待补充, 见大众点评项目的城市选择分组");
	}

}
