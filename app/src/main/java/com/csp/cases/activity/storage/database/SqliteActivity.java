package com.csp.cases.activity.storage.database;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 数据库案例
 * <p>Create Date: 2017/8/30
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class SqliteActivity extends BaseListActivity {
	private final String DB_NAME = "AndroidCases.db";

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("[Sqlite]使用：无封装的简单使用", "operateDatabases", "DEBUG：不能将数据库创建到[/data/data/xxx/databases/]下"));
		itemInfos.add(new ItemInfo("[Sqlite]使用：含事务操作", "operateDBWithTransaction", ""));
		itemInfos.add(new ItemInfo("使用[SimpleCursorAdapter]查询结果", "showInListView", "注意01：查询的数据库表必须含[_id]\n注意02:：[SQLiteDatabase][Cursor]不能执行[close()]"));
		itemInfos.add(new ItemInfo("[SQLiteOpenHelper]应用", "sQLiteHelper", ""));

		return itemInfos;
	}

	/**
	 * [Sqlite]使用：无封装的简单使用
	 */
	@SuppressWarnings({"ResultOfMethodCallIgnored", "UnusedAssignment"})
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void operateDatabases() {
		SQLiteDatabase sdb = null;
		try {
			String path = getFilesDir().getAbsolutePath() + '/' + DB_NAME; // 数据库路径
			String sql = null;
			String msg = "数据库路径：" + path + '\n';

			// 创建/连接数据库
			sdb = SQLiteDatabase.openOrCreateDatabase(path, null, null);
			// sdb = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

			// 删除数据库表
			sql = "DROP TABLE IF EXISTS contacts";
			sdb.execSQL(sql);

			// 创建数据库表
			sql = " CREATE TABLE IF NOT EXISTS contacts ("
					+ "   _id integer not null primary key autoincrement, "
					+ "   phone text not null,"
					+ "   name text,"
					+ "   created text not null"
					+ " )";
			sdb.execSQL(sql);

			// 增删改查方法01：执行[SQL]语句
			sql = "INSERT INTO contacts VALUES (null, '1111', 'ABC', '2016-01-01')";
			sdb.execSQL(sql);

			sql = "UPDATE contacts SET name = ?, created = ? WHERE phone LIKE ?";
			sdb.execSQL(sql, new Object[]{"ABC1111", "2017-01-01", "%1%"});

			sql = "SELECT * FROM contacts WHERE phone LIKE ? ORDER BY _id";
			Cursor cursor = sdb.rawQuery(sql, new String[]{"%%"});
			msg += "\n更新数据后，查询01：\n" + showCursor(cursor);
			cursor.close();

			sql = "DELETE FROM contacts WHERE phone LIKE ?";
			sdb.execSQL(sql, new Object[]{"%1%"});

			// 增删改查方法02：使用封装方法，均会返回操作影响的行数
			ContentValues values = new ContentValues();
			values.put("phone", "2222");
			values.put("name", "DEF");
			values.put("created", "2016-02-01");
			sdb.insert("contacts", null, values); // 返回影响的行数

			values = new ContentValues();
			values.put("name", "DEF2222");
			values.put("created", "2017-02-01");
			sdb.update("contacts", values, "phone LIKE ?", new String[]{"%2%"});

			cursor = sdb.query(
					"contacts",
					new String[]{"_id", "phone", "name", "created"},
					"phone like ?",
					new String[]{"%%"},
					null,
					null,
					"_id");
			msg += "\n更新数据后，查询02：\n";
			msg += showCursor(cursor);
			cursor.close();

			sdb.delete("contacts", "phone LIKE ?", new String[]{"%2%"});

			// 删除数据库
			new File(path).delete();
			// deleteDatabase(DB_NAME);

			logError(msg);
		} finally {
			// 关闭数据库
			if (sdb != null)
				sdb.close();
		}
	}

	/**
	 * 遍历游标并返回查询结果Log
	 */
	private String showCursor(Cursor cursor) {
		String msg = "";
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String created = cursor.getString(cursor.getColumnIndex("created"));

			msg += "ROW: " + id + ", " + phone + ", " + name + ", " + created + '\n';
		}
		return msg;
	}

	/**
	 * [Sqlite]使用：含事务操作
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void operateDBWithTransaction() {
		String path = getFilesDir().getAbsolutePath() + '/' + DB_NAME;
		SQLiteDatabase sdb = null;

		try {
			sdb = SQLiteDatabase.openOrCreateDatabase(path, null, null);

			sdb.beginTransaction();

			String sql = "DROP TABLE IF EXISTS contacts";
			sdb.execSQL(sql);

			sdb.setTransactionSuccessful();
		} finally {
			if (sdb != null) {
				sdb.endTransaction();
				sdb.close();
			}
			new File(path).delete();
		}
		LogCat.e("事务执行成功");
	}

	/**
	 * 使用[SimpleCursorAdapter]展示查询数据结果
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void showInListView() {
		// 制造数据源
		getDatabasePath(DB_NAME).delete();
		SQLiteHelper helper = new SQLiteHelper(this, DB_NAME);
		SQLiteDatabase sdb = helper.getWritableDatabase();
		Cursor cursor = sdb.rawQuery("SELECT * FROM contacts", null);

		// [SimpleCursorAdapter]使用
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_2,
				cursor,
				new String[]{"phone", "name"},
				new int[]{android.R.id.text1, android.R.id.text2},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER); // Cursor变化时, adapter自动刷新

		ListView lsv = new ListView(this);
		lsv.setLayoutParams(new ListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		lsv.setAdapter(adapter);
		lfrItem.addView(lsv);
	}

	/**
	 * [SQLiteOpenHelper]应用
	 */
	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void sQLiteHelper() {
		getDatabasePath(DB_NAME).delete();

		SQLiteHelper helper = new SQLiteHelper(this, DB_NAME, null, 1);
		SQLiteDatabase sdb = helper.getWritableDatabase();
		sdb.close();

		helper = new SQLiteHelper(this, DB_NAME, null, 2);
		sdb = helper.getWritableDatabase();
		sdb.close();
	}
}
