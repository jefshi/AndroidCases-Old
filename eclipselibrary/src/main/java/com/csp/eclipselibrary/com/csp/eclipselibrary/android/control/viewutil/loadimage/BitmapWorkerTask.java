package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil.loadimage;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * [AsyncTask]实现
 * 1) 加载[Bitmap]后, 将[Bitmap]存至缓存
 * 2) 任务执行完毕后, 对[ImageView]前, 必检查
 *   a) 任务是否已取消
 *   b) 获得的[Bitmap]、绑定的[ImageView]是否为空
 *   c) 由于存在以下情况, 须检测[ImageView]上绑定的任务是否与当前任务是否不一致
 *      i) 主线程取消任务, 并重新绑定新任务, 而旧任务已经执行完毕[doInBackground()执行完毕]
 *     ii) 主线程在执行[onPostExecute()]方法前, 旧任务尚未执行取消命令
 *   d) 上述条件任何一个为肯定, 则不执行[ImageView]设置操作
 * 3) 提供返回[uri]的方法
 *   a) 方法: String getUri()
 * 4) 提供判定任务是否相同的方法
 *   a) 方法: isSame(String uri)
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-26 08:19:12
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class BitmapWorkerTask extends AsyncTask<Object, Integer, Bitmap> {
	private WeakReference<ImageView>	wImageView;
	private String						uri;

	public BitmapWorkerTask(ImageView img) {
		wImageView = new WeakReference<ImageView>(img);
	}

	@Override
	protected Bitmap doInBackground(Object... params) {
		uri = (String) params[0];
		int reqWidth = (Integer) params[1];
		int reqHeight = (Integer) params[2];

		Bitmap bitmap = DecodePitureUtil.decodeSampledBitmapFromResource(uri, reqWidth, reqHeight);
		ImageUtil.getInstance().addBitmapToMemoryCache(uri, bitmap);
		
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			return;
		}
		
		if (bitmap != null && wImageView != null) {
			ImageView img = wImageView.get();
			BitmapWorkerTask bwTask = ImageUtil.getInstance().getBitmapWorkerTask(img);
			if (this == bwTask && img != null) {
				wImageView.get().setImageBitmap(bitmap);
			}
		}
	}

	public String getUri() {
		return uri;
	}
	
	/**
	 * 判定任务是否相同的方法
	 * @param uri 任务关键字
	 * @return true, 相同; false, 不同
	 */
	public boolean isSame(String uri) {
		return uri.equals(this.uri) ? true : false;
	}
}
