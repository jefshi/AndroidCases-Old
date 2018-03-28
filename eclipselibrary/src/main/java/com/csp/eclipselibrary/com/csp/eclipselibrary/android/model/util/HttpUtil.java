package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class HttpUtil {
	// 自定义错误码
	public static final int		VISIT_FAIL	= -100000;	// 网络访问失败
	public static final int		OTHER_FAIL	= -200000;	// 其他失败

	// Volley.newRequestQueue(MyApp.getContext());
	public static RequestQueue	queue		= null;

	/**
	 * 获取[ImageLoader]对象
	 */
	public static ImageLoader getImageLoader() {
		ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache() {
			int							cacheSize	= 0;
			LruCache<String, Bitmap>	cache		= null;
			{
				cacheSize = (int) Runtime.getRuntime().maxMemory() / 8;
				cache = new LruCache<String, Bitmap>(cacheSize) {
					@Override
					protected int sizeOf(String key, Bitmap bitmap) {
						return bitmap.getHeight() * bitmap.getRowBytes();
					}
				};
			}

			@Override
			public void putBitmap(String key, Bitmap bitmap) {
				cache.put(key, bitmap);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return cache.get(key);
			}
		});
		return loader;
	}
}
