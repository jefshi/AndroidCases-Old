package com.csp.eclipselibrary.com.csp.eclipselibrary.android.aInterface;

/**
 * 无需实现或继承, 仅用于提示
 */
public interface IDatabase {

	public <T> T queryAll(String sql);

	public int insert(String sql);

	public int delete(String sql);

}
