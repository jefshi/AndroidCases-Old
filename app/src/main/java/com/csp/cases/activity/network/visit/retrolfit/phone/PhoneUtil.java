package com.csp.cases.activity.network.visit.retrolfit.phone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.util.UUID;

/**
 * Class: PhoneUtil
 * Created by se7enTina on 2015/11/24.
 * Description: 获取手机设备信息
 */
@SuppressWarnings("ALL")
public class PhoneUtil {

	private PhoneInfo phoneInfo = new PhoneInfo();
	private PhoneHardwareInfo phoneHardwareInfo = new PhoneHardwareInfo();
	private PhoneSoftwareInfo phoneSoftwareInfo = new PhoneSoftwareInfo();

	private PhoneHardwareInfo getPhoneHardInfo(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		phoneHardwareInfo.setTerminalType(Build.MODEL);
		phoneHardwareInfo.setImei(tm.getDeviceId());
		phoneHardwareInfo.setAndroidId(Build.ID);
		phoneHardwareInfo.setTerminalSn(Build.SERIAL);
		phoneHardwareInfo.setMac(getMacAddress(context));
		phoneHardwareInfo.setIsSupportNfc(hasNfcHce(context) == false ? "0" : "1");
		return phoneHardwareInfo;
	}

	private PhoneSoftwareInfo getPhoneSoftInfo(Context context) {

		phoneSoftwareInfo.setMobileAppName(AppUtil.getApplicationName(context));
		phoneSoftwareInfo.setMobileAppSign(AppUtil.getSingInfo(context));
		phoneSoftwareInfo.setMobileAppVersion(AppUtil.getMyVersionCode(context) + "");
		phoneSoftwareInfo.setMobileOsType("android");
		phoneSoftwareInfo.setMobileOsVersion(Build.VERSION.RELEASE);

		return phoneSoftwareInfo;
	}

	public PhoneInfo getPhoneInfo(final Context context) {
		int grantPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
		if (grantPermission != PackageManager.PERMISSION_GRANTED) {
			return null;
		}

		phoneInfo.setPhoneHardwareInfo(getPhoneHardInfo(context));
		phoneInfo.setPhoneSoftwareInfo(getPhoneSoftInfo(context));
		return phoneInfo;
	}

	/**
	 * 手机是否异常
	 *
	 * @param context
	 * @return true ,异常；false，正常
	 */
	public static boolean isDeviceException(Context context) {
		if (true) {
			return false;
		}
		return isRooted() || isAdb(context);
	}

	/**
	 * 手机设备是否支持HCE：Android 4.4以上，支持NFC功能
	 *
	 * @return true: 支持
	 */
	public static boolean isSupportHCE(Context context) {
		if (true) {
			return true;
		}
		boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		return hasNfcHce(context) && isKitKat;
	}

	/**
	 * 判断当前手机是否有ROOT权限
	 *
	 * @return
	 */
	public static boolean isRooted() {
		boolean bool = false;
		try {
			if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
				bool = false;
			} else {
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	/**
	 * 检查手机是否支持HCE
	 *
	 * @return
	 */
	public static boolean hasNfcHce(Context context) {
		return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION);
	}

	/**
	 * 获取手机mac地址
	 * 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 检查手机是否处于调试状态
	 *
	 * @return
	 */
	public static boolean isAdb(Context context) {
		return (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
	}

	public static String getUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}
}