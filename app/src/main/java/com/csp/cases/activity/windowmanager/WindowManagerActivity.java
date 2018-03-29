package com.csp.cases.activity.windowmanager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.csp.cases.R;
import com.csp.cases.activity.view.arcmenu.ArcAdapter;
import com.csp.cases.activity.view.arcmenu.ArcLayout;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;
import com.csp.library.android.util.display_metrics.DisplayMetricsUtil;
import com.csp.library.android.util.log.LogCat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshp on 2018/3/29.
 */

public class WindowManagerActivity extends BaseListActivity {

    private WindowManager wm;
    private WindowManager.LayoutParams wlp;

    @Override
    public List<ItemInfo> getItemInfos() {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wlp = new WindowManager.LayoutParams();
        initParams(this);


        List<ItemInfo> items = new ArrayList<>();
        items.add(new ItemInfo("显示悬浮窗", "showFloatWindow", ""));
        items.add(new ItemInfo("隐藏悬浮窗", "hideFloatWindow", ""));
        items.add(new ItemInfo("自定义[ArcMenu]", "showarcmenu", ""));

        return items;
    }

    private void initParams(Context context) {
        int size = DisplayMetricsUtil.dipToPx(this, 50);

        wlp.type = WindowManager.LayoutParams.TYPE_PHONE;
        wlp.format = PixelFormat.TRANSLUCENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wlp.width = size;
        wlp.height = size;
        wlp.gravity = Gravity.CENTER;
        wlp.x = 0;
        wlp.y = 0;
    }

    private FrameLayout frameLayout;
    private ImageView img;

    private int lastX;
    private int lastY;
    private int minmove = 10;

    private void showFloatWindow() {
        int size = DisplayMetricsUtil.dipToPx(this, 40);

//        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
//        wlp.height = wlp.width = radius;

        frameLayout = new FrameLayout(this) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                LogCat.e("onTouch");

                int x = (int) event.getX();
                int y = (int) event.getY();


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int xmove = x - lastX;
                        int ymove = y - lastY;


                            wlp.x  += xmove;
                            wlp.y  += ymove;
                            wm.updateViewLayout(this, wlp);

//                            lastX = x;
//                            lastY = y;


                        break;


                }
                return true; // super.onTouchEvent(event);
            }
        };

        img = new ImageView(this);
        img.setImageResource(R.drawable.recorder_icon);
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(size, size);

        frameLayout.addView(img);


//        img.setLayoutParams(wlp);

        wm.addView(frameLayout, wlp);
    }


    private void hideFloatWindow() {
        wm.removeView(frameLayout);
    }

    private void showarcmenu() {
        Integer[] resIds = new Integer[]{
                R.drawable.recorder_goon,
                R.drawable.recorder_pause,
                R.drawable.recorder_stop,
                R.drawable.recorder_start,
                R.drawable.recorder_browser
        };

        ArcAdapter adapter = new ArcAdapter(this, resIds);

//        ArcLayout arcLayout = (ArcLayout) findViewById(R.id.arc);
//        arcLayout.setAdapter(adapter);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArcLayout arcLayout = (ArcLayout) inflater.inflate(R.layout.view_sample, null, false);
        arcLayout.setAdapter(adapter);
//        arcLayout.show();
    }
}
