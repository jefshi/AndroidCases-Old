package com.csp.cases.activity.network.retrolfit.phone;

/**
 * Class: PhoneInfo
 * Created by se7enTina on 2015/11/24.
 * Description: 手机设备信息
 */
public class PhoneInfo {

    private PhoneHardwareInfo phoneHardwareInfo;
    private PhoneSoftwareInfo phoneSoftwareInfo;


    public PhoneSoftwareInfo getPhoneSoftwareInfo() {
        return phoneSoftwareInfo;
    }

    public void setPhoneSoftwareInfo(PhoneSoftwareInfo phoneSoftwareInfo) {
        this.phoneSoftwareInfo = phoneSoftwareInfo;
    }

    public PhoneHardwareInfo getPhoneHardwareInfo() {
        return phoneHardwareInfo;
    }

    public void setPhoneHardwareInfo(PhoneHardwareInfo phoneHardwareInfo) {
        this.phoneHardwareInfo = phoneHardwareInfo;
    }


    @Override
    public String toString() {
        return "PhoneInfo{" +
                "phoneHardwareInfo=" + phoneHardwareInfo +
                ", phoneSoftwareInfo=" + phoneSoftwareInfo +
                '}';
    }
}
