package com.csp.project.common.base;

public interface InitialUi {
	/**
	 * 初始化布局, 依次调用initBundle(), initView(), initViewContent(), initViewEvent() 方法
	 */
	public void initialUi();
	
	/**
	 * 初始化传入的参数, 只运行一次
	 */
	public void initBundle();

	/**
	 * 获取各个[View], 只运行一次
	 */
	public void initView();

	/**
	 * 设置各个[View]内容, 只运行一次
	 */
	public void initViewContent();

	/**
	 * 设置各个[View]事件, 只运行一次
	 */
	public void initViewEvent();
	
	/**
	 * 刷新UI, 运行多次
	 */
	public void onRefresh();
}
