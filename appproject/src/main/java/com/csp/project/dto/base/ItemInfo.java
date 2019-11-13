package com.csp.project.dto.base;

import android.app.Activity;

/**
 * 项目信息
 */
public class ItemInfo {
    private String name; // 项目名称
    private String description; // 项目描述
    private boolean emphasize; // 是否需要强调
    private Class<? extends Activity> jumpClass; // 跳转Class
    private String methodName; // 执行的方法


    public ItemInfo(String name, Class<? extends Activity> jumpClass) {
        this.name = name;
        this.jumpClass = jumpClass;
    }

    public ItemInfo(String name, String description, Class<? extends Activity> jumpClass) {
        this.name = name;
        this.description = description;
        this.jumpClass = jumpClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmphasize() {
        return emphasize;
    }

    public void setEmphasize(boolean emphasize) {
        this.emphasize = emphasize;
    }

    public Class<? extends Activity> getJumpClass() {
        return jumpClass;
    }

    public void setJumpClass(Class<? extends Activity> jumpClass) {
        this.jumpClass = jumpClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", emphasize=" + emphasize +
                ", jumpClass=" + jumpClass +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}