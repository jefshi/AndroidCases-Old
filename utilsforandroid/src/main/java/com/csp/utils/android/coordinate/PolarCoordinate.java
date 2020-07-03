package com.csp.utils.android.coordinate;

import android.graphics.Point;

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
        double theta;
        if (dx == 0)
            theta = Math.PI / 2;
        else
            theta = Math.atan(dy / dx);
        return new Polar(radius, theta);
    }
}
