package com.csp.cases.activity.thread;

import android.os.AsyncTask;

import com.csp.utils.android.log.LogCat;

/**
 * Description: 异步任务案例
 * <p>Create Date: 2017/9/7
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class CustomAsyncTask extends AsyncTask<Character, Integer, String> {
	private String name; // 任务名称

	@Override
	protected void onPreExecute() {
		LogCat.e("任务预备，线程名(主线程)：" + Thread.currentThread().getName());
	}

	/**
	 * 异步线程
	 */
	@Override
	protected String doInBackground(Character... params) {
		name = "任务(" + params[0] + ')';
		try {
			LogCat.e(name + "开始，线程名(异步线程)：" + Thread.currentThread().getName());

			publishProgress(0);
			Thread.currentThread().sleep(1000);
			// TimeUnit.SECONDS.sleep(1000);
			publishProgress(100);

			LogCat.e(name + "结束，线程名(异步线程)：" + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			LogCat.printStackTrace(e);
		}

		return name + "执行结果返回值";
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		LogCat.e(name + "进度：" + values[0] + "，线程名(主线程)：" + Thread.currentThread().getName());
	}

	@Override
	protected void onPostExecute(String result) {
		LogCat.e(name + "结束结果：" + result + "，线程名(主线程)：" + Thread.currentThread().getName());
	}
}
