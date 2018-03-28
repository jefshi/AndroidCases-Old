package com.csp.cases.activity.network.retrolfit.hce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 永丰基地 on 2017/5/12.
 */
public class SdkInitReq {
    @SerializedName("txnType")
    private String txnType;
    @SerializedName("userId")
    private String userId;
    @SerializedName("isRoot")
    private String isRoot;
    @SerializedName("deviceModel")
    private String deviceModel;
    @SerializedName("deviceSn")
    private String deviceSn;
    @SerializedName("osType")
    private String osType;
    @SerializedName("osVersion")
    private String osVersion;
    @SerializedName("deviceId")
    private String deviceId;
    @SerializedName("deviceUniqId")
    private String deviceUniqId;
    @SerializedName("mac")
    private String mac;
    @SerializedName("imei")
    private String imei;
    @SerializedName("walletName")
    private String walletName;
    @SerializedName("walletSignature")
    private String walletSignature;
    @SerializedName("walletVersion")
    private String walletVersion;
    @SerializedName("isNFC")
    private String isNFC;
    @SerializedName("isSeFlag")
    private String isSeFlag;
    @SerializedName("seId")
    private String seId;
    @SerializedName("gpsLocation")
    private String gpsLocation;
    @SerializedName("accessPin")
    private String accessPin;
    @SerializedName("deviceHash")
    private String deviceHash;

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceUniqId() {
        return deviceUniqId;
    }

    public void setDeviceUniqId(String deviceUniqId) {
        this.deviceUniqId = deviceUniqId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getWalletSignature() {
        return walletSignature;
    }

    public void setWalletSignature(String walletSignature) {
        this.walletSignature = walletSignature;
    }

    public String getWalletVersion() {
        return walletVersion;
    }

    public void setWalletVersion(String walletVersion) {
        this.walletVersion = walletVersion;
    }

    public String getIsNFC() {
        return isNFC;
    }

    public void setIsNFC(String isNFC) {
        this.isNFC = isNFC;
    }

    public String getIsSeFlag() {
        return isSeFlag;
    }

    public void setIsSeFlag(String isSeFlag) {
        this.isSeFlag = isSeFlag;
    }

    public String getSeId() {
        return seId;
    }

    public void setSeId(String seId) {
        this.seId = seId;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getAccessPin() {
        return accessPin;
    }

    public void setAccessPin(String accessPin) {
        this.accessPin = accessPin;
    }

    public String getDeviceHash() {
        return deviceHash;
    }

    public void setDeviceHash(String deviceHash) {
        this.deviceHash = deviceHash;
    }

    public boolean needEncrypt() {
        return false;
    }

    public void setDevAuthCode(String devAuthCode) {
        return;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '{' +
                "'" + txnType + '\'' +
                ", '" + userId + '\'' +
                ", '" + isRoot + '\'' +
                ", '" + deviceModel + '\'' +
                ", '" + deviceSn + '\'' +
                ", '" + osType + '\'' +
                ", '" + osVersion + '\'' +
                ", '" + deviceId + '\'' +
                ", '" + deviceUniqId + '\'' +
                ", '" + mac + '\'' +
                ", '" + imei + '\'' +
                ", '" + walletName + '\'' +
                ", '" + walletSignature + '\'' +
                ", '" + walletVersion + '\'' +
                ", '" + isNFC + '\'' +
                ", '" + isSeFlag + '\'' +
                ", '" + seId + '\'' +
                ", '" + gpsLocation + '\'' +
                ", '" + accessPin + '\'' +
                ", '" + deviceHash + '\'' +
                ", " + needEncrypt() +
                '}';
    }
}
