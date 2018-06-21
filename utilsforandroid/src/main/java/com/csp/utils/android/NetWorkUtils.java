package com.csp.utils.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import com.csp.utils.android.log.LogCat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description: 网络工具类
 * <p>Create Date: 2018/04/11
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class NetworkUtils {

    /**
     * 获取  NetworkInfo 对象
     *
     * @param context context
     * @return NetworkInfo 对象
     */
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cManager == null ? null : cManager.getActiveNetworkInfo();
    }

    /**
     * 网络是否连通，注意不监测 Portal 验证
     *
     * @return true: 已连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cManager == null)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        State wifiState = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        return wifiState != null && State.CONNECTED == wifiState
                || (mobileState != null && State.CONNECTED == mobileState);
    }

    /**
     * 当前 wifi 是否需要 Portal 验证
     * ["http://g.cn/generate_204"]网址专门用于监测 Portal 验证
     * 该网址返回：204，请求成功，无 Portal 验证
     * 该网址返回：200，请求成功，需要 Portal 验证
     *
     * @return true: 需要验证, false: 不需要，或无网络
     */
    public static boolean isPortalWifi() {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://g.cn/generate_204");
            connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setUseCaches(false);
            inputStream = connection.getInputStream();
            return connection.getResponseCode() != 204;
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LogCat.printStackTrace(e);
                }

            if (connection != null)
                connection.disconnect();
        }

        return false;
    }
}
