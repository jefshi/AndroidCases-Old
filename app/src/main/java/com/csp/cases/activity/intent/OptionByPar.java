package com.csp.cases.activity.intent;

import android.os.Parcel;
import android.os.Parcelable;

public class OptionByPar implements Parcelable {
	public int x;
	public int y;

	public OptionByPar(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * 序列化成员
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(x);
		dest.writeInt(y);
	}

	/**
	 * 反序列化, Parcelable.Creator<T> 实现
	 * 必须是为类域: CREATOR, 否则报运行时异常
	 * 由于[Bundle]实现了[Parcelable]接口, 所以可参考它
	 */
	public static final Creator<OptionByPar> CREATOR = new Creator<OptionByPar>() {

		@Override
		public OptionByPar[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public OptionByPar createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
