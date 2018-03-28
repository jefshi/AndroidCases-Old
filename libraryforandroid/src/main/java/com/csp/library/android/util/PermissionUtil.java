package com.csp.library.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Description: 动态权限获取
 * <p>Create Date: 2017/7/5 005
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class PermissionUtil {
	/**
	 * 请求权限: 若权限未获取, 则发起请求
	 *
	 * @param permissions 权限集合
	 * @param requestCode 请求码
	 */
	public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
		if (!checkPermissions(activity, permissions)) {
			ActivityCompat.requestPermissions(activity, permissions, requestCode);
		}
	}

	/**
	 * 是否拥有所有指定的权限
	 *
	 * @param permissions 权限集合
	 * @return true: 是
	 */
	public static boolean checkPermissions(Context context, String[] permissions) {
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}
}
