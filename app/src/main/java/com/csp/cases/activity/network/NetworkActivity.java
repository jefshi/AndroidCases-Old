package com.csp.cases.activity.network;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build;

import com.csp.cases.activity.network.downfile.DownFileActivity;
import com.csp.cases.activity.network.jobscheduler.MyJobService;
import com.csp.cases.activity.network.ping.PingUtil;
import com.csp.cases.activity.network.visit.NetworkVisitActivity;
import com.csp.cases.activity.network.vpn.VpnActivity;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.NetWorkUtils;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 网络访问案例
 * <p>Create Date: 2017/09/07
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class NetworkActivity extends BaseListActivity {
    private NetworkStatusReceiver receiver;

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            NetworkStatusReceiver.unregisterReceiver(this, receiver);
            receiver = null;
        }

        super.onDestroy();
    }

    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();

        itemInfos.add(new ItemInfo("网络连通检测", "networkConnected", "包含检测 wifi 的 Portal 验证"));
        itemInfos.add(new ItemInfo("当前有效 NetworkInfo", "networkInfo", "API 23 及以上才能使用"));
        itemInfos.add(new ItemInfo("wifi 的 Portal 验证", "isPortalWifi", "当前 wifi 是否需要 Portal 验证"));
        itemInfos.add(new ItemInfo("ping", "ping", "无法监测出 wifi 的 Portal 验证"));
        itemInfos.add(new ItemInfo("-----", "", ""));

        itemInfos.add(new ItemInfo("广播监听网络状态", "networkStatusReceiver", "API 24 及以上，请使用 JobScheduler 或 GCMNetworkManager"));
        itemInfos.add(new ItemInfo("ConnectivityManager", "networkListence", ""));
        itemInfos.add(new ItemInfo("JobScheduler", "scheduleJob", ""));
        itemInfos.add(new ItemInfo("-----", "", ""));

        itemInfos.add(new ItemInfo("VPN", VpnActivity.class, "VPN 使用案例"));
        itemInfos.add(new ItemInfo("网络访问", NetworkVisitActivity.class, "各种网络访问"));
        itemInfos.add(new ItemInfo("下载文件", DownFileActivity.class, "各种网络访问"));

        return itemInfos;
    }

    public static final int MY_BACKGROUND_JOB = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob() {
        JobScheduler js =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(MY_BACKGROUND_JOB,
                new ComponentName(this, MyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();
        js.schedule(job);

    }

    /**
     * 网络连通检测
     */
    private void networkConnected() {
        new Thread(() -> {
            boolean connected = NetWorkUtils.isConnected(NetworkActivity.this)
                    && !NetWorkUtils.isPortalWifi();

            LogCat.e("网络连通检测：" + connected);
        }).start();
    }

    /**
     * NetworkInfo
     */
    private void networkInfo() {
        NetworkInfo networkInfo = NetWorkUtils.getActiveNetworkInfo(this);
        LogCat.e("NetworkInfo：", networkInfo);

        if (networkInfo == null)
            return;

        LogCat.e("NetworkInfo.isAvailable()[不监测 Portal 验证]：", networkInfo.isAvailable());
        LogCat.e("NetworkInfo.isConnected()[不监测 Portal 验证]：", networkInfo.isConnected());
        LogCat.e("NetworkInfo.getTypeName()：", networkInfo.getTypeName());
        LogCat.e("NetworkInfo.getSubtypeName()：", networkInfo.getSubtypeName());
        LogCat.e("NetworkInfo.getState()：", networkInfo.getState());
        LogCat.e("NetworkInfo.getDetailedState().name()：", networkInfo.getDetailedState().name());
        LogCat.e("NetworkInfo.getExtraInfo()：", networkInfo.getExtraInfo());
        LogCat.e("NetworkInfo.getType()：", networkInfo.getType());
    }

    /**
     * 当前 wifi 是否需要 Portal 验证
     */
    private void isPortalWifi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogCat.e("当前 wifi 是否需要 Portal 验证：",
                        NetWorkUtils.isPortalWifi());
            }
        }).start();
    }

    /**
     * ping
     */
    private void ping() {
        LogCat.e(PingUtil.ping(4, "www.baidu.com"));
    }

    /**
     * 广播监听网络状态
     * TODO 接收广播待补充
     */
    private void networkStatusReceiver() {
        if (receiver == null) {
            receiver = NetworkStatusReceiver.registerReceiver(this);
            NetworkStatusReceiver.sendCheckoutBroadcast(this);
        } else {
            NetworkStatusReceiver.unregisterReceiver(this, receiver);
            receiver = null;
        }
    }
}
