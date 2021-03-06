package com.csp.cases.activity.component.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.csp.utils.android.log.LogCat;

@SuppressWarnings("UnusedAssignment")
public class StaticReceiver extends BroadcastReceiver {
    public static String PERMISSION = "android.cases.custom.permission";
    public static String KEY_EXPLAIN = "KEY_EXPLAIN";
    public static String KEY_INTERCEPT = "KEY_INTERCEPT";

    public static String RECEIVER_ACTION = "android.cases.receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        onBroadcastReceive("--[静态广播]: 优先级 = 100", this, intent);
    }

    /**
     * 广播处理
     */
    public static void onBroadcastReceive(String explain, BroadcastReceiver receiver, Intent intent) {
        String action = intent.getAction();
        String toUri = intent.toUri(Intent.URI_INTENT_SCHEME);
        String resultData = receiver.getResultData();
        Uri uri = intent.getData();
        String uriString = intent.getDataString();
        String msg = explain + "\n--[Action]: " + action
                + "\n--[toUri]: " + toUri
                + "\n--[ResultData]: " + resultData
                + "\n--[Uri]: " + String.valueOf(uri)
                + "\n--[UriString]: " + uriString;

        boolean keyIntercept = false;
        String key = null;
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            key = bundle.getString(KEY_EXPLAIN);
            keyIntercept = bundle.getBoolean(KEY_INTERCEPT);
            msg += "\n--[拦截]: " + keyIntercept + "\n--[说明]: " + key;
        }
        LogCat.e(msg);

        // 拦截广播
        if (keyIntercept) {
            receiver.abortBroadcast();
        }
    }
}
