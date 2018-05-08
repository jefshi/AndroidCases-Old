package com.csp.cases.activity.component.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        StaticReceiver.onBroadcastReceive("--[本地广播]: 优先级 = 150", this, intent);
    }
}
