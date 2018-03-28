package cc.rengu.src.kitkat.android.view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Description:
 * <p>Create Date: 2017/10/17
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since Android 19
 */
public final class MotionEvent extends InputEvent implements Parcelable {

	/**
	 * 事件坐标
	 * getX()/getY()：相对于当前[View]的[x, y]
	 * getRawX()/getRawY()：相对于手机屏幕的[x, y]
	 */
	public final float getX() {
		return 0;
	}

	public final float getY() {
		return 0;
	}

	public final float getRawX() {
		return 0;
	}

	public final float getRawY() {
		return 0;
	}

	/**
	 * Parcelable
	 */
	public static final Parcelable.Creator<MotionEvent> CREATOR = null;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
}
