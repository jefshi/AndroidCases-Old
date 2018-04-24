package com.csp.cases.activity.component;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.csp.cases.MainActivity;
import com.csp.cases.R;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.base.activity.BaseGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 状态栏通知案例
 * <p>Create Date: 2017/07/25
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class NotificationActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("状态栏通知：通知", "notification01", ""));
        items.add(new ItemInfo("状态栏通知：自定义View", "notification02", ""));

        return items;
    }

    /**
     * 状态栏通知：普通
     */
    private void notification01() {
        // 延迟意图, 用于跳转页面
        PendingIntent intent = PendingIntent.getActivity(
                this,
                100,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        // 生成Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher) // 必须项，实际显示应用图标
                .setContentTitle("Title")
                .setContentText("Content")
                .setProgress(100, 50, false)
                .setContentIntent(intent)
                .setTicker("有一条新消息");

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        // notification.flags = Notification.FLAG_NO_CLEAR; // 只能通过停止服务或卸载软件来关闭通知

        // Notification 管理器
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0, notification); // [ID]重复会导致通知被覆盖
        // manager.cancel(0); // 通知关闭
    }

    /**
     * 状态栏通知：自定义View
     */
    private void notification02() {
        // 自定义View
        RemoteViews rmv = new RemoteViews(getPackageName(), R.layout.item_notification);
        rmv.setImageViewResource(R.id.imgTitle, R.mipmap.item_card);
        rmv.setTextViewText(R.id.txtTitle, "Title");
        rmv.setTextViewText(R.id.txtContent, "Content");

        // 获取通知，其余见[状态栏通知：普通]
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher) // 必须项，实际显示应用图标
                .setContent(rmv)
                .setTicker("有一条新消息");

        // 通知创建
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }
}