package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.util;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 页面跳转类, 工具类
 *
 * @author csp
 *         <p style='font-weight:bold'>Date:</p> 2016-10-11 17:59:18
 *         <p style='font-weight:bold'>AlterDate:</p> 2016-10-17 16:27:54
 * @version 1.1
 */
public class IntentUtil {
    /**
     * 数据集合
     */
    private static class Data {
        public static int enterResId; // 入场动画
        public static int exitResId; // 出场动画
    }

    // ========================================
    // 页面跳转, 基本
    // ========================================

    /**
     * 仅跳转界面
     *
     * @param conFrom 起点
     * @param clsTo   目的
     */
    public static void startActivity(Context conFrom, Class<?> clsTo) {
        startActivity(conFrom, clsTo, null);
    }

    /**
     * 跳转界面, 并携带[Bundle]数据
     *
     * @param conFrom 起点
     * @param clsTo   目的
     * @param data    携带数据
     */
    public static void startActivity(Context conFrom, Class<?> clsTo, Bundle data) {
        Intent intent = new Intent();
        if (data != null) {
            intent.putExtras(data); // 绑定数据
        }
        intent.setClass(conFrom, clsTo); // 设置跳转页面
        conFrom.startActivity(intent);
    }

    /**
     * 跳转界面, 携带[Bundle]数据, 携带附加[Bundle]项(如: 动画)
     *
     * @param conFrom 起点
     * @param clsTo   目的
     * @param data    携带数据
     * @param options 携带附加项
     */
    public static void startActivityByOption(Context conFrom, Class<?> clsTo, Bundle data, Bundle options) {
        Intent intent = new Intent();
        if (data != null) {
            intent.putExtras(data);
        }
        intent.setClass(conFrom, clsTo);
        conFrom.startActivity(intent, options);
    }

    // ========================================
    // 页面跳转, 高级
    // ========================================

    /**
     * 设置出场入场动画
     *
     * @param enterId 入场动画
     * @param exitId  出场动画
     */
    public static void setSkipActivityAnim(int enterId, int exitId) {
        Data.enterResId = enterId;
        Data.exitResId = exitId;
    }

    /**
     * 跳转页面, 携带出场入场动画
     *
     * @param conFrom 起点
     * @param clsTo   目的
     */
    public static void startActivityByAnim(Context conFrom, Class<?> clsTo) {
        startActivityByAnim(conFrom, clsTo, null, Data.enterResId, Data.exitResId);
    }

    /**
     * 跳转页面, 携带出场入场动画
     *
     * @param conFrom 起点
     * @param clsTo   目的
     * @param data    携带数据
     */
    public static void startActivityByAnim(Context conFrom, Class<?> clsTo, Bundle data) {
        startActivityByAnim(conFrom, clsTo, data, Data.enterResId, Data.exitResId);
    }

    /**
     * 跳转页面, 携带出场入场动画
     *
     * @param conFrom    起点
     * @param clsTo      目的
     * @param data       携带数据
     * @param enterResId 入场动画
     * @param exitResId  出场动画
     */
    public static void startActivityByAnim(Context conFrom, Class<?> clsTo, Bundle data, int enterResId, int exitResId) {
        ActivityOptions ao = ActivityOptions.makeCustomAnimation(conFrom, enterResId, exitResId);
        Bundle options = ao.toBundle();
        startActivityByOption(conFrom, clsTo, data, options);
    }
}
