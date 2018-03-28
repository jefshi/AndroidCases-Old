package com.csp.cases.base.activity;

import android.widget.AbsListView;

import com.csp.cases.base.dto.ItemInfo;

import java.util.List;

/**
 * Description: 项目信息显示(Activity)接口
 * <p>Create Date: 2017/7/18
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
interface ItemInterface {
	/**
	 * 设置案例项目
	 *
	 * @param view    AbsListView 对象
	 * @param objects 数据源
	 */
	void setAbsListView(AbsListView view, List<ItemInfo> objects);

	/**
	 * 获取项目信息列表
	 *
	 * @return 项目信息列表
	 */
	List<ItemInfo> getItemInfos();
}
