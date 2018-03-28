package com.csp.cases.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Android 6.0以上 动态权限获取案例
 * <p>Create Date: 2017/8/9
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class PermissionActivity extends BaseListActivity {
	private final int PERMISSIONS_REQUEST_CODE = 1000;

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("动态获取权限", "requestAllPermissions", "权限已经获取或禁止的，就不会出现请求对话框"));

		return items;
	}

	private void requestAllPermissions() {
		// 获取权限
		String[] permissions = {
				Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.READ_PHONE_STATE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE
		};
		PermissionUtil.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			String[] mustPermissions = {
					Manifest.permission.READ_PHONE_STATE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE
			};

			// 必要权限未取得，则弹出对话框引导用户设置权限
			if (!PermissionUtil.checkPermissions(this, mustPermissions)) {
				showPermissionDialog();
			}
		}
	}

	/**
	 * 必要权限未取得，则弹出对话框引导用户设置权限
	 */
	private void showPermissionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("本应用需要获取以下权限: 读取本机识别码、SD卡存储");
		builder.setPositiveButton("手动授权", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(Uri.parse("package:" + getPackageName()));
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}
}
