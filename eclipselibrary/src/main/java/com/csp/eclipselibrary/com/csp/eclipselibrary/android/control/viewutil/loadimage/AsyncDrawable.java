package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil.loadimage;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * [Drawable]继承类实现
 * 1) 提供返回[AsyncTask]的方法
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-26 08:19:12
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class AsyncDrawable extends BitmapDrawable {
	private WeakReference<BitmapWorkerTask> wBitmapWorkerTask;
	
	public AsyncDrawable(final BitmapWorkerTask task, Resources res, Bitmap defaultBitmap) {
		super(res, defaultBitmap); // 设置默认图片显示
		wBitmapWorkerTask = new WeakReference<BitmapWorkerTask>(task);
	}

	public BitmapWorkerTask getBitmapWorkerTask() {
		return wBitmapWorkerTask.get();
	}
}
