package com.project.tools.piture;

import java.util.List;

import com.common.android.control.viewutil.loadimage.ImageUtil;
import com.project.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;

/**
 * 多张图片的异步并发加载, 并使用缓存
 * 
 * Module 01: 设置[ListView]与[Adapter]
 * 1) 调用[ListView.setAdapter()]
 * 2) [Adapter.getView()]的[ImageView]内容设置
 *   a) 通过[uri]获取[Bitmap], 从而使用[ImageView.setImageBitmap()]达到目的
 *   b) 通过方法[loadBitmap(ImageView img, String uri)]完成
 * 
 * Module 02: Bitmap 获取
 * 1) 存在问题01: 因为图片太大, 导致内存溢出
 * 2) 存在问题02: 有时不需要将图片完全显示
 * 3) 解决: 通过压缩图片来获取Bitmap
 * 4) 方法: DecodePiture.decodeSampledBitmapFromResource(String uri)
 * 
 * Module 03: loadBitmap(ImageView img, String uri) 方法实现
 * 1) 缓存中已存在所需数据, 则直接设置[ImageView]内容, 并返回
 * 
 * 2) 缓存中不存在所需数据, 则由于以下问题, 所以采用[工作线程执行并行异步任务]来加载图片
 *   a) 加载图片是个耗时过程
 *   b) 需要加载多张图片
 *   
 * 3) 由于以下原因, 所以需要使用绑定, 从而[ImageView]能间接找到[AsyncTask]
 *   a) 问题01: 滑动[ListView], 导致部分[ImageView]上未完成的旧任务[AsyncTask]需要中止, 并赋予新任务
 *   b) 问题02: 需要得到旧任务才能中止任务, 然而[ImageView]无法直接取得其所拥有的旧任务
 *   c) 解决: 引入第三方对象, 该对象含[AsyncTask]实例域, 并绑定到[ImageView], 从而[ImageView]能间接找到[AsyncTask]
 *   d) 可用方法: [ImageView]存在绑定方法[setTag(), setImageDrawable()], 所以可以继承[Object]、[Drawable](例)
 * 
 * 4) 旧任务不同于新任务, 中止旧任务
 *   a) 方法: cancelPotentialWork(ImageView img, String uri)
 *   
 * 5) 新任务
 *   a) 新建异步任务[AsyncTask]
 *   b) 新建[Drawable]继承类
 *   c) [ImageView.setImageDrawable()]
 *   d) 执行异步任务[AsyncTask]
 * 
 * Module 04: cancelPotentialWork(ImageView img, String uri) 中止旧任务方法实现
 * 1) 通过[ImageView]获取[AsyncTask]
 * 2) 通过[AsyncTask]获取旧[uri]
 * 3) 对比新旧[uri], 判定是否中止旧任务
 * 
 * Module 05: [AsyncTask]实现
 * 1) 加载[Bitmap]后, 将[Bitmap]存至缓存
 * 2) 提供返回[uri]的方法
 * 
 * Module 06: [Drawable]继承类实现
 * 1) 提供返回[AsyncTask]的方法
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-25 09:37:39
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ActivityPiture extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_none);
		
		// 设置缓存大小
		ActivityManager aManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		int memory = aManager.getMemoryClass();
		int maxMemorySize = memory * 1024 * 1024 / 8; // 1/8 的内存大小
		ImageUtil.getInstance().Builder(maxMemorySize);
		
		// 配置[GridView]数据
		List<Animal> animals = new AnimalProvider().getAnimals();
		AdapterPiture adapter = new AdapterPiture(this, animals);
		
		GridView grv = (GridView) findViewById(R.id.grvNones);
		grv.setNumColumns(1);
		grv.setAdapter(adapter);
	}

//	/**
//	 * Adapter, Animal
//	 */
//	class AnimalAdapter extends AdapterPiture<Animal> {
//
//		public AnimalAdapter(Context context, List<Animal> object) {
//			super(context, object);
//		}
//		
//		@Override
//		public void setViewEvent(AdapterPiture<Animal>.ViewHolder vHolder) {
//		}
//
//		@Override
//		public void setViewContent(AdapterPiture<Animal>.ViewHolder vHolder, Animal object) {
//			
//		}
//	}
}
