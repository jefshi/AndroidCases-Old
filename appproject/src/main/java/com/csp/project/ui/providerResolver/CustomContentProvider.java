package com.project.day1025.providerResolver;

import com.common.android.control.util.LogUtil;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class CustomContentProvider extends ContentProvider {

	@Override
	public boolean onCreate() {
		LogUtil.logInfo("onCreate()");
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		LogUtil.logInfo("query()");
		return null;
	}

	@Override
	public String getType(Uri uri) {
		LogUtil.logInfo("getType()");
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		LogUtil.logInfo("insert()");
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		LogUtil.logInfo("delete()");
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		LogUtil.logInfo("update()");
		return 0;
	}

}
