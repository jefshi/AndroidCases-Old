package com.csp.cases.activity.network.visit.retrolfit.phone;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.View;

/**
 * Class: AppUtil
 * Created by se7enTina on 2015/11/24.
 * Description: APP工具类
 */
@SuppressWarnings("ALL")
public class AppUtil {

    public static final String TAG = "AppUtil";

    /**
     * @param packageName 判断本地是否存在的包名
     */
    public static boolean isLocal(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * @param packageName 要打开应用的包名
     */
    public static void startApp(Activity activity, String packageName) {
        activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(packageName));
    }

    /**
     * 封装的打开应用单击事件
     */
    public static class StartAppOnClickListener implements View.OnClickListener {
        private Activity activity;
        private String packageName;
        public StartAppOnClickListener(Activity activity, String packageName) {
            this.activity = activity;
            this.packageName = packageName;
        }
        @Override
        public void onClick(View v) {
            startApp(activity, packageName);
        }

    }

    /**
     * 获取应用名称
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;

    }

    public static int getMyVersionCode(Context context) {
        return getMyPackageInfo(context).versionCode;
    }


    public static PackageInfo getMyPackageInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }

    /**
     * 获取应用签名信息
     */

    public static String getSingInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);

            return SHA1.encryptToSHA(packageInfo.signatures[0].toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 登录、注册获取指纹的方法
     *
     * @param context
     * @return 设备指纹
     */
    public static String getAppFingerprint(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(Build.SERIAL);
        stringBuilder.append(Build.ID);
        stringBuilder.append(tm.getDeviceId());
        stringBuilder.append("android");
        stringBuilder.append(Build.VERSION.RELEASE);
        stringBuilder.append(getApplicationName(context));
        stringBuilder.append(getSingInfo(context));
        stringBuilder.append(getMyVersionCode(context));
        return SHA1.encryptToSHA(stringBuilder.toString());
    }

    /**
     * SDK中获取设备指纹的方法
     *
     * @param context
     * @return 设备指纹
     */
    public static String getFingerprint(Context context) {
        PhoneInfo phoneInfo = new PhoneUtil().getPhoneInfo(context);
        if (phoneInfo == null) {
            return "";
        }
        StringBuffer hashData = new StringBuffer();
        hashData.append(phoneInfo.getPhoneHardwareInfo().getImei());
        hashData.append(PhoneUtil.getMacAddress(context));
        hashData.append(PhoneUtil.getUUID(context));
        String hash = MD5.hexdigest(hashData.toString());
        return Tools.stringToHex(hash);
    }
}


