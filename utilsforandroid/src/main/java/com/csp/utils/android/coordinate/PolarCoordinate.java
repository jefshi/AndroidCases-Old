package com.csp.utils.android.coordinate;

import android.graphics.Point;
import android.view.MotionEvent;

public class PolarCoordinate {

    /**
     * @param begin 矢量起点
     * @param end   矢量末点
     * @return 以起点为原点的极坐标
     */
    public static Polar toPolar(Point begin, Point end) {
        int dx = end.x - begin.x;
        int dy = end.y - begin.y;

        double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double theta = Math.atan(dx / dy);
        return new Polar(radius, theta);
    }

    /**
     * @param center 围绕中心点
     * @param begin  矢量起点
     * @param end    矢量末点
     * @return true 是顺时针
     */
    public static boolean isClockwise(Point center, Point begin, Point end) {
        Polar pBegin = toPolar(center, begin);
        Polar pEnd = toPolar(center, end);
        return pEnd.theta >= pBegin.theta;
    }
}
