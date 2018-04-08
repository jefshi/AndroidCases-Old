package com.csp.eclipselibrary.com.csp.eclipselibrary.android.control.viewutil;

import java.lang.reflect.Field;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 标题栏主题类, 工具类
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-17 17:03:07
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ActionBarUtil {
	/**
	 * 设置AcionBar Theme： 蓝色背景、中间白色文本、无Title、有Logo
	 * @version 1.0.0
	 * @author tarena
	 * <p style='font-weight:bold'>Date:</p> 2016年10月13日 下午8:27:45
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static void setTheme01(Context context, ActionBar actionBar, String title) {
		// 空title
		// actionBar.setTitle("");
		actionBar.setDisplayShowTitleEnabled(false);

		// 设置背景色
		// actionBar.setBackgroundDrawable(d);

		// 自定义View
		actionBar.setDisplayShowCustomEnabled(true);
		LayoutParams layoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.CENTER);
		TextView txt = new TextView(context);
		txt.setText(title);
		txt.setTextColor(Color.WHITE);
		txt.setTextSize(15);
		actionBar.setCustomView(txt, layoutParams);

		// AcionBar 显示Logo("<")(选项菜单)
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * AcitonBar 显示浮动菜单(反射)
	 * @version 1.0.0
	 * @param context
	 * @author tarena
	 * <p style='font-weight:bold'>Date:</p> 2016年10月14日 上午8:39:32
	 * <p style='font-weight:bold'>AlterDate:</p>
	 */
	public static void setOverFlowMunuState(Context context) {
		ViewConfiguration vcf = ViewConfiguration.get(context); // 获取要访问的对象
		try {
			Field field = vcf.getClass().getDeclaredField("sHasPermanentMenuKey"); // 获取类的属性: 含名称、类型、访问类型
			// boolean value = (Boolean) field.get(vcf); // 获取对象的指定属性的值
			field.setAccessible(true);
			field.set(vcf, false); // 设置对象指定属性的值
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
