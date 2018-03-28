package com.csp.cases.activity.activity.luanchmod;

import android.content.Intent;

import com.csp.cases.activity.activity.LifeCycleActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>Create Date: 2017/9/12
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class LauchModeActivity extends LifeCycleActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("Standard", LauchModeActivity.class, "[Standard]模式，注意生命周期"));
		itemInfos.add(new ItemInfo("SingleTop", SingleTopActivity.class, "[SingleTop]模式，注意生命周期"));
		itemInfos.add(new ItemInfo("SingleTask", SingleTaskActivity.class, "[SingleTask]模式，注意生命周期"));
		itemInfos.add(new ItemInfo("SingleInstance", SingleInstanceActivity.class, "[SingleInstance]模式，注意生命周期"));
		return itemInfos;
	}

	@Override
	protected void onResume() {
		super.onResume();

		String msg = this.getClass().getSimpleName()
				+ "{hashCode = " + hashCode()
				+ ", stackId = " + getTaskId()
				+ '}';
		logError(true, msg);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		logError("当前调用方法：onNewIntent");
	}
}
