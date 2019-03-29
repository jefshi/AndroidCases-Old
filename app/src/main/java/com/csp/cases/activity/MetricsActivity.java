package com.csp.cases.activity;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.csp.cases.base.dto.ItemInfo;
import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.utils.android.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 度量以及屏幕参数案例
 * <p>Create Date: 2017/8/7
 * <p>Modify Date: 2018/03/29
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
 * @version 1.0.2
 * @since AndroidCases 1.0.0
 */
@SuppressWarnings("unused")
public class MetricsActivity extends BaseGridActivity {
    @Override
    public List<ItemInfo> getItemInfos() {
        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("屏幕参数", "getDisplayMetrics", ""));
        items.add(new ItemInfo("状态栏、标题栏高度", "activityMetrics", "参考博客：https://www.cnblogs.com/developer-wang/p/6771073.html, https://blog.csdn.net/a_running_wolf/article/details/50477965"));
        items.add(new ItemInfo("单位转换", "unitConversion", "单位转换说明见类说明"));

        return items;
    }

    /**
     * 获取屏幕参数
     */
    private void getDisplayMetrics() {
        // 方法01: Context
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LogCat.e("方法01: " + metrics.widthPixels + " : " + metrics.heightPixels + " : " + metrics.density);

        // 方法02: Context
        metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            manager.getDefaultDisplay().getMetrics(metrics);
            LogCat.e("方法02: " + metrics.widthPixels + " : " + metrics.heightPixels + " : " + metrics.density);
        }

        // 方法03: Activity
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        LogCat.e("方法03: " + metrics.widthPixels + " : " + metrics.heightPixels + " : " + metrics.density);

        // 方法04: Activity
        Display display = getWindowManager().getDefaultDisplay();
        LogCat.e("方法04: " + display.getWidth() + " : " + display.getHeight() + " : " + metrics.density);

        String msg = "请查看 LogCat";
        setDescription(msg);
    }

    /**
     * 获取屏幕参数
     */
    private void activityMetrics() throws Exception {
        // 获取状态栏高度——方法01：获取status_bar_height资源的ID
        int statusBarHeight1 = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        LogCat.e("状态栏-方法01: ", statusBarHeight1);

        // 获取状态栏高度——方法02：获取status_bar_height资源的ID
        int statusBarHeight2 = -1;
        Class<?> clazz = Class.forName("com.android.internal.R$dimen");
        Object object = clazz.newInstance();
        int height = Integer.parseInt(clazz.getField("status_bar_height")
                .get(object).toString());
        statusBarHeight2 = getResources().getDimensionPixelSize(height);
        LogCat.e("状态栏-方法02: ", statusBarHeight2);

        /**
         * 获取状态栏高度——方法3
         * 应用区的顶端位置即状态栏的高度
         * *注意*该方法不能在初始化的时候用
         * */
        // *注意* 如果单单获取statusBar高度而不获取titleBar高度时，这种方法并不推荐大家使用，因为这种方法依赖于WMS（窗口管理服务的回调）。
        // 正是因为窗口回调机制，所以在Activity初始化时执行此方法得到的高度是0，这就是很多人获取到statusBar高度为0的原因。
        // 这个方法推荐在回调方法onWindowFocusChanged()中执行，才能得到预期结果。
        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        // 高度为rectangle.top-0仍为rectangle.top
        LogCat.e("状态栏-方法03: ", rectangle.top);

        /**
         * 获取状态栏高度——方法4
         * 状态栏高度 = 屏幕高度 - 应用区高度
         * *注意*该方法不能在初始化的时候用
         * */
        // 3、4这两种方法其实本质是一样，所以如果单单获取statusBar高度而不获取titleBar高度时也不推荐大家使用，理由同上方法3
        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //应用区域
        Rect outRect1 = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        int statusBar = dm.heightPixels - outRect1.height();  //状态栏高度=屏幕高度-应用区域高度
        LogCat.e("状态栏-方法04: ", statusBar);

        // TODO ???
    }

    /**
     * 单位转换(dp -> px)
     */
    private void unitConversion() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int sp = 55;
        int unit = TypedValue.COMPLEX_UNIT_SP;
        float px = TypedValue.applyDimension(unit, sp, metrics);
        LogCat.e(" dx = " + sp + " px = " + px);
    }
}
