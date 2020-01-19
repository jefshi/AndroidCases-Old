package com.csp.utils.android.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopupWindow 优化
 * Created by csp on 2019/11/03
 */
public class PopupWindowOptimize extends PopupWindow {

    public PopupWindowOptimize(Context context) {
        super(context);
    }

    public PopupWindowOptimize(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupWindowOptimize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PopupWindowOptimize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PopupWindowOptimize() {
    }

    public PopupWindowOptimize(View contentView) {
        super(contentView);
    }

    public PopupWindowOptimize(int width, int height) {
        super(width, height);
    }

    public PopupWindowOptimize(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public PopupWindowOptimize(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    /**
     * 根据需要在目标控件的不同方位上，显示 PopupWindow
     * 具体见：https://www.cnblogs.com/jzyhywxz/p/7039503.html
     *
     * @param layoutGravity PopupWindow 在目标控件上的位置
     * @see #showAsDropDown(View, int, int)
     */
    @SuppressWarnings("UnusedReturnValue")
    public PopupWindow showBashOfAnchor(final View anchor, final PopupWindowLayoutGravity layoutGravity, final int xoff, final int yoff) {
        showAsDropDown(anchor); // 只是为了绘制内容，以便获取正确的高宽

        final PopupWindow popupWindow = this;
        final View view = getContentView();

        view.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        dismiss();
                        int[] offset = layoutGravity.getOffset(anchor, popupWindow);
                        showAsDropDown(anchor, offset[0] + xoff, offset[1] + yoff);
                    }
                });
        return this;
    }

    /**
     * 让 PopupWindow 以外的区域阴影显示
     *
     * @param window      PopupWindow 所在的 Window 对象
     * @param popupWindow PopupWindow 对象
     * @param alpha       透明度
     */
    public static void setWindowAlpha(@NonNull final Window window, @NonNull PopupWindow popupWindow, float alpha) {
        if (alpha < 0 || 1f - alpha < 0.001)
            return;

        setWindowAlpha(window, alpha);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(window, 1f);
            }
        });
    }

    /**
     * 设置窗口的透明度
     *
     * @param window 窗口对象
     * @param alpha  透明度
     */
    private static void setWindowAlpha(Window window, float alpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
    }
}
