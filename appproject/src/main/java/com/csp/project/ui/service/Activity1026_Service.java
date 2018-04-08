package com.project.day1026.service;

import com.common.android.control.util.LogUtil;
import com.project.R;
import com.project.common.grideview.FunctionGridView;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 所有的[Service]需要在清单配置文件中注册
 * 1) 注册: <application> -> <service> -> <intent-filter> -> <action>
 * 2) 唯一性: <action>
 * 3) 一个Service, 允许有多个<action>
 * 
 * Service 类型
 * 1) 启动模式
 * 2) 绑定模式
 * 3) 混合模式: 启动 + 绑定
 * 
 * 启动模式
 * 1) 生命周期(启动、停止): 见源代码
 * 2) 返回值: 见[MixService]源代码
 * 3) 场合: 服务被非正常中止时, 需要重启的场合
 * 
 * 绑定模式
 * 1) 生命周期(启动、停止): 见源代码
 * 2) 绑定需使用的[ServiceConnection](绑定链接)
 *   a) onServiceConnected()   : 获取服务
 *   b) onServiceDisconnected(): 绑定链接置为空
 * 3) 解绑需使用同一个[ServiceConnection]
 * 4) 为避免内存泄漏, 哪个Activity绑定Service，哪个Activity销毁时解绑
 * 5) 场合: 需要获取[Service], 并操作
 * 
 * 绑定模式获取[Service]
 * 1) 服务端: onBind() 返回引用[Service]的对象(一般用内部类)
 * 2) 客户端: onServiceConnected(), 调用上述对象的方法获取服务
 * 
 * 混合模式
 * 1) 生命周期(启动、停止): 见源代码
 * 2) 开启服务: 一般先启动, 再绑定. 但反过来也行
 * 3) 销毁服务: 一般先停止服务, 再解除绑定. 但反过来也行
 * 4) 场合:
 *   a) 服务被非正常中止时, 需要重启的场合
 *   b) 需要获取[Service], 并操作
 * 
 * [Intent], 显示意图与隐式意图
 * 1) 显示意图, 仅进程内访问, new Intent(this, MixService.class)
 * 2) 隐式意图, 可跨进程访问, new Intent("my.android.app.MixService")
 * 
 * 远程Service + 通讯
 * 步骤:
 * 1) 服务端创建[Service]
 *   a) 创建[Service.java]
 *   b) 清单文件注册[Service]
 * 2) 客户端连接[Service]
 *   a) 启动模式(可选)
 *   b) 绑定模式(通讯处理)
 *   c) 中断服务连接
 *   d) 停止服务(可选)
 * 
 * AIDL 远程访问Service
 * 描述: 
 * 1) AIDL: Android Interface definition language的缩写，是一种android内部进程通信接口的描述语言，通过它我们可以定义进程间的通信接口
 * 2) IPC: Inter-Process Communication : 进程间通信
 * 3) AIDL是[Android]独有的实现[IPC]功能的方式
 * 步骤:
 * 1) 服务器: 编写[***.aidl](类似接口)(以IRemoteService.aidl为例)
 *   a) 将会自动生成同名的[***.java], 位置在[gen/...]
 * 2) 服务器: 编写Service
 *   a) 实现:  IBinder onBind(Intent intent)
 *   b) 返回值: return new IRemoteService.Stub() { // 实现接口方法. }
 * 3) 客户端: 获取通讯协议
 *   a) 复制[***.aidl](官方推荐), 也可以直接复制自动生成的[***.java]
 *   b) 注: 包路径必须与服务端相同
 * 4) 客户端: 绑定模式链接远程Service
 * 5) 客户端: 绑定链接[ServiceConnection]的[onServiceConnected()]实现
 *   a) 获取service: IRemoteService service = IRemoteService.Stub.asInterface(iBinder);
 *   b) 调用service.接口方法()
 */
public class Activity1026_Service extends Activity {
	public GetServiceConnection gsc = GetServiceConnection.getInstance();

	protected TextView	txt;
	protected ImageView	img;
	protected ListView	lsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		// 创建[SampleGridView]
		FunctionGridView fgv = new FunctionGridView(this);

		// 数据源
		fgv.addData("启动模式(启动、终止、返回值)", "startService01", false);
		fgv.addData("绑定模式(启动、终止、获取服务)", "bindService01", false);
		fgv.addData("混合模式(启动、终止)", "mixService01", false);
		fgv.addData("多条请求串行执行(启动模式, 自动销毁服务)", "runMoreRequest", false);
		fgv.addData("----------", "", false);
		fgv.addData("远程Service(启动模式)(仅模板, 不可用)", "remoteStartService", false);
		fgv.addData("远程Service(绑定模式)(仅模板, 远程通讯)", "remoteBindService", false);
		fgv.addData("远程Service(绑定**,AIDL,模板,远程通讯)", "remoteAidlService", false);
		fgv.addData("----------", "", false);
		fgv.addData("使用[IntentService](源码参考第4项)", "demoIntentService", false);
		fgv.addData("自定义[IntentService]", "demoMyInstentService", false);

		// [GridView] 生成
		fgv.setGridView(findViewById(R.id.grvOptions));

		// 获取其他控件
		txt = (TextView) findViewById(R.id.txtOption);
		img = (ImageView) findViewById(R.id.imgOption);
		lsv = (ListView) findViewById(R.id.lsvOption);

	}

	/**
	 * n ms后停止服务
	 * @param ServiceConnection 绑定链接
	 */
	public void stopServiceAfterMilli(final int milli, final Intent... intents) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(milli);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < intents.length; i++) {
					stopService(intents[i]);
					LogUtil.logInfo("停止服务." + i);
				}
			};
		}.start();
	}

	/**
	 * n ms后解除绑定链接
	 * @param ServiceConnection 绑定链接
	 */
	public void unbindServiceAfterMilli(final int milli, final ServiceConnection... scs) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(milli);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int i = 0; i < scs.length; i++) {
					unbindService(scs[i]);
					LogUtil.logInfo("解除绑定链接." + i);
				}
			};
		}.start();
	}

	/**
	 * 启动模式(启动、终止、返回值)
	 * 1) 生命周期(启动、停止): 见源代码
	 * 2) 返回值: 见[MixService]源代码
	 * 3) 场合: 服务被非正常中止时, 需要重启的场合
	 * 
	 * [Intent], 显示意图与隐式意图
	 * 1) 显示意图, 仅进程内访问, new Intent(this, MixService.class)
	 * 2) 隐式意图, 可跨进程访问, new Intent("my.android.app.MixService")
	 */
	public void startService01() {
		// 显示意图
		Intent intent = new Intent(this, MixService.class);

		// 启动模式
		startService(intent);

		// 停止服务
		stopServiceAfterMilli(1000, intent);

		txt.setText("启动与销毁方法见源代码、[Service]流程见LogCat\n返回值见[Service.onStartCommand()]说明");
	}

	/**
	 * 绑定模式(启动、终止、获取服务)
	 * 1) 生命周期(启动、停止): 见源代码
	 * 2) 绑定需使用的[ServiceConnection]
	 * 3) 解绑需使用同一个[ServiceConnection]
	 * 4) 为避免内存泄漏, 哪个Activity绑定Service，哪个Activity销毁时解绑
	 * 5) 场合: 需要获取[Service], 并操作
	 * 
	 * [Intent], 显示意图与隐式意图
	 * 1) 显示意图, 仅进程内访问, new Intent(this, MixService.class)
	 * 2) 隐式意图, 可跨进程访问, new Intent("my.android.app.MixService")
	 */
	public void bindService01() {
		// 显示意图
		Intent intent = new Intent(this, MixService.class);

		// 绑定模式
		bindService(intent, gsc.getLogServiceConnection(), Service.BIND_AUTO_CREATE);

		// 获取服务, 见绑定链接说明
		LogUtil.logInfo("获取服务.兼容问题, 请见源代码");

		// 解绑服务, 为避免内存泄漏, 哪个Activity绑定Service，哪个Activity销毁时解绑
		unbindServiceAfterMilli(1000, gsc.getLogServiceConnection());

		txt.setText("启动与销毁方法见源代码、[Service]流程见LogCat");
	}

	/**
	 * 混合模式(启动、终止)
	 * 1) 生命周期(启动、停止): 见源代码
	 * 2) 开启服务: 一般先启动, 再绑定. 但反过来也行
	 * 3) 销毁服务: 一般先停止服务, 再解除绑定. 但反过来也行
	 * 4) 场合:
	 *   a) 服务被非正常中止时, 需要重启的场合
	 *   b) 需要获取[Service], 并操作
	 * 
	 * [Intent], 显示意图与隐式意图
	 * 1) 显示意图, 仅进程内访问, new Intent(this, MixService.class)
	 * 2) 隐式意图, 可跨进程访问, new Intent("my.android.app.MixService")
	 */
	public void mixService01() {
		// 显示意图
		Intent intent = new Intent(this, MixService.class);

		// 启动模式
		startService(intent);

		// 绑定模式
		bindService(intent, gsc.getLogServiceConnection(), Service.BIND_AUTO_CREATE);

		// 停止服务
		stopServiceAfterMilli(1000, intent);

		// 解绑服务
		unbindServiceAfterMilli(1000, gsc.getLogServiceConnection());

		txt.setText("启动与销毁方法见源代码、[Service]流程(生命周期)见LogCat");
	}

	/**
	 * 多条请求串行执行(启动模式, 自动销毁服务)
	 */
	public void runMoreRequest() {
		// 显示意图
		Intent intent = new Intent(this, MixService.class);
		intent.putExtra("key", "runMoreRequest()");

		// 启动模式, 发送多次请求
		startService(intent);
		startService(intent);
		startService(intent);
		startService(intent);

		txt.setText("启动与销毁方法见源代码、[Service](生命周期)流程见LogCat");
	}

	/**
	 * 远程Service(启动模式)(模板, 不可用)
	 */
	public void remoteStartService() {
		LogUtil.logInfo("启动远程Service(启动模式), remoteStartService");

		// 隐藏意图
		// action: 目标[AndroidManifest.xml] -> <intent-filter><<action> -> android:name
		String action = "my.android.app.MixService";
		Intent intent = new Intent(action);

		// 启动远程Service(启动模式)
		startService(intent);
		startService(intent);
		startService(intent);

		// 停止远程Service(启动模式)
		stopServiceAfterMilli(1000, intent);

		txt.setText("请按照步骤将服务器与客户端分离, 方可看到真正效果");
	}

	/**
	 * 远程Service(绑定模式)(模板, 远程通讯)
	 */
	public void remoteBindService() {
		LogUtil.logInfo("启动远程Service(绑定模式), remoteBindService");

		// 隐藏意图
		// action: 目标[AndroidManifest.xml] -> <intent-filter><<action> -> android:name
		String action = "my.android.app.MixService";
		Intent intent = new Intent(action);

		// 启动远程Service(绑定模式)
		bindService(intent, gsc.getRemoteServiceConnection(), Service.BIND_AUTO_CREATE);

		// 停止远程Service(绑定模式)
		unbindServiceAfterMilli(2000, gsc.getRemoteServiceConnection());

		txt.setText("请按照步骤将服务器与客户端分离, 方可看到真正效果");
	}

	/**
	 * AIDL 远程访问Service
	 * 步骤:
	 * 1) 服务器: 编写[***.aidl](类似接口)(以IRemoteService.aidl为例)
	 *   a) 将会自动生成同名的[***.java], 位置在[gen/...]
	 * 2) 服务器: 编写Service
	 *   a) 实现:  IBinder onBind(Intent intent)
	 *   b) 返回值: return new IRemoteService.Stub() { // 实现接口方法. }
	 * 3) 客户端: 复制自动生成的[IRemoteService.java]
	 * 4) 客户端: 绑定模式链接远程Service
	 * 5) 客户端: 绑定链接[ServiceConnection]的[onServiceConnected()]实现
	 *   a) 获取service: IRemoteService service = IRemoteService.Stub.asInterface(iBinder);
	 *   b) 调用service.接口方法()
	 */
	public void remoteAidlService() {
		LogUtil.logInfo("启动远程Service(绑定模式, AIDL), remoteAidlService");

		// 隐藏意图
		// action: 目标[AndroidManifest.xml] -> <intent-filter><<action> -> android:name
		String action = "my.android.app.AIDLRemoteService";
		Intent intent = new Intent(action);

		// 启动远程Service(绑定模式)
		bindService(intent, gsc.getAidlServiceConnection(), Service.BIND_AUTO_CREATE);

		// 停止远程Service(绑定模式)
		unbindServiceAfterMilli(2000, gsc.getAidlServiceConnection());

		txt.setText("请按照步骤将服务器与客户端分离, 方可看到真正效果");
	}

	/**
	 * 使用[IntentService](源码参考第4项)
	 */
	public void demoIntentService() {
		LogUtil.logInfo("demoIntentService()");

		// 显示意图
		Intent intent = new Intent(this, MyIntentService.class);

		// 启动模式
		startService(intent);

		// 停止服务
		stopServiceAfterMilli(1000, intent);

		txt.setText("启动与销毁方法见源代码、LogCat");
	}

	/**
	 * 自定义[IntentService]
	 */
	public void demoMyInstentService() {
		txt.setText("待补充");
	}
}
