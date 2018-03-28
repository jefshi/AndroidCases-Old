package com.csp.library.android.interfaces;

/**
 * Description: 页面初始化接口
 * <p>Create Date: 2017-12-22
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidLibrary 1.0.0
 */
public interface InitialUi {
	/**
	 * 初始化布局, 依次调用initBundle(), initView(), initViewContent(), initViewEvent() 方法
	 */
	void initialUi();

	/**
	 * 初始化传入的参数, 只运行一次
	 */
	void initBundle();

	/**
	 * 获取各个[View], 只运行一次
	 */
	void initView();

	/**
	 * 设置各个[View]内容, 只运行一次
	 */
	void initViewContent();

	/**
	 * 设置各个[View]事件, 只运行一次
	 */
	void initViewEvent();

	/**
	 * 刷新UI, 运行多次
	 */
	void onRefresh();
}
