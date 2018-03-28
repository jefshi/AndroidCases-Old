package com.csp.eclipselibrary.com.csp.eclipselibrary.android.aInterface;

/**
 * 异步任务相关监听器
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-22 20:16:18
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public interface IAsyncTask {

	/**
	 * 异步任务执行监听器
	 */
	public interface OnAsyncTaskListener {
		/**
		 * 异步任务进度更新监听
		 * @param key    异步任务标识
		 * @param params 传递的参数
		 */
		public void OnAsyncProgress(String key, Object... params);

		/**
		 * 异步任务执行完毕监听
		 * @param key    异步任务标识
		 * @param params 传递的参数
		 */
		public void OnAsyncComplete(String key, Object... params);
	}
}
