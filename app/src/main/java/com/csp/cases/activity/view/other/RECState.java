package com.csp.cases.activity.view.other;

import android.os.SystemClock;


/**
 * Created by luffy on 2018/1/8.
 * 录制状态操作中，时序最高
 */
public class RECState {
    private static boolean started; // 启动录制，从准备录制算起
    private static boolean isREC;
    private static boolean isPAUSE;
    private static boolean isPure; // true: 无痕录制状态
    private static long beginClock; // 录制起始时刻
    private static long pauseTime; // 停止时间
    private static long pauseClock; // 暂停时刻，用于计算中暂停时间

    public static boolean isIsPure() {
        return isPure;
    }

    public static void setIsPure(boolean isPure) {
        RECState.isPure = isPure;
    }

    public static boolean isIsREC() {
        return isREC;
    }

    public static boolean isIsPAUSE() {
        return isPAUSE;
    }

    public static boolean isStarted() {
        return started;
    }

    /**
     * 获取录制时间
     */
    public static long getRecordTime() {
        return isREC ? getNowClock() - beginClock - pauseTime - (isPAUSE ? getNowClock() - pauseClock : 0) : 0;
    }

    private static long getNowClock() {
        return SystemClock.elapsedRealtime();
    }

    /**
     * 强制将状态改为准备录制
     */
    public static void readyRecordByForce() {
        started = true;
        isREC = false;
        isPAUSE = false;
        beginClock = 0;
        pauseTime = 0;
        pauseClock = 0;
    }

    /**
     * 强制将状态改为开始录制
     */
    public static void startRecordByForce() {
        started = true;
        isREC = true;
        isPAUSE = false;
        beginClock = getNowClock();
        pauseTime = 0;
        pauseClock = 0;
    }

    /**
     * 强制将状态改为停止录制
     */
    public static void stopRecordByForce() {
        started = false;
        isREC = false;
        isPAUSE = false;
        beginClock = 0;
        pauseTime = 0;
        pauseClock = 0;
    }

    /**
     * 强制将状态改为暂停录制
     */
    public static void pauseRecordByForce() {
        started = true;
        isREC = true;
        isPAUSE = true;
        pauseClock = getNowClock();
    }

    /**
     * 强制将状态改为继续录制
     */
    public static void resumeRecordByForce() {
        started = true;
        isREC = true;
        isPAUSE = false;
        pauseTime += getNowClock() - pauseClock;
        pauseClock = 0;
    }

//    /**
//     * 用户是否使用过录屏
//     * @return
//     */
//    public static boolean isUseREC(){
//        return SharedPreferencesUtils.getBoolean(App.getAppContext(), Constant.Record.STATE_USER_USE_REC,false);
//    }
//    /**
//     * 用户是否使用过录屏
//     * @return
//     */
//    public static void setUseREC(boolean isUse){
//        SharedPreferencesUtils.put(App.getAppContext(), Constant.Record.STATE_USER_USE_REC,isUse);
//    }
}