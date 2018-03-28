package com.csp.cases.activity.intent;

import android.content.Intent;

import com.csp.cases.activity.broadcast.StaticReceiver;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: Intent 应用案例
 * <p>Create Date: 2017/9/5
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class IntentActivity extends BaseGridActivity {

	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> itemInfos = new ArrayList<>();
		itemInfos.add(new ItemInfo("数据传输", "transfer", "通过实现[Serializable, Parcelable]完成数据传输"));
		return itemInfos;
	}

	/**
	 * 数据传输
	 */
	private void transfer() {
		Intent intent = new Intent(StaticReceiver.RECEIVER_ACTION);
		intent.putExtra("Serializable", new OptionBySer(10, 20));
		intent.putExtra("Parcelable", new OptionByPar(15, 25));
		sendBroadcast(intent, StaticReceiver.PERMISSION);
	}
}
