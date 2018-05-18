package com.csp.utils.android.permissions;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.csp.utils.android.SettingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Description: 动态权限获取
 * <p>Create Date: 2017/7/5 005
 * <p>Modify Date: 2018/03/27
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class PermissionUtil {
    /**
     * 权限分类，将权限分为手动权限，和自动权限
     *
     * @param permissions 所有权限
     * @param auto        自动权限：通过系统自身的提示框引导用户设置权限
     * @param manual      手动权限：需要通过自定义的提示框引导用户设置权限
     */
    private static void classifyPermissions(Collection<String> permissions, Collection<String> auto, Collection<String> manual) {
        for (String permission : permissions) {
            if (permission == null)
                continue;

            switch (permission) {
                case Manifest.permission.SYSTEM_ALERT_WINDOW:
                    manual.add(permission);
                    break;
                default:
                    auto.add(permission);
            }
        }
    }

    /**
     * 检测自动权限
     *
     * @param permissions 权限集合
     * @return 未获取权限集合
     */
    public static Collection<String> checkAutoPermissions(Context context, Collection<String> permissions) {
        Collection<String> noPower = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                noPower.add(permission);
            }
        }
        return noPower;
    }

    /**
     * 检测手动权限
     *
     * @param permissions 权限集合
     * @return 未获取权限集合
     */
    public static Collection<String> checkManualPermissions(Context context, Collection<String> permissions) {
        Collection<String> noPower = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case Manifest.permission.SYSTEM_ALERT_WINDOW:
                    if (!checkSYSTEM_ALERT_WINDOW(context)) {
                        noPower.add(permission);
                    }
                    break;
            }
        }
        return noPower;
    }

    /**
     * 检测必要权限，权限未完全获取，弹出对话框引导用户设置权限
     *
     * @param activity    activity
     * @param permissions 必要权限，key：权限名称，value：权限说明
     */
    public static void requestMustPermissions(Activity activity, HashMap<String, String> permissions, int requestCode) {
        // 权限分类
        Collection<String> manual = new ArrayList<>();
        Collection<String> auto = new ArrayList<>();
        classifyPermissions(permissions.keySet(), auto, manual);

        // 取得未获取的权限
        Collection<String> noPower = checkAutoPermissions(activity, auto);
        noPower.addAll(checkManualPermissions(activity, manual));

        HashMap<String, String> noPowerMap = new HashMap<>();
        for (String permission : noPower)
            noPowerMap.put(permission, permissions.get(permission));

        // 弹出提示框
        if (noPowerMap.size() > 0) {
            new PermissionDialog(activity)
                    .setPermissions(noPowerMap)
                    .setRequestCode(requestCode)
                    .show();
        }
    }

    /**
     * 请求权限: 若权限未获取, 则自动发起权限请求（通过系统提示框）
     *
     * @param activity    activity
     * @param permissions 权限集合
     * @param requestCode 请求码
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        // 权限分类
        Collection<String> manual = new ArrayList<>();
        Collection<String> auto = new ArrayList<>();
        classifyPermissions(Arrays.asList(permissions), auto, manual);

        if (checkAutoPermissions(activity, auto).size() > 0)
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        else if (checkManualPermissions(activity, manual).size() > 0)
            requestManualPermissions(activity, manual, requestCode);
    }

    /**
     * 请求手动权限
     *
     * @param activity    activity
     * @param permissions 权限集合
     * @param requestCode 请求码
     */
    public static void requestManualPermissions(Activity activity, Collection<String> permissions, int requestCode) {
        // 权限检测
        int index = -1;
        String[] permissionArray = new String[permissions.size()];
        int[] grantResults = new int[permissions.size()];
        for (String permission : permissions) {
            ++index;
            if (null == permission)
                continue;

            switch (permission) {
                case Manifest.permission.SYSTEM_ALERT_WINDOW:
                    grantResults[index] = checkSYSTEM_ALERT_WINDOW(activity) ?
                            PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
                    break;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.onRequestPermissionsResult(requestCode, permissionArray, grantResults);
        }
    }

    /**
     * 检测悬浮窗权限
     *
     * @return true: 已授权
     */
    public static boolean checkSYSTEM_ALERT_WINDOW(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        }
        // TODO 21 - 23 权限说明
        return true;
    }

    /**
     * 跳转所有权限设置界面
     *
     * @param context     context
     * @param permissions 权限集合
     * @param requestCode 请求码
     */
    public static void openPermissionsSetting(Context context, Collection<String> permissions, int requestCode) {
        if (permissions == null || permissions.size() == 0)
            return;

        if (permissions.size() == 1) {
            String permission = permissions.iterator().next();
            if (android.Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission))
                SettingUtils.startFloatingPermissionSetting(context, requestCode);

            return;
        }

        SettingUtils.startAppInformationSetting(context, requestCode);
    }
}
