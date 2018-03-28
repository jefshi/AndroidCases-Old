package cc.rengu.src.kitkat.android.view;

import android.view.ViewDebug;

/**
 * Description:
 * <p>Create Date: 2017/10/17
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since Android 19
 */
@SuppressWarnings("unused")
public class View {
	/**
	 * 原始顶点坐标，相对于父容器，左上角，右下角
	 * getLeft(), getRight(), getTop(), getBottom()
	 * 偏移顶点坐标，见[getX(), getY(), getTranslationX(), getTranslationY()]
	 */
	@ViewDebug.ExportedProperty(category = "layout")
	protected int mLeft;

	@ViewDebug.ExportedProperty(category = "layout")
	protected int mRight;

	@ViewDebug.ExportedProperty(category = "layout")
	protected int mTop;

	@ViewDebug.ExportedProperty(category = "layout")
	protected int mBottom;

	/**
	 * Android 3.0，偏移顶点坐标，相对于父容器，[x, y]左上角坐标, [translationX, translationY]左上角偏移量，默认为0
	 * [View]平移等操作，[top, left]不改变，[x, y, translationX, translationY]改变
	 * 换算规则：x = left + translationX, y = top + translationY
	 */
	public float getX() {
		return 0;
	}

	public void setX(float x) {
	}

	public float getY() {
		return 0;
	}

	public void setY(float y) {
	}

	public float getTranslationX() {
		return 0;
	}

	public void setTranslationX(float translationX) {
	}

	public float getTranslationY() {
		return 0;
	}

	public void setTranslationY(float translationX) {
	}

	/**
	 * 原始顶点坐标
	 */
	public int getLeft() {
		return mLeft;
	}

	public int getRight() {
		return mRight;
	}

	public int getTop() {
		return mTop;
	}

	public int getBottom() {
		return mBottom;
	}
}
