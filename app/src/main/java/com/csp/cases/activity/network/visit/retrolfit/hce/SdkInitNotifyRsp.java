package com.csp.cases.activity.network.visit.retrolfit.hce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 永丰基地 on 2017/5/12.
 */
public class SdkInitNotifyRsp {
	@SerializedName("rspCode")
	private String rspCode;
	@SerializedName("rspMsg")
	private String rspMsg;
	@SerializedName("txnType")
	private String txnType;
	@SerializedName("reloadFlag")
	private String reloadFlag;

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

	public String getReloadFlag() {
		return reloadFlag;
	}

	public void setReloadFlag(String reloadFlag) {
		this.reloadFlag = reloadFlag;
	}

	@Override
	@SuppressWarnings({"ConstantConditions", "PointlessBooleanExpression"})
	public String toString() {
		return this.getClass().getSimpleName() + '{' +
				"'" + rspCode + '\'' +
				", '" + rspMsg + '\'' +
				", '" + txnType + '\'' +
				", '" + reloadFlag + '\'' +
				'}';
	}
}
