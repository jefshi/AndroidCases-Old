package com.csp.cases.activity;

import android.content.Intent;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.SettingUtils;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;


/**
 * Description: 设置功能案例
 * <p>Create Date: 2018/04/13
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class SettingActivity extends BaseGridActivity {
    private final static int PERMISSIONS_REQUEST_CODE = 1200;

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo("Wifi 设置界面", "skipWifi", "跳转到 Wifi 设置界面"));
        itemInfos.add(new ItemInfo("应用信息设置界面", "skipAppInformation", "跳转到应用信息界面"));
        itemInfos.add(new ItemInfo("悬浮窗权限设置界面", "skipFloatingPermission", "跳转到悬浮窗权限设置界面"));

        return itemInfos;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                LogCat.e("onActivityResult.RESULT_OK");
            else
                LogCat.e("onActivityResult.NOT_RESULT_OK");
        }
    }

    private void skipWifi() {
        SettingUtils.skipWifi(this, PERMISSIONS_REQUEST_CODE);
    }

    private void skipAllPermission() {
        SettingUtils.skipAppInformation(this, PERMISSIONS_REQUEST_CODE);
    }

    private void skipFloatingPermission() {
        SettingUtils.skipFloatingPermission(this, PERMISSIONS_REQUEST_CODE);
    }
}
