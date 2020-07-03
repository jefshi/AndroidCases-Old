package com.csp.utils.android.coordinate;

public class Polar {
    public double radius;
    public double radian;

    public Polar(double radius, double radian) {
        this.radius = radius;
        this.radian = radian;
    }

    /**
     * Offset the polar's coordinates
     */
    public final void offset(double radius, double radian) {
        this.radius += radius;
        this.radian += radian;
    }
}
