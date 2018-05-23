package com.csp.cases.activity.component.broadcast;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.base.activity.BaseGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 广播案例
 * <p>Create Date: 2017/7/19
 * <p>Modify Date: 无
 * <p>
 * 官方文档：https://developer.android.com/guide/components/broadcasts
 * <p>
 * <p>发送有序广播(广播接收器优先级)
 * <p>1) 区间: [-1000, 1000]
 * <p>2) 静态未设置优先级, 则按排列顺序作为优先级, 越靠前, 优先级越高
 * <p>3) 发送(无序/普通)广播
 * <p>  a) 动态广播 > 静态广播
 * <p>  b) 数字越小, 优先级越低
 * <p>4) 发送有序广播
 * <p>  a) 不看动态静态, 数字越小, 优先级越低
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class BroadcastActivity extends BaseGridActivity {
    BroadcastReceiver receiver = null;
    BroadcastReceiver localReceiver = null;

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("静态注册广播", "", "静态注册见清单配置文件"));
        items.add(new ItemInfo("动态注册广播(包含本地广播)", "registerReceiver", "本地广播只能用于应用内通信"));
        items.add(new ItemInfo("注销广播(包含本地广播)", "unregisterReceiver", ""));
        items.add(new ItemInfo("发送普通广播（含权限限制）", "onSendBroadcast", "该广播接收器追加自定义权限，详情见Manifest.xml"));
        items.add(new ItemInfo("发送有序广播（含权限限制）", "onSendOrderedBroadcast", ""));
        items.add(new ItemInfo("发送本地广播（含权限限制）", "onSendLocalBroadcast", "本地广播只能本地广播接收器才能接收，同时本地广播接收器也只接收本地广播"));
        return items;
    }

    @Override
    public void initViewContent() {
        setDescription("注01: 静态注册见清单配置文件");

        super.initViewContent();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver();
        super.onDestroy();
    }

    /**
     * 动态注册广播(包含本地广播)
     */
    private void registerReceiver() {
        if (receiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(StaticReceiver.RECEIVER_ACTION);
            filter.setPriority(50);

            receiver = new DynamicReceiver();
            registerReceiver(receiver, filter);
        }
        if (localReceiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(StaticReceiver.RECEIVER_ACTION);
            filter.setPriority(150);

            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            localReceiver = new LocalReceiver();
            lbm.registerReceiver(localReceiver, filter);
        }
    }

    /**
     * 注销广播(包含本地广播)
     */
    private void unregisterReceiver() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        if (localReceiver != null) {
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            lbm.unregisterReceiver(localReceiver);
            localReceiver = null;
        }
    }

    /**
     * 发送普通广播
     */
    private void onSendBroadcast() {
        Intent intent = new Intent();
        intent.setAction(StaticReceiver.RECEIVER_ACTION);
        intent.putExtra(StaticReceiver.KEY_EXPLAIN, "发送普通广播");
        // sendBroadcast(intent);
        sendBroadcast(intent, StaticReceiver.PERMISSION);
    }

    /** q``
     * 发送有序广播
     */
    private void onSendOrderedBroadcast() {
        sendOrderedBroadcast(StaticReceiver.RECEIVER_ACTION, "第一次有序广播", false);
        sendOrderedBroadcast(StaticReceiver.RECEIVER_ACTION, "第二次有序广播", true);
    }

    /**
     * 发送有序广播
     */
    private void sendOrderedBroadcast(String action, String explain, boolean intercept) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(StaticReceiver.KEY_EXPLAIN, explain);
        intent.putExtra(StaticReceiver.KEY_INTERCEPT, intercept);
        // sendOrderedBroadcast(intent, null);
        sendOrderedBroadcast(intent, StaticReceiver.PERMISSION);
    }

    /**
     * 发送本地广播
     */
    private void onSendLocalBroadcast() {
        Intent intent = new Intent();
        intent.setAction(StaticReceiver.RECEIVER_ACTION);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.sendBroadcast(intent);
    }
}
