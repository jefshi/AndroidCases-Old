package com.csp.utils.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.csp.utils.android.log.LogCat;

import java.security.MessageDigest;

/**
 * 当前应用信息
 * Created by csp on 2017/04/09.
 * Modified by csp on 2018/06/22.
 *
 * @version 1.0.3
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AppInfoUtils {
    /**
     * 获取指定应用的 PackageInfo
     * <p>
     * {@link PackageManager#getPackageInfo(String, int)}
     */
    public static PackageInfo getPackageInfo(Context context, String packageName, int flags) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(packageName, flags);
        } catch (NameNotFoundException e) {
            LogCat.printStackTrace(e);
            return null;
        }
    }

    /**
     * @see #getPackageInfo(Context, String, int)
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        return getPackageInfo(context, packageName, 0);
    }

    /**
     * @see #getPackageInfo(Context, String, int)
     */
    public static PackageInfo getPackageInfo(Context context) {
        return getPackageInfo(context, context.getPackageName(), 0);
    }

    /**
     * 获取指定应用的版本号
     *
     * @return 指定应用的版本号, -1: 应用不存在
     */
    public static int getVersionCode(Context context, String packageName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo == null ? -1 : packageInfo.versionCode;
    }

    /**
     * @see #getVersionCode(Context, String)
     */
    public static int getVersionCode(Context context) {
        return getVersionCode(context, context.getPackageName());
    }

    /**
     * 判断指定应用是否是最新版
     *
     * @param latestVersionCode 最新版本号
     * @return true: 不是最新版，或者指定应用不存在
     */
    public static boolean isNotLatestVersion(Context context, String packageName, int latestVersionCode) {
        return latestVersionCode > getVersionCode(context, packageName);
    }

    /**
     * 获取指定应用的版本信息
     *
     * @return 指定应用的版本号, null: 应用不存在
     */
    public static String getVersionName(Context context, String packageName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo == null ? null : packageInfo.versionName;
    }

    /**
     * @see #getVersionName(Context, String)
     */
    public static String getVersionName(Context context) {
        return getVersionName(context, context.getPackageName());
    }

    /**
     * 获取应用最近安装（或更新）时间
     *
     * @param packageInfo 应用信息
     * @return 应用最近安装（或更新）时间
     */
    public static long getLastInstallTime(PackageInfo packageInfo) {
        return Math.max(packageInfo.firstInstallTime, packageInfo.lastUpdateTime);
    }

    /**
     * TODO 待验证获取应用签名
     *
     * @param context     context
     * @param packageName 包名
     * @return 返回应用的签名
     */
    public static String getSign(Context context, String packageName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        return packageInfo == null
                ? null : hexdigest(packageInfo.signatures[0].toByteArray());
    }

    /**
     * TODO 将签名字符串转换成需要的32位签名
     *
     * @param paramArrayOfByte 签名byte数组
     * @return 32位签名字符串
     */
    private static String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97,
                98, 99, 100, 101, 102};
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0; ; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception e) {
            LogCat.printStackTrace(e);
            return null;
        }
    }
}
