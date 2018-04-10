package com.csp.cases.activity.permissions;

import android.support.annotation.NonNull;

import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.permissions.PermissionUtil;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * 所有权限列表
     */
    private final String[] permissions = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.SYSTEM_ALERT_WINDOW
    };

    /**
     * 必要权限列表
     */
    private final HashMap<String, String> mustPermissions = new HashMap<>();
    {
        mustPermissions.put(android.Manifest.permission.READ_PHONE_STATE, "读取本机识别码");
        mustPermissions.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储");
        mustPermissions.put(android.Manifest.permission.SYSTEM_ALERT_WINDOW, "悬浮窗");
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("动态获取权限", "requestAllPermissions", "权限已经获取或禁止的，就不会出现请求对话框"));

        return items;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            PermissionUtil.requestMustPermissions(this, mustPermissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        PermissionUtil.requestMustPermissions(this, mustPermissions, PERMISSIONS_REQUEST_CODE);
    }

    // 动态获取权限
    private void requestAllPermissions() {
        PermissionUtil.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
    }
}
