package com.csp.eclipselibrary.com.csp.eclipselibrary.android.aInterface;

/**
 * 对话框相关监听器
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-22 20:16:18
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public interface IDialog {

	/**
	 * 对话框关闭监听器
	 */
	public interface OnDismissListener {
		/**
		 * 对话框关闭时, 回调的监听器
		 * @param params
		 */
		public void onDismiss(Object... params);
	}
}
