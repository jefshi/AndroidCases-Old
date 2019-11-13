package com.csp.eclipselibrary.com.csp.eclipselibrary.android.aInterface;

/**
 * 不好分类的自定义监听器
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-12-23 15:03:18
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public interface ICustom {
	/**
	 * 全能的自定义监听器
	 */
	public interface OnCustomListener {
		/**
		 * 自定义监听器
		 * @param key    监听器Key
		 * @param params 参数
		 */
		public void onCustom(String key, Object... params);
	}

	/**
	 * 全选监听器, 监听列表的选中项发生后, 导致的全选或非全选事件
	 */
	public interface OnCheckAllListener {
		/**
		 * 全选事件
		 * @param isCheckAll 是否全选
		 */
		public void onCheckAll(boolean isCheckAll);
	}

}
