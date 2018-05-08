package com.csp.cases.activity.network.visit.retrolfit.hce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 永丰基地 on 2017/5/12.
 */
public class SdkInitNotifyReq {
    @SerializedName("txnType")
    private String txnType;
    @SerializedName("mpaId")
    private String mpaId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("notifyRlt")
    private String notifyRlt;

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getMpaId() {
        return mpaId;
    }

    public void setMpaId(String mpaId) {
        this.mpaId = mpaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNotifyRlt() {
        return notifyRlt;
    }

    public void setNotifyRlt(String notifyRlt) {
        this.notifyRlt = notifyRlt;
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
                ", '" + mpaId + '\'' +
                ", '" + userId + '\'' +
                ", '" + notifyRlt + '\'' +
                ", " + needEncrypt() +
                '}';
    }
}
