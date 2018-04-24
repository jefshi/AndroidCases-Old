package com.csp.cases.activity.system.appinfo;

import android.graphics.drawable.Drawable;

/**
 * Created by chenshp on 2018/4/11.
 */

public class AppInfo {
    private String packageName;
    private String label;
    private Drawable icon;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageName='" + packageName + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
