package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil.loadimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 压缩图片获取[Bitmap]
 * 1) 存在问题01: 因为图片太大, 导致内存溢出
 * 2) 存在问题02: 有时不需要将图片完全显示
 * 3) 解决: 通过压缩图片来获取Bitmap
 * 4) 方法: DecodePiture.decodeSampledBitmapFromResource(String uri)
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-25 09:37:39
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class DecodePitureUtil {
	/**
	 * 加载图片
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	// public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
	public static Bitmap decodeSampledBitmapFromResource(String uri, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		BitmapFactory.decodeFile(uri, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(uri, options);
		// return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * 计算压缩比例
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	private static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}
}
