package com.csp.cases.activity.network.visit.retrolfit.phone;

/**
 * Class: PhoneSoftwareInfo
 * Created by se7enTina on 2015/11/24.
 * Description: 手机软件信息
 */
@SuppressWarnings("ALL")
public class PhoneSoftwareInfo {

    // 终端操作系统类型
    private String mobileOsType;

    // 操作系统版本
    private String mobileOsVersion;

    // 移动应用名称
    private String mobileAppName;

    // 移动应用签名信息
    private String mobileAppSign;

    // 移动应用版本
    private String mobileAppVersion;

    public String getMobileOsType() {
        return mobileOsType;
    }

    public void setMobileOsType(String mobileOsType) {
        this.mobileOsType = mobileOsType;
    }

    public String getMobileOsVersion() {
        return mobileOsVersion;
    }

    public void setMobileOsVersion(String mobileOsVersion) {
        this.mobileOsVersion = mobileOsVersion;
    }

    public String getMobileAppName() {
        return mobileAppName;
    }

    public void setMobileAppName(String mobileAppName) {
        this.mobileAppName = mobileAppName;
    }

    public String getMobileAppSign() {
        return mobileAppSign;
    }

    public void setMobileAppSign(String mobileAppSign) {
        this.mobileAppSign = mobileAppSign;
    }

    public String getMobileAppVersion() {
        return mobileAppVersion;
    }

    public void setMobileAppVersion(String mobileAppVersion) {
        this.mobileAppVersion = mobileAppVersion;
    }

    @Override
    public String toString() {
        return "PhoneSoftwareInfo{" +
                "mobileOsType='" + mobileOsType + '\'' +
                ", mobileOsVersion='" + mobileOsVersion + '\'' +
                ", mobileAppName='" + mobileAppName + '\'' +
                ", mobileAppSign='" + mobileAppSign + '\'' +
                ", mobileAppVersion='" + mobileAppVersion + '\'' +
                '}';
    }
}
