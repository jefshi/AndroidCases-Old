package com.csp.project.ui.broadcast;

import android.app.Activity;

/**
 * 广播(接收者)类型
 * 1) 静态广播: 清单配置文件注册
 * 2) 动态广播: java 注册
 * 
 * 广播处理
 * 1) 继承类, BroadcastReceiver
 * 2) 实现方法, onReceive()
 *   a) 广播接收到后, 底层自动调用该方法
 * 
 * 广播接收器优先级(有序广播)
 * 1) 区间: [-1000, 1000]
 * 2) 静态未设置优先级, 则按排列顺序作为优先级, 越靠前, 优先级越高
 * 3) 发送(无序/普通)广播
 *   a) 动态广播 > 静态广播
 *   b) 数字越小, 优先级越低
 * 4) 发送有序广播
 *   a) 不看动态静态, 数字越小, 优先级越低
 * 
 * 发送(普通/有序)广播
 * 1) 设置权限, 需要在清单配置中注册, <permission android:name="" />
 * 2) 设置广播ID, <action>
 * 3) 发送广播(有序无序)
 *   a) 发送普通广播: sendBroadcast(intent, permission)
 *   b) 发送有序广播: sendOrderedBroadcast(intent, permission)
 *   c) 普通广播的接收是异步的, 接收顺序不定, 所以性能较高
 *   d) 拦截广播, 只能拦截有序广播, abortBroadcast();
 * 
 * 静态广播注册, 清单配置文件注册
 * 1) 注册接收器: <application> -> <receiver>
 * 2) 设置接收器优先级: <intent-filter android:priority="">
 * 3) 设置接收的广播ID: <intent-filter> -> <action>
 * 
 * 动态广播注册, java 注册
 * 1) 创建广播接收器, receiver = new 广播()
 * 2) 注册(到组件上)广播, registerReceiver(receiver, filter);
 * 3) 解除注册, unregisterReceiver(receiver);
 * 4) filter, 类型: IntentFilter
 *   a) 可以接收的广播ID, addAction(action)
 *   b) 优先级, setPriority(priority)
 * 
 * 广播生命周期
 * 1) 静态广播
 *   a) 每次接收广播都会创建一个新对象
 *   b) 结束: onReceive() 执行完毕后
 * 2) 动态广播
 *   a) 注册前创建一个新对象
 *   b) 结束: 解除注册后结束
 *   c) 注: 广播处理的线程与广播解除的线程不是一个线程
 * 
 * 本地广播
 * 1) 用于进程内各个组件通讯, 不能进行跨进程通讯
 * 2) 需要借助[LocalBroadcastManager]对象
 *   a) 获取: LocalBroadcastManager.getInstance(this)
 *   b) 注册: LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
 *   c) 解除: LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver, filter);
 * 
 * API
 * 1) 拦截广播(限有序): abortBroadcast()
 * 
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-27 16:39:38
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class Activity1027_Broadcast extends Activity {

}
