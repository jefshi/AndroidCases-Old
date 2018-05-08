package com.csp.cases.base.dto;

import android.app.Activity;

/**
 * Description: 项目信息
 * <p>Create Date: 2017/8/9
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class ItemInfo {
	private String name; // 项目名称
	private String description; // 项目描述
	private boolean workThread; // true: 工作线程中执行
	private String methodName; // 执行的方法
	private Class<? extends Activity> jumpClass; // 跳转Class
	private boolean error; // true: 错误

	private ItemInfo(String name, String description) {
		this.name = name;
		this.description = description;
		this.workThread = false;
		this.error = false;
	}

	public ItemInfo(String name, Class<? extends Activity> jumpClass, String description) {
		this(name, description);
		this.jumpClass = jumpClass;
	}

	public ItemInfo(String name, String methodName, String description) {
		this(name, description);
		this.methodName = methodName;
	}

	public ItemInfo(boolean workThread, String name, String methodName, String description) {
		this(name, description);
		this.workThread = workThread;
		this.methodName = methodName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isWorkThread() {
		return workThread;
	}

	public void setWorkThread(boolean workThread) {
		this.workThread = workThread;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<? extends Activity> getJumpClass() {
		return jumpClass;
	}

	public void setJumpClass(Class<? extends Activity> jumpClass) {
		this.jumpClass = jumpClass;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ItemInfo{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				", workThread=" + workThread +
				", methodName='" + methodName + '\'' +
				", jumpClass=" + jumpClass +
				", error=" + error +
				'}';
	}
}