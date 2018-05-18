package com.csp.utils.android.log;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 日志文件打印
 * <p>Create Date: 2017/7/14
 * <p>Modify Date: 2017/09/18
 *
 * @author csp
 * @version 1.0.1
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings("unused")
public class LogWriter extends Thread {
    private boolean isWrite = true; // true: 允许写日志
    private int level; // 日志优先级
    private File file;

    private final static int EOF = -1;
    private final static int BUFFER_LENGTH = 8192; // 8 KB

    public LogWriter(File file) {
        this(file, Log.ERROR);
    }

    public LogWriter(File file, int level) {
        this.file = file;
        this.level = level;
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void run() {
        Process process = null;
        try {
            // 获取[Log]流
            String cmd = getLogcatCmd();
            process = Runtime.getRuntime().exec(cmd);
            InputStream is = process.getInputStream();

            // 写入文件, 并在写入完成后, 清空[Log]
            file.getParentFile().mkdirs();
            write(is, file);
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * 日志记录是否有效
     */
    public boolean isValid() {
        return file.exists() && isWrite;
    }

    /**
     * 停止日志编写
     */
    public void quit() {
        isWrite = false;
    }

    /**
     * 获取[log]的命令行
     */
    private String getLogcatCmd() {
        String cmd = "logcat -v time *:";
        switch (level) {
            case Log.ERROR:
                return cmd + 'E';
            case Log.WARN:
                return cmd + 'W';
            case Log.INFO:
                return cmd + 'I';
            case Log.DEBUG:
                return cmd + 'D';
            default:
                return cmd + 'V';
        }
    }

    /**
     * 文件写入(二进制方式)
     *
     * @param is   输入流
     * @param dest 目标文件
     * @return true: 操作成功
     * @throws IOException if an I/O error occurs.
     */
    @SuppressWarnings("UnusedAssignment")
    private boolean write(InputStream is, File dest) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = null;
        try {
            int len = 0;
            byte[] bArr = new byte[BUFFER_LENGTH];
            bos = new BufferedOutputStream(new FileOutputStream(dest, true));
            while (isValid() && (len = bis.read(bArr)) != EOF) {
                bos.write(bArr, 0, len);
                bos.flush();
            }
        } finally {
            bis.close();
            if (bos != null) {
                bos.close();
            }
        }
        return true;
    }
}
