package com.csp.cases.activity.component.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DynamicReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        StaticReceiver.onBroadcastReceive("--[动态广播]: 优先级 = 50", this, intent);
    }
}
