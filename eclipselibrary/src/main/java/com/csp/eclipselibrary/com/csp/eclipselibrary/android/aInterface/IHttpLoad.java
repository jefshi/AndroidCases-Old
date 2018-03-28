package com.csp.eclipselibrary.com.csp.eclipselibrary.android.aInterface;

/**
 * 网络相关监听器
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-22 20:16:18
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public interface IHttpLoad {

	/**
	 * 网络访问监听器
	 */
	public interface OnHttpLoadListener {
		/**
		 * 网络访问成功
		 * @param params 返回数据: [0]: (成功/失败的数据), [1...]: 其他参数数据
		 */
		public void onSuccess(Object... params);

		/**
		 * 网络访问失败
		 * @param code    失败代码
		 * @param message 失败原因
		 */
		public void onFailure(int code, String message);
	}
}
