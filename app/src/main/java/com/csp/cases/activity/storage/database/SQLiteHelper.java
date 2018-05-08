package com.csp.cases.activity.storage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.csp.utils.android.log.LogCat;

public class SQLiteHelper extends SQLiteOpenHelper {
	private static int version = 0;

	public SQLiteHelper(Context context, String databasesName, CursorFactory factory, int version) {
		super(context, databasesName, factory, version);
		SQLiteHelper.version = version;
	}

	public SQLiteHelper(Context context, String databasesName) {
		this(context, databasesName, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		LogCat.e("void onCreate(SQLiteDatabase db)");

		String sql = " CREATE TABLE IF NOT EXISTS contacts ("
				+ "   _id integer not null primary key autoincrement, "
				+ "   phone text not null,"
				+ "   name text,"
				+ "   created text not null"
				+ " )";
		db.execSQL(sql);

		sql = "INSERT INTO contacts VALUES (null, '1111', 'ABC', '2016-01-01'),(null, '2222', 'DEF', '2016-02-01')";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogCat.e("void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)");
	}
}
