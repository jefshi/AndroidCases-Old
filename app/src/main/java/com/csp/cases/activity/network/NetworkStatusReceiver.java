package com.csp.cases.activity.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.csp.utils.android.NetWorkUtils;

/**
 * Description: 网络连通性监听
 * <p>Create Date: 2018/04/11
 * <p>Modify Date: 无
 * <p>
 * 权限：
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class NetworkStatusReceiver extends BroadcastReceiver {
    private static boolean connected = false;
    private static boolean enablaWifi = false;
    private static boolean wifi = false;
    private static boolean mobile = false;

    /**
     * 注册广播监听器
     * WifiManager.WIFI_STATE_CHANGED_ACTION：
     * 监听 wifi 是否启用，与 wifi 连接无关
     * <p>
     * WifiManager.NETWORK_STATE_CHANGED_ACTION：
     * 监听 wifi 是否有效，仅当 wifi 启用时，才能接受到该广播
     * <p>
     * ConnectivityManager.CONNECTIVITY_ACTION：
     * 监听网络连通性变化，包括 wifi 和移动数据的变化。但无法监测 Portal 认证
     */
    public static NetworkStatusReceiver registerReceiver(Activity activity) {
        NetworkStatusReceiver receiver = new NetworkStatusReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(receiver, filter);

        return receiver;
    }

    public static void unregisterReceiver(Activity activity, NetworkStatusReceiver receiver) {
        activity.unregisterReceiver(receiver);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
            enablaWifi = WifiManager.WIFI_STATE_ENABLED
                    == intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
        }

        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (parcelableExtra instanceof NetworkInfo) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                wifi = state == NetworkInfo.State.CONNECTED;
            }
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            NetworkInfo networkInfo = NetWorkUtils.getActiveNetworkInfo(context);
            if (networkInfo == null || !networkInfo.isConnected()) {
                wifi = false;
                mobile = false;
                connected = false;
                return;
            }

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                wifi = true;
                connected = true;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                mobile = true;
                connected = true;
            }
        }
    }
}
