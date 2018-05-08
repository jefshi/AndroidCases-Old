package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil.loadimage;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * 获取[Bitmap]方法实现, loadBitmap(ImageView img, String uri)
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
 * cancelPotentialWork(ImageView img, String uri) 中止旧任务方法实现
 * 1) 通过[ImageView]获取[AsyncTask]
 * 2) 判定新旧[AsyncTask]是否相同, 不同则中止任务
 * 
 * [AsyncTask]实现
 * 1) 加载[Bitmap]后, 将[Bitmap]存至缓存
 * 2) 提供返回[uri]的方法
 * 3) 提供判定任务是否相同的方法
 * 
 * [Drawable]继承类实现
 * 1) 提供返回[AsyncTask]的方法
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-25 09:37:39
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ImageUtil {

	// ========================================
	// 单例模式
	// ========================================
	private ImageUtil() {
	}

	public static ImageUtil getInstance() {
		return Inner.instance;
	}

	static class Inner {
		public static ImageUtil instance = new ImageUtil();
	}

	// ========================================
	// 缓存
	// ========================================
	/**
	 * 
	 */
	private LruCache<String, Bitmap> mMemoryCache = null;

	public void Builder(int maxSize) {
		// 单位: MB
		mMemoryCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}

	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		Bitmap bitmap = null;
		if (mMemoryCache != null) {
			bitmap = mMemoryCache.get(key);
		}
		return bitmap;
	}

	/**
	 * 追加缓存数据
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (mMemoryCache != null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	// ========================================
	// 加载图片
	// ========================================
	/**
	 * 异步任务并发加载图片
	 * 1) 从缓存读取数据
	 * 2) 取消旧任务
	 * 3) 新建新任务
	 * @param img
	 * @param uri
	 */
	public void loadBitmap(ImageView img, String uri, int pos) {
		// LogUtil.logInfo(pos + " ： " + uri);

		// 从缓存读取数据
		Bitmap bitmap = getBitmapFromMemCache(uri);
		if (bitmap != null) {
			img.setImageBitmap(bitmap);
			return;
		}

		// 异步任务加载图片: 旧任务取消, 新任务追加
		if (cancelPotentialWork(img, uri)) {
			BitmapWorkerTask bwTask = new BitmapWorkerTask(img);
			AsyncDrawable aDrawable = new AsyncDrawable(bwTask, null, null);
			img.setImageDrawable(aDrawable);
			bwTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri, 200, 400);
		}
	}

	/**
	 * cancelPotentialWork(ImageView img, String uri) 中止旧任务方法实现
	 * 1) 通过[ImageView]获取[AsyncTask]
	 * 2) 判定新旧[AsyncTask]是否相同, 不同则中止任务
	 * @param img [ImageView]对象
	 * @param uri 新任务的[uri]
	 * @return true: 任务不再执行, false: 任务继续执行
	 */
	public boolean cancelPotentialWork(ImageView img, String uri) {
		BitmapWorkerTask bwTask = getBitmapWorkerTask(img);
		if (bwTask != null) {
			if (!bwTask.isSame(uri)) {
				bwTask.cancel(true);
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * 通过[ImageView]获取[AsyncTask]
	 * @param img
	 * @return
	 */
	public BitmapWorkerTask getBitmapWorkerTask(ImageView img) {
		final Drawable drawable = img == null ? null : img.getDrawable();
		if (drawable instanceof AsyncDrawable) {
			return ((AsyncDrawable) drawable).getBitmapWorkerTask();
		}

		return null;
	}
}
