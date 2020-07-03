package com.csp.utils.android.coordinate;

import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

/**
 * 笛卡尔坐标系（直角坐标系）
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class CartesianCoordinate {

    public static PointF toPoint(MotionEvent e) {
        return new PointF(e.getX(), e.getY());
    }

    public static void switchPoint(PointF result, MotionEvent e) {
        result.set(e.getX(), e.getY());
    }

    public static PointF toPoint(View v) {
        int top = v.getTop();
        int left = v.getLeft();
        int width = v.getWidth();
        int height = v.getHeight();
        return new PointF(top + width / 2f, left + height / 2f);
    }

    /**
     * 极坐标 -> 笛卡尔坐标
     */
    public static PointF toPoint(PointF center, Polar polar) {
        PointF result = new PointF();
        switchPoint(center, polar, result);
        return result;
    }

    /**
     * 极坐标 -> 笛卡尔坐标
     */
    public static void switchPoint(PointF center, Polar polar, PointF result) {
        float dx = (float) (polar.radius * Math.cos(polar.radian));
        float dy = (float) (polar.radius * Math.sin(polar.radian));
        result.set(center.x + dx, center.y - dy);
    }

    /**
     * @param center 围绕中心点
     * @param begin  矢量起点
     * @param end    矢量末点
     * @return true 是顺时针
     */
    public static boolean isClockwise(PointF center, PointF begin, PointF end) {
        // 根据两向量的叉乘来判断顺逆时针
        return (begin.x - center.x) * (end.y - center.y) > (begin.y - center.y) * (end.x - center.x);
    }

    /**
     * 计算两矢量间的夹角，矢量(cen, first)到矢量(cen, second)的旋转角度（弧度）
     *
     * @return 矢量1 到矢量2 的旋转角度，> 0，逆时针，< 0，顺时针
     */
    public static float calIncludedAngle(PointF cen, PointF first, PointF second) {
        float dx1, dx2, dy1, dy2;

        dx1 = first.x - cen.x;
        dy1 = first.y - cen.y;
        dx2 = second.x - cen.x;
        dy2 = second.y - cen.y;

        // 计算三边的平方
        float ab2 = (second.x - first.x) * (second.x - first.x) + (second.y - first.y) * (second.y - first.y);
        float oa2 = dx1 * dx1 + dy1 * dy1;
        float ob2 = dx2 * dx2 + dy2 * dy2;

        // 根据两向量的叉乘来判断顺逆时针
        boolean isClockwise = ((first.x - cen.x) * (second.y - cen.y) - (first.y - cen.y) * (second.x - cen.x)) > 0;

        // 根据余弦定理计算旋转角的余弦值
        double cosDegree = (oa2 + ob2 - ab2) / (2 * Math.sqrt(oa2) * Math.sqrt(ob2));

        // 异常处理，因为算出来会有误差绝对值可能会超过一，所以需要处理一下
        if (cosDegree > 1) {
            cosDegree = 1;
        } else if (cosDegree < -1) {
            cosDegree = -1;
        }

        // 计算弧度
        double radian = Math.acos(cosDegree);

        // 计算旋转过的角度，顺时针为正，逆时针为负
        return (float) (isClockwise ? Math.toDegrees(radian) : -Math.toDegrees(radian));
    }
}