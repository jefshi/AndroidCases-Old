package com.csp.cases.activity.network.retrolfit.phone;

/**
 * Class: PhoneHardwareInfo
 * Created by se7enTina on 2015/11/24.
 * Description: 设备硬件信息
 */
@SuppressWarnings("ALL")
public class PhoneHardwareInfo {

    // 终端型号
    private String terminalType;
    // 终端序列号
    private String terminalSn;
    // android ID
    private String androidId;
    // IMEI
    private String imei;
    //MAC
    private String mac;
    //是否支持NFC
    private String isSupportNfc;

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalSn() {
        return terminalSn;
    }

    public void setTerminalSn(String terminalSn) {
        this.terminalSn = terminalSn;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIsSupportNfc() {
        return isSupportNfc;
    }

    public void setIsSupportNfc(String isSupportNfc) {
        this.isSupportNfc = isSupportNfc;
    }

    @Override
    public String toString() {
        return "PhoneHardwareInfo{" +
                "terminalType='" + terminalType + '\'' +
                ", terminalSn='" + terminalSn + '\'' +
                ", androidId='" + androidId + '\'' +
                ", imei='" + imei + '\'' +
                ", mac='" + mac + '\'' +
                ", isSupportNfc='" + isSupportNfc + '\'' +
                '}';
    }
}
