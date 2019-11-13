package com.project.tools.media;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

public class ScanMedia {
	/**
	 * 扫描指定文件
	 */
	public static void scanFileAsync(Context context, String filePath) {
		Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		scanIntent.setData(Uri.fromFile(new File(filePath)));
		context.sendBroadcast(scanIntent);
	}

	/**
	 * 扫描指定目录
	 * 
	 * 这种扫描方式中，由于扫描工作是在MediaScanner服务中进行的，因此不会阻塞当前程序进程。
	 * 当扫描大量媒体文件且实时性要求不高的情况下，适合使用该扫描方式。
	 */
	public static final String ACTION_MEDIA_SCANNER_SCAN_DIR = "android.intent.action.MEDIA_SCANNER_SCAN_DIR";

	public static void scanDirAsync(Context context, String dir) {

		Intent scanIntent = new Intent(ACTION_MEDIA_SCANNER_SCAN_DIR);
		scanIntent.setData(Uri.fromFile(new File(dir)));
		context.sendBroadcast(scanIntent);
	}

	/**
	 * 通过MediaScanner提供的API接口，扫描媒体文件。
	 * 
	 * 这种扫描媒体文件的方式是同步的，扫描工作将会阻塞当前的程序进程。当扫描少量文件， 且要求立即获取扫描结果的情况下，适合使用该扫描方式。
	 * 在扫描媒体文件前，程序应该根据终端当前的语言环境正确设置MediaScanner的语言环境设置，避免产生编解码的错误
	 */
	public static void scanMainThrea(Context ctx) {
		//		MediaScanner scanner = new ScanMedia(ctx);
		//		Locale locale = ctx.getResources().getConfiguration().locale;
		//		String language = locale.getLanguage();
		//		String country = locale.getCountry();
		//		scanner.setLocale(language + "_" + country);
	}

	/**
	 * 扫描全盘(SD卡),相当于重启或者重新加载外置SD卡时扫描外置SD卡
	 * 版本小于等于4.4
	 */
	public static void scanAllAsync01(Context context) {
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
		intent.setData(Uri.parse("file://" + Environment.getExternalStorageDirectory()));
		context.sendBroadcast(intent);
		// }
	}

	/**
	 * 扫描全盘(SD卡),相当于重启或者重新加载外置SD卡时扫描外置SD卡
	 * 版本大于4.4
	 */
	public static void scanAllAsync02(Context context) {
		// if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		intent.setData(Uri.parse("file://" + Environment.getExternalStorageDirectory()));
		context.sendBroadcast(intent);
		// }
	}
}
