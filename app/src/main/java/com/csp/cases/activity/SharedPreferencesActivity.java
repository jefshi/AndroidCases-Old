package com.csp.cases.activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.constants.SystemConstant;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 偏好设置案例
 * <p>Create Date: 2017/8/9
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class SharedPreferencesActivity extends BaseGridActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("写入偏好设置", "writePreference", ""));
		items.add(new ItemInfo("读取偏好设置", "readPreference", ""));

		return items;
	}

	/**
	 * 写入偏好设置
	 */
	private void writePreference() {
		// 方法01: 文件为[指定文件名.xml]
		SharedPreferences preferences = getSharedPreferences(
				SystemConstant.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);

		// 方法02: 文件为[Activity类名_preference.xml]
		// SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);

		// 方法03: 文件为[包名_preference.xml]
		// SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

		// 获取写入器
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("user", "chenshiping");
		editor.putInt("password", 123456);
		editor.putBoolean("isRemember", false);
		boolean result = editor.commit(); // 立即磁盘写入，成功返回true
		// editor.apply(); // 提交到内存，后异步写入磁盘。频繁写入的情况下效率更高，无返回值

		setDescription("偏好设置写入成功: " + result);
	}

	/**
	 * 读取偏好设置
	 */
	private void readPreference() {
		SharedPreferences preferences = getSharedPreferences(
				SystemConstant.SHARED_PREFERENCES_NAME,
				Context.MODE_PRIVATE);

		String user = preferences.getString("user", "");
		int password = preferences.getInt("password", 0);
		boolean isRemember = preferences.getBoolean("isRemember", false);

		String msg = "user = " + user
				+ "\npassword = " + password
				+ "\nisRemember = " + isRemember;
		LogCat.e(msg);
	}
}
