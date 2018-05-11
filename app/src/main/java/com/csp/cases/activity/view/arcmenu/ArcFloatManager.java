package com.csp.cases.activity.view.arcmenu;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.csp.cases.R;
import com.csp.cases.activity.view.other.RECState;
import com.csp.utils.android.MetricsUtil;
import com.csp.utils.android.aaaTemp.Utils;

/**
 * Created by csp on 2018/4/2 002.
 */

public class ArcFloatManager {
    private WindowManager wManager;
    private Context mContext;

    public ArcFloatManager(Context context) {
        init(context);
    }

    private Handler mainHandler;

    private ArcMenu arcMenu;
    private ArcFloat02 arcFloat;
    private ArcExitView arcExitView;
    private RelativeLayout arcTipView;

    private static final int MOVE_TO_EDGE = 10010; // 移动靠边
    private static final int HIDE_TO_EDGE = 10011; // 靠边N秒缩小
    private static final int HIDE_TO_ARC_MENU = 10012; // 自动折叠



    private void init(Context context) {
        wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initMetrics();




        initViews(context);

        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                synchronized (ArcFloatManager.this) {
                    switch (msg.what) {
                        case MOVE_TO_EDGE:
                            arcFloat.movingToEdge((boolean) msg.obj);
                            break;
                        case HIDE_TO_EDGE:
                            arcFloat.reduceArcFloat();
                            break;
                        case HIDE_TO_ARC_MENU:
                            arcMenu.switchShow(false);
                            arcFloat.downAnim();
                            mainHandler.sendEmptyMessageDelayed(HIDE_TO_EDGE, 2000);
                            break;
                    }
                }
            }
        };
    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;

        arcFloat = new ArcFloat02(context);
        arcMenu = (ArcMenu) inflater.inflate(R.layout.view_arclayout, null, false);
        arcExitView = new ArcExitView(context);

        Integer[] resIds = new Integer[]{
                R.drawable.recorder_goon,
                R.drawable.recorder_pause,
                R.drawable.recorder_stop,
                R.drawable.recorder_start,
                R.drawable.recorder_browser
        };
        arcMenu.setAdapter(new ArcAdapter(context, resIds));
        arcMenu.switchShow(false);

        arcFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams wlp = arcFloat.getWindowLayoutParams();
                arcMenu.switchShow(wlp.x + wlp.width / 2, wlp.y + wlp.height / 2);
            }
        });


    }


    /**
     * TODO 未整理的
     * CSP: 更新位置
     */
    private void updateViewLayout(View view) {
        if (view instanceof IWindowLayoutParams) {
            IWindowLayoutParams iwlpView = (IWindowLayoutParams) view;

            WindowManager.LayoutParams wlp = iwlpView.getWindowLayoutParams();

            if (wlp.x < 0 || wlp.x > mScreenWidth
                    || wlp.y < 0 || wlp.y > mScreenHeight) {

                if (wlp.x < 0)
                    wlp.x = 0;
                else if (wlp.x > mScreenWidth)
                    wlp.x = mScreenWidth;

                if (wlp.y < 0)
                    wlp.y = 0;
                else if (wlp.y > mScreenHeight)
                    wlp.y = mScreenHeight;


                iwlpView.setWindowLayoutParams(wlp.x, wlp.y);
            }

            wManager.updateViewLayout(view, iwlpView.getWindowLayoutParams());
        }
    }

    /**
     * 初始化屏幕尺寸
     */
    private void initMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;

        // TODO 改为试一试
//        mScreenWidth = metrics.widthPixels;
//        mScreenHeight = metrics.heightPixels;

        Point point = new Point();
        wManager.getDefaultDisplay().getSize(point);
        mScreenWidth = point.x;
        mScreenHeight = point.y;

        mStatusBarHeight = MetricsUtil.getStatusBarHeight(mContext);
    }


    boolean portrait; // 默认竖屏
    private float density;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mStatusBarHeight; // 状态栏高度




    /**
     * 显示并附着在悬浮窗上
     *
     * @param attach true: 显示并附着在悬浮窗上
     */
    public void attach(boolean attach) {
        if (attach) {
//            wManager.addView(arcTipView, new WindowManager.LayoutParams());
            wManager.addView(arcExitView, arcExitView.getWindowLayoutParams());
            wManager.addView(arcMenu, arcMenu.getWindowLayoutParams());
            wManager.addView(arcFloat, arcFloat.getWindowLayoutParams());

            // TODO
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateViewLayout(arcFloat);
//                    wManager.updateViewLayout(arcFloat, arcFloat.getWindowLayoutParams());
                }
            }, 10);

        } else {
            wManager.removeView(arcExitView);
            wManager.removeView(arcMenu);
            wManager.removeView(arcFloat);
            arcExitView = null;
            arcMenu = null;
        }
    }


    /**
     * 靠边动画
     * @param moveLeft
     */
    public void moveEdge(boolean moveLeft) {
        mainHandler.sendMessage(mainHandler.obtainMessage(MOVE_TO_EDGE, moveLeft));
    }

    public void movingEdge(boolean moveLeft, boolean movingtoEdge) {
        updateViewLayout(arcFloat);
        if (movingtoEdge)
            mainHandler.sendMessageDelayed(
                    mainHandler.obtainMessage(MOVE_TO_EDGE, moveLeft),
                    10);
    }

    /**
     * 拖拽悬浮窗
     * @param x
     * @param y
     */
    public void dragAnywhere(float x, float y) {
//        wManager.updateViewLayout(arcFloat, wlp);


        updateViewLayout(arcFloat);
        arcMenu.switchShow(false);


        if (!RECState.isIsREC()) {
            arcExitView.show();
        }


        WindowManager.LayoutParams wlp = arcFloat.getWindowLayoutParams();


        /** 移动到了删除控件的范围内 **/
        if (arcExitView.isTouchPointInView(wlp.x, wlp.y)) {
            // 同时震动一下
//            if (!isVbirated) {
                Utils.vibrate(mContext, 200);
//            }
//            isVbirated = true;
//            isGoToDelete = true;
            int[] center = new int[2];
            arcExitView.getCenterXY(center);

            wlp.x = center[0] - wlp.width / 2;
            wlp.y = center[1] - wlp.height / 2;
            arcFloat.setWindowLayoutParams(wlp.x, wlp.y);
            updateViewLayout(arcFloat);

//            mWindowManager.updateViewLayout(iconFloatFl, layoutParams);
        } else {
//            isVbirated = false;
//            isGoToDelete = false;


//            wlp.x = x - arcFloat.getWidth() / 2;
//            wlp.y = y - arcFloat.getHeight();
//
//            updateViewPosition(x - iconFloatFl.getWidth() / 2, y - iconFloatFl.getHeight());
        }
    }

    /**
     * 手势松开
     */
    public void motionUp() {
        arcExitView.hide();
        arcFloat.moveToEdge();
    }

    // TODO 录制事件处理

}
