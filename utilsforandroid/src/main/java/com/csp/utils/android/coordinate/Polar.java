package com.csp.utils.android.coordinate;

public class Polar {
    public double radius;
    public double theta;

    public Polar(double radius, double theta) {
        this.radius = radius;
        this.theta = theta;
    }

    /**
     * Offset the polar's coordinates
     */
    public final void offset(double radius, double theta) {
        this.radius += radius;
        this.theta += theta;
    }


}
