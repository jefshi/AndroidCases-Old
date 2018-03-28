package com.csp.eclipselibrary.com.csp.eclipselibrary.android.model.util;

import com.common.java.util.StringUtil;

import android.database.Cursor;

public class CursorUtil {
	public static int getInt(Cursor cursor, String columnName) {
		return cursor.getInt(cursor.getColumnIndex(columnName));
	}

	public static long getLong(Cursor cursor, String columnName) {
		return cursor.getLong(cursor.getColumnIndex(columnName));
	}

	public static String getString(Cursor cursor, String columnName) {
		return cursor.getString(cursor.getColumnIndex(columnName));
	}

	/**
	 * 该字段值必须是"true", 或"false"
	 */
	public static boolean getBoolean(Cursor cursor, String columnName) {
		String result = cursor.getString(cursor.getColumnIndex(columnName));
		return "TRUE".equals(StringUtil.toUpperCase(result)) ? true : false;
	}
}
