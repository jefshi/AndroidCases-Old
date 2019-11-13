package com.csp.project.ui.thread;

import android.os.AsyncTask;

/**
 * 异步任务
 * 参数说明
 * 1) Params:   传入异步任务的参数的类型
 * 2) Progress: 表示任务执行进度的数据的类型
 * 3) Result:   异步任务执行完毕, 返回的数据类型
 * 
 * 运行顺序:
 * 1) onPreExecute()   : 主线程执行, 任务执行之前操作, 一般是[View]的初始化操作
 * 2) doInBackground() : 工作线程执行, 异步任务
 * 3) onPostExecute()  : 主线程执行, 任务执行完毕后操作, 一般是[View]的刷新操作
 * 
 * 进度提醒
 * 1) 在[doInBackground()]中执行: publishProgress(进度);
 * 2) 重写: onProgressUpdate()
 */
public class DemoAsyncTask extends AsyncTask<Character, Integer, String> {
	@Override
	protected String doInBackground(Character... params) {
		return null;
	}
}
