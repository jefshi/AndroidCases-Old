package com.project.day1026.service;

import com.common.android.control.util.LogUtil;
import com.project.day1026.service.aidl.IRemoteService;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class GetServiceConnection {
	// ========================================
	// 单例模式
	// ========================================
	private GetServiceConnection() {
	}

	public static GetServiceConnection getInstance() {
		return Inner.instance;
	}

	static class Inner {
		public static GetServiceConnection instance = new GetServiceConnection();
	}

	// ========================================
	// 绑定模式需要的服务链接: 仅Log, 为查看流程
	// ========================================
	private ServiceConnection scLog;

	public void clearScLog() {
		scLog = null;
	}
	
	public ServiceConnection getLogServiceConnection() {
		if (scLog != null) {
			return scLog;
		}

		scLog = new ServiceConnection() {

			@Override
			/**
			 * 非正常解绑时执行
			 */
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				LogUtil.logInfo("onServiceDisconnected");
				scLog = null;
			}

			@Override
			/**
			 * 绑定成功时(Activity)执行
			 */
			public void onServiceConnected(ComponentName name, IBinder iBinder) {
				LogUtil.logInfo("onServiceConnected");

				// 获取服务
				// MixService service = ((LocalBinder) iBinder).getService();
				// LogUtil.logInfo("获取服务" + service.toString());
			}
		};
		return scLog;
	}

	// ========================================
	// 绑定链接: 绑定后进行远程通讯
	// ========================================
	private ServiceConnection scRemote;

	public void clearScRemote() {
		scRemote = null;
	}
	
	public ServiceConnection getRemoteServiceConnection() {
		if (scRemote != null) {
			return scRemote;
		}

		scRemote = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				scLog = null;
			}

			/**
			 * 绑定模式: 向远程[Service]发送消息
			 * 1) 通过[Messenger], 关联远程进程的[IBinder]
			 * 2) Messenger..send(Messenger msg);
			 *   a) 由当前进程发送消息给远程进程
			 */
			@Override
			public void onServiceConnected(ComponentName name, IBinder iBinder) {
				LogUtil.logInfo("onServiceConnected");

				// 通过[Messenger]关联绑定成功后返回的[IBinder]对象
				Messenger messenger = new Messenger(iBinder);
				Message msg = Message.obtain();

				// 发送的消息
				Bundle bundle = new Bundle();
				bundle.putString("key", "通讯: my.android.project send to my.android.app");
				msg.setData(bundle);

				try {
					// 发送消息
					messenger.send(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		};
		return scRemote;
	}

	// ========================================
	// 绑定链接: AIDL 远程通讯
	// ========================================
	private ServiceConnection scAidl;

	public void clearScAidl() {
		scAidl = null;
	}

	public ServiceConnection getAidlServiceConnection() {
		if (scAidl != null) {
			return scAidl;
		}

		scAidl = new ServiceConnection() {

			@Override
			/**
			 * 非正常解绑时执行
			 */
			public void onServiceDisconnected(ComponentName name) {
				LogUtil.logInfo("onServiceDisconnected");
				scLog = null;
			}

			@Override
			/**
			 * 绑定成功时执行
			 */
			public void onServiceConnected(ComponentName name, IBinder iBinder) {
				LogUtil.logInfo("onServiceConnected");
				IRemoteService service = IRemoteService.Stub.asInterface(iBinder);
				try {
					int id = service.getId();
					LogUtil.logInfo("pid." + id);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		};
		return scAidl;
	}
}
