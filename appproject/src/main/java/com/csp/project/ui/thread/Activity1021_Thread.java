package com.csp.project.ui.thread;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.android.control.util.IntentUtil;
import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;
import com.project.tools.piture.ActivityPiture;

import java.util.ArrayList;
import java.util.List;

/**
 * [CustomThread.java]说明
 * 1) 自定义工作线程
 * 2) 线程描述
 * 
 * [DemoAsyncTask.java]说明
 * 1) 异步任务使用
 * 
 * 使用工作线程
 * 1) 创建并启动工作线程
 * 2) 创建消息发送与处理器
 * 3) 发送消息
 * 4) 退出工作线程
 * 
 * 消息
 * 1) 创建: msg = new Message()
 * 2) 封装任务
 *   a) msg.obj = 数据[可以是任务(Runnable), 可以是普通数据, 等等]
 *   b) msg.what = int
 * 
 * 异步任务(串行)
 * 1) 创建异步任务(new AsyncTask())
 * 2) 发布任务: execute(params)
 *   a) 一个异步任务不能多次调用execute(params)
 * 3) 一个异步任务只能执行一个任务
 * 
 * 异步任务(并行)
 * 1) 创建异步任务(new AsyncTask())
 * 2) 发布任务: executeOnExecutor(executor, params)
 *   a) 使用线程池与消息队列
 * 3) 一个异步任务只能执行一个任务
 * 
 * 多张图片异步加载
 * 1) 见[ActivityPiture.java]说明
 * 
 * 内存泄漏问题
 * 1) 非静态内部类会持有外部类对象的引用, 导致内部类对象不释放, 外部类也无法释放
 * 2) Handler对象发送消息时, 会将Handler对象绑定到消息上, 故消息执行完毕之前, Handler对象无法释放
 *   a) 所以使用Handler对象应使用静态内部类进行实例化, 或使用外部类实例化
 * 
 * 常见[API]
 * 1) Message
 *   a) Message.obtain(...)
 *   b) msg.sendToTarget(...)
 *   c) msg.obj = Object
 *   d) msg.what = int
 *   e) msg.target = Handler
 *   f) msg.arg1 = int
 *   g) msg.arg2 = int
 * 2) MessageQueue
 * 3) Looper
 *   a) Looper.prepare()
 *   b) Looper.myLooper()
 *   d) Looper.getMainLooper()
 *   c) Looper.loop()
 *   e) loop.quit()
 * 4) Handler
 * 
 * 
 */
public class Activity1021_Thread extends Activity {
	/**
	 * 多张图片异步加载
	 */
	public void morePitureAsync() {
		IntentUtil.startActivity(this, ActivityPiture.class);
	}
}
