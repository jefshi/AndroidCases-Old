package com.csp.cases.activity;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.base.activity.BaseGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 度量以及屏幕参数案例
 * <p>Create Date: 2017/8/7
 * <p>Modify Date: 无
 * <p>
 * <p>density: 屏幕密度, px / inch
 * <p>1) 低密度, ldpi, 120px/inch, 1 dpi = 0.75 px
 * <p>2) 中密度, mdpi, 160px/inch, 1 dpi = 1 px
 * <p>3) 高密度, hdpi, 240px/inch, 1 dpi = 1.5 px
 * <p>4) 高高密度, xhdpi, 360px/inch, 1 dpi = 2 px
 * <p>5) 高高高密度, xxhdpi, 480px/inch, 1 dpi = 3 px
 * <p>
 * <p>单位说明
 * <p>1) dpi, 绝对值, 1dp = 1/160 inch
 * <p>2) sp, 绝对值
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class MetricsActivity extends BaseGridActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("屏幕参数", "getDisplayMetrics", ""));
		items.add(new ItemInfo("单位转换", "unitConversion", "单位转换说明见类说明"));

		return items;
	}

	/**
	 * 获取屏幕参数
	 */
	private void getDisplayMetrics() {
		// 方法01: Context
		DisplayMetrics metrics = getResources().getDisplayMetrics();

		// 方法02: Context
		// DisplayMetrics metrics = new DisplayMetrics();
		// WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// manager.getDefaultDisplay().getMetrics(metrics);

		// 方法03: Activity
		// DisplayMetrics metrics = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(metrics);

		// 方法04: Activity
		// Display display = getWindowManager().getDefaultDisplay();
		// String msg = display.getHeight() + " : " + display.getWidth();

		String msg = "获取屏幕参数(宽高密度): "
				+ metrics.widthPixels + " : " + metrics.heightPixels + " : "
				+ metrics.density;
		setDescription(msg);
	}

	/**
	 * 单位转换(dp -> px)
	 */
	private void unitConversion() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();

		int dip = 55;
		int unit = TypedValue.COMPLEX_UNIT_SP;
		float px = TypedValue.applyDimension(unit, dip, metrics);
		logError(dip + " px = " + px + " px");
	}
}
