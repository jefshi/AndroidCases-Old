package com.project.day1026.service.aidl;

import com.common.android.control.util.LogUtil;
import com.project.day1026.service.aidl.IRemoteService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

public class AIDLRemoteService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.logInfo("onBind()");
		return new IRemoteService.Stub() {
			@Override
			public int getId() throws RemoteException {
				LogUtil.logInfo("IRemoteService.getId()");
				return Process.myPid();
			}
		};
	}

}
