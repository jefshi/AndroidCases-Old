package com.csp.utils.android.phone;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.csp.library.java.ByteUtil;
import com.csp.utils.android.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * Description: 当前应用信息
 * <p>Create Date: 2018/04/10
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings("unused")
public class PhoneHardwareUtils {

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return Android 版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager tManager =
                (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);

        return tManager == null ? null : tManager.getDeviceId();
    }

//    public static boolean isFeatures() {
//        return Build.FINGERPRINT.startsWith("generic")
//                || Build.FINGERPRINT.toLowerCase().contains("vbox")
//                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
//                || Build.MODEL.contains("google_sdk")
//                || Build.MODEL.contains("Emulator")
//                || Build.MODEL.contains("Android SDK built for x86")
//                || Build.MANUFACTURER.contains("Genymotion")
//                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
//                || "google_sdk".equals(Build.PRODUCT);
//    }

    /**
     * @return true: 存在蓝牙
     */
    public static boolean existBlueTooth() {
        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        return bluetooth != null && !TextUtils.isEmpty(bluetooth.getName());
    }

    /**
     * @return true: 存在光传感器（可用于判断是否是模拟器）
     */
    public static Boolean existLightSensor() {
        Context context = Utils.getAppContext();
        if (context == null)
            return false;

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        return manager != null && manager.getDefaultSensor(Sensor.TYPE_LIGHT) != null;
    }

    /**
     * @return 获取 CPU 信息
     */
    @NonNull
    public static String getCpuInfo() {
        InputStream is = null;
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder cmd = new ProcessBuilder(args);

            Process process = cmd.start();
            is = process.getInputStream();
            byte[] bytes = ByteUtil.toBytes(is);
            return new String(bytes, "utf-8");
        } catch (IOException ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        return "";
    }
}
