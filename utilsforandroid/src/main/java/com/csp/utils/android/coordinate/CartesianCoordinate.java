package com.csp.utils.android.coordinate;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * 笛卡尔坐标系（直角坐标系）
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class CartesianCoordinate {

    public static boolean is(Point center, Point begin, Point end) {



        return false;
    }

    public static Point toPoint(MotionEvent e) {
        return new Point((int) e.getX(), (int) e.getY());
    }

    public static Point toPoint(View v) {
        int top = (int) v.getTop();
        int left = v.getLeft();
        int width = v.getWidth();
        int height = v.getHeight();
        return new Point(top + width / 2, left + height / 2);
    }
}
