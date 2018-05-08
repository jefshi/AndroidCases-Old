package com.csp.cases.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.utils.android.log.LogCat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 进程案例
 * <p>Create Date: 2017/09/01
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ProcessActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo("当前进程内存信息", "processMemory", "查看当前DVM(Dalvik)进程内存信息，而不是操作系统的"));
        itemInfos.add(new ItemInfo("查看所有进程信息", "allProcessInfo", "查看当前系统所运行的所有进程的信息\nTODO 待补充"));
        itemInfos.add(new ItemInfo("执行命令行", "exeCMD", "执行命令行"));

        return itemInfos;
    }

    /**
     * 当前进程内存信息
     */
    private void processMemory() {
        // 进程最大可用内存(能够占用操作系统内存的最大值)
        // 进程已获得内存(已占用操作系统内存的大小)
        // 进程空闲内存(已获得的内存中未使用的内存大小)
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024;
        long freeMemory = Runtime.getRuntime().freeMemory() / 1024;

        String msg = "进程最大可用内存(能够占用操作系统内存的最大值)：" + maxMemory + " KB\n"
                + "进程已获得内存(已占用操作系统内存的大小)：" + totalMemory + " KB\n"
                + "进程空闲内存(已获得的内存中未使用的内存大小)：" + freeMemory + " KB\n";

        logError(msg);
    }

    /**
     * 查看所有进程信息
     */
    private void allProcessInfo() {
        //获得系统里正在运行的所有进程
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();

        // 获取进程的内存信息
        int[] pids = new int[processes.size()];
        for (int i = 0; i < pids.length; i++) {
            pids[i] = processes.get(i).pid;
        }
        Debug.MemoryInfo[] memoryInfo = manager.getProcessMemoryInfo(pids);

        // 显示部分进程信息
        String msg = "进程名    进程ID    用户ID    内存使用";
        for (int i = 0; i < processes.size(); i++) {
            ActivityManager.RunningAppProcessInfo process = processes.get(i);
            int pid = process.pid; // 进程ID号
            int uid = process.uid; // 用户ID
            String processName = process.processName; // 进程名
            int memory = memoryInfo[i].dalvikPrivateDirty;

            msg += "\nPS[" + i + "]：" + processName + ", " + pid + ", " + uid + ", " + memory + "KB";
        }
        logError(msg);
    }

    private void exeCMD() {
        // -c: 发包次数，-s: 发包大小(byte)，-i: 发包间隔(ms)
        String ping = "ping -c 4 -s 64 -i 1 www.baidu.com";

        Process process = null;
        BufferedReader successReader = null;
        BufferedReader errorReader = null;
        try {
            process = Runtime.getRuntime().exec(ping);
            InputStream in = process.getInputStream();
            OutputStream out = process.getOutputStream();

            // success and error
            successReader = new BufferedReader(new InputStreamReader(in));
            errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String lineStr;
            while ((lineStr = successReader.readLine()) != null)
                LogCat.e(lineStr);

            while ((lineStr = errorReader.readLine()) != null) {
                LogCat.e("ErrorStream: " + lineStr);
            }

        } catch (IOException e) {
            LogCat.printStackTrace(e);
        } catch (Exception e) {
            LogCat.printStackTrace(e);
        } finally {
            try {
                if (successReader != null) {
                    successReader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
            } catch (IOException e) {
                LogCat.printStackTrace(e);
            }

            if (process != null) {
                process.destroy();
            }
        }
    }
}
