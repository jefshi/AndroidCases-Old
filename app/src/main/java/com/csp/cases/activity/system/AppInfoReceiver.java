package com.csp.cases.activity.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

import com.csp.library.java.fileSystem.FileUtil;
import com.csp.utils.android.log.LogCat;
import com.csp.utils.android.log.LogWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenshp on 2018/4/23.
 */

public class AppInfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        File file = Environment.getExternalStoragePublicDirectory("APK/Log/test.txt");
        LogWriter logWriter = new LogWriter(file);
        logWriter.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            LogCat.printStackTrace(e);
        }


        String tip = "AppInfoReceiver.onReceive = " + intent.getAction();
        Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
        LogCat.e(tip);

        file = Environment.getExternalStoragePublicDirectory("APK/test.txt");
        String path = file != null ? file.getAbsolutePath() : null;
        LogCat.e(path);

        try {
            FileUtil.write(System.currentTimeMillis() + "]: " + tip, file, null, true);
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        }

//        LogCat.e("AppInfoReceiver.onReceive = " + intent.getAction());

        logWriter.quit();
    }
}
