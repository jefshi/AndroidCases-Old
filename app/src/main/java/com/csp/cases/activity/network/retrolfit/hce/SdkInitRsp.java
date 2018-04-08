package com.csp.cases.activity.network.retrolfit.hce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 永丰基地 on 2017/5/12.
 */
public class SdkInitRsp {
	@SerializedName("rspCode")
	private String rspCode;
	@SerializedName("rspMsg")
	private String rspMsg;
	@SerializedName("txnType")
	private String txnType;
	@SerializedName("userId")
	private String userId;
	@SerializedName("mpaId")
	private String mpaId;
	@SerializedName("confKey")
	private String confKey;
	@SerializedName("macKey")
	private String macKey;

	public String obtainRspCode() {
		return rspCode;
	}

	public String obtainRspMsg() {
		return rspMsg;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

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

	public String getMpaId() {
		return mpaId;
	}

	public void setMpaId(String mpaId) {
		this.mpaId = mpaId;
	}

	public String getConfKey() {
		return confKey;
	}

	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}

	public String getMacKey() {
		return macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + '{' +
				"'" + rspCode + '\'' +
				", '" + rspMsg + '\'' +
				", '" + txnType + '\'' +
				", '" + userId + '\'' +
				", '" + mpaId + '\'' +
				", '" + confKey + '\'' +
				", '" + macKey + '\'' +
				", '" + obtainRspCode() + '\'' +
				", '" + obtainRspMsg() + '\'' +
				'}';
	}
}
