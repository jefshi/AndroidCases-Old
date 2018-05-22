package com.csp.cases.activity.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.csp.utils.android.NetWorkUtils;
import com.csp.utils.android.log.LogCat;

/**
 * Description: 网络连通性监听
 * <p>Create Date: 2018/04/11
 * <p>Modify Date: 2018/05/22
 * <p>
 * 权限：
 * <uses-permission android:name="android.permission.INTERNET" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *
 * @author csp
 * @version 1.0.1
 * @since AndroidCases 1.0.0
 */
public class NetworkStatusReceiver extends BroadcastReceiver {
    private static final String NETWORK_CONNECTED_TEST = "NETWORK_CONNECTED_TEST";
    private static boolean connected;

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
    public static NetworkStatusReceiver registerReceiver(Context context) {
        NetworkStatusReceiver receiver = new NetworkStatusReceiver();

        // TODO 7.0 更换网络连通监测方式
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(NETWORK_CONNECTED_TEST);
        context.registerReceiver(receiver, filter);
        return receiver;
    }

    /**
     * 解除注册广播监听器
     */
    public static void unregisterReceiver(Context context, BroadcastReceiver receiver) {
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    /**
     * 发送检测网络连通性广播
     */
    public static void sendCheckoutBroadcast(Context context) {
        context.sendBroadcast(
                new Intent(NetworkStatusReceiver.NETWORK_CONNECTED_TEST));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        // 网路连通性监测
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)
                || NETWORK_CONNECTED_TEST.equals(action)) {
            new Thread(() -> {
                // TODO Portal wifi 已登录检测
                connected = NetWorkUtils.isConnected(context)
                        && !NetWorkUtils.isPortalWifi();

                // 发送网络状态变化广播
                // EventBus.getDefault().post(Constant.Network.CONNECTED_CHANGE);
                LogCat.e("网络状态变化 = " + connected);
            }).start();
        }

        // TODO Wifi 以及 移动数据 监测（未整理）
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
            boolean connected = NetWorkUtils.isConnected(context);
            if (networkInfo != null && connected) {
                int type = networkInfo.getType();
                wifi = type == ConnectivityManager.TYPE_WIFI;
                mobile = type == ConnectivityManager.TYPE_MOBILE;
            } else {
                wifi = mobile = false;
            }
        }
    }
}
