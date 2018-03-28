package com.csp.cases.activity.activity;

import com.csp.cases.activity.activity.luanchmod.LauchModeActivity;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: [Activity]案例
 * <p>Create Date: 2017/9/12
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ActivityActivity extends BaseGridActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("四大启动模式", LauchModeActivity.class, "四大启动案例，见清单配置文件"));
		return itemInfos;
	}
}
