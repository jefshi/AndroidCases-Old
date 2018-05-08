package com.project.day1025.providerResolver;

import java.util.ArrayList;
import java.util.List;

import com.common.android.control.util.LogUtil;
import com.common.android.model.util.CursorUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ContentProvider 清单配置文件注册
 * 1) 注册: <application> -> <provider>
 * 2) 唯一性: <provider> -> [android:authorities]属性
 * 
 * 自定义[ContentProvider]
 * 1) 编写[ContentProvider]继承类
 * 2) 在清单配置文件中注册<provider>
 * 
 * ContentResolver 访问
 * 1) 创建[ContentResolver]对象, cr = getContentResolver();
 * 2) 获取注册的[ContentProvider]的URI
 * 3) 调用[ContentProvider]方法, cr.getType(uri);
 * 
 * URI 结构: 三个部分
 * 1) 例如: Uri.parse("content://com.ljq.provider.personprovider/person/10")
 *   a) 参考网站: http://www.cnblogs.com/linjiqin/archive/2011/05/28/2061396.html
 * 2) 结构(三个部分): scheme、主机名或authorities(清单配置文件)、路径(可含ID)
 *   a) scheme, 安卓固定为[content://]
 *   b) 主机名或authorities, 用于唯一标识这个ContentProvider, 以便外部调用者定位
 *   c) 路径: 表示该[Provider]下某个数据库或某个偏好设置文件
 *   d) 路径/ID: 表示该数据库(偏好设置)的ID为指定的id
 * 3) 最大匹配, 即路径不对也能访问到[Provider]
 * 
 * CursorLoader(系统联系人)
 * 1) 底层实现异步加载
 * 2) Adapter: SimpleCursorAdapter等
 * 3) 获取LoaderManager, lm = getLoaderManager()
 * 4) 初始化LoaderManager(异步加载), lm.initLoader(id, bundle, new LoaderCallbacks<D>() {});
 *   a) onCreateLoader(): 创建CursorLoader
 *   b) onLoadFinished(): ...
 *   c) onLoaderReset():  ...
 *   d) 注意导入包的路径
 * 4) 重启LoaderManager(异步加载), lm.restartLoader(id, bundle, new LoaderCallbacks<D>() {});
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-28 08:23:29
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class Activity1025_ProviderResolver extends Activity {
	public SimpleCursorAdapter adapter;

	public TextView	txt;
	public ImageView	img;
	public ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("自定义[ContentProvider]访问", "contentProvider", false);
		fgv.addData("ContentResolver(系统音乐)", "ContentResolver", false);
		fgv.addData("CursorLoader(系统联系人)", "cursorLoader", false);
		fgv.addData("自定义[CursorLoader]", "customCursorLoader", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);
	}

	/**
	 * 自定义[ContentProvider]访问
	 * 见类说明
	 */
	public void contentProvider() {
		// ContentResolver
		ContentResolver cr = getContentResolver();

		// URI
		Uri uri = Uri.parse("content://com.app.csp/");

		// [Provider]方法
		cr.query(uri, null, null, null, null);

		txt.setText("详见LogCat、源代码");
	}

	/**
	 * ContentResolver(系统音乐)
	 * 见类说明
	 */
	public void contentResolver() {
		// ContentResolver
		ContentResolver cr = getContentResolver();

		// Uri
		// 具体数据库在: /data/data/com.android.providers.***/databases/***.db
		Uri uri = Media.EXTERNAL_CONTENT_URI;

		// [Provider]方法
		Cursor cursor = cr.query(uri, null, null, null, null);

		// 业务
		List<String> logList = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String title = CursorUtil.getString(cursor, Media.TITLE);
			String data = CursorUtil.getString(cursor, Media.DATA);
			logList.add(title + " : " + data);
		}

		lsv.setAdapter(new ArrayAdapter<String>(
				this, R.layout.template_txt, logList));
	}

	/**
	 * CursorLoader(系统联系人)
	 * 1) 底层实现异步加载
	 * 2) Adapter: SimpleCursorAdapter等
	 * 3) 获取LoaderManager, lm = getLoaderManager()
	 * 4) 初始化LoaderManager(异步加载), lm.initLoader(id, bundle, new LoaderCallbacks<D>() {});
	 *   a) onCreateLoader(): 创建CursorLoader
	 *   b) onLoadFinished(): ...
	 *   c) onLoaderReset():  ...
	 *   d) 注意导入包的路径
	 * 4) 重启LoaderManager(异步加载), lm.restartLoader(id, bundle, new LoaderCallbacks<D>() {});
	 */
	public void cursorLoader() {
		// LoaderManager
		LoaderManager lm = getLoaderManager();

		// 初始化LoaderManager
		int id = 0;
		Bundle bundle = new Bundle();
		lm.initLoader(id, bundle, getLoaderCalllbacks());

		// 重启LoaderManager
		// lm.restartLoader(id, bundle, new LoaderCallbacks<D>() {});

		// 业务
		adapter = new SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_2,
				null,
				new String[] { Phone.DISPLAY_NAME, Phone.NUMBER },
				new int[] { android.R.id.text1, android.R.id.text2 },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lsv.setAdapter(adapter);
	}

	public LoaderCallbacks<Cursor> getLoaderCalllbacks() {
		return new LoaderCallbacks<Cursor>() {

			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				LogUtil.logInfo("onCreateLoader()");

				CursorLoader cursorLoader = null;

				if (id == 0) {
					cursorLoader = new CursorLoader(
							Activity1025_ProviderResolver.this,
							Phone.CONTENT_URI,
							null,
							null,
							null,
							null);
				}
				return cursorLoader;
			}

			/**
			 * 异步加载数据后, 完成对主线程的数据更新
			 */
			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
				LogUtil.logInfo("onLoadFinished()");

				// 交换[Cursor], 并关闭旧[Cursor]
				adapter.changeCursor(cursor);
			}

			/**
			 * 主线程数据更新完毕后, 删除异步加载数据
			 */
			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				LogUtil.logInfo("onLoaderReset()");

				// 将[Cursor]关闭
				adapter.changeCursor(null);
			}
		};
	}

	/**
	 * 自定义[CursorLoader]
	 */
	public void customCursorLoader() {
		txt.setText("暂待补充");
	}
}
