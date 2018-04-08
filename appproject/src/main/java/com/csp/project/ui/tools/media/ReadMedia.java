package com.project.tools.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * 获取手机多媒体信息
 * @version 1.0
 * @author tarena
 * <p style='font-weight:bold'>Date:</p> 2016年11月24日 下午5:00:23
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ReadMedia {
	public static Uri	AUDIOURI	= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;	//音频
	public static Uri	IMAGEURI	= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;	//图片
	public static Uri	VEDIOURI	= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;	//视频

	public static void getAudioInfo(Context context, String tag) {
		Cursor cursor = context.getContentResolver().query(AUDIOURI, null, null, null, null);

		cursor.moveToNext();
		showDetail(cursor, tag);

		Log.d(tag, "Count: " + cursor.getCount());
		while (cursor.moveToNext()) {
			Log.d(tag, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
		}
		cursor.close();
	}

	public static void getImageInfo(Context context, String tag) {
		Cursor cursor = context.getContentResolver().query(IMAGEURI, null, null, null, null);

		cursor.moveToNext();
		showDetail(cursor, tag);

		Log.d(tag, "Count: " + cursor.getCount());
		while (cursor.moveToNext()) {
			Log.d(tag, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
		}
		cursor.close();
	}

	public static void getVedioInfo(Context context, String tag) {
		Cursor cursor = context.getContentResolver().query(VEDIOURI, null, null, null, null);

		cursor.moveToNext();
		showDetail(cursor, tag);

		Log.d(tag, "Count: " + cursor.getCount());
		while (cursor.moveToNext()) {
			Log.d(tag, cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
		}
		cursor.close();
	}

	/**
	 * 显示详细信息
	 * @param cursor
	 * @param tag
	 */
	public static void showDetail(Cursor cursor, String tag) {
		String[] columnNames = cursor.getColumnNames();

		for (int i = 0; i < columnNames.length; i++) {
			Log.d(tag, columnNames[i] + " : " + cursor.getString(cursor.getColumnIndex(columnNames[i])));
		}
	}
}
