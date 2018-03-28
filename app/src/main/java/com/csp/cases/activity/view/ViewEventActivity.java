package com.csp.cases.activity.view;

import android.gesture.GestureOverlayView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.csp.cases.base.activity.BaseGridActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>Create Date: 2017/10/17
 * <p>Modify Date: 无
 *
 * @author 永丰基地
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ViewEventActivity extends BaseGridActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("常见参数", "getParams", ""));

		return items;
	}

	/**
	 * TODO 常见参数
	 * View: getLeft(), getRight(), getTop(), getBottom(), getX(), getY(), getTranslationX(), getTranslationY()
	 * MotionEvent: getX(), getY(), getRawX(), getRawY()
	 * VelocityTracker, 见{@link #onTouchEvent(MotionEvent)}
	 * GestureDetector
	 * Scroller
	 */
	private void getParams() {
		

		int top = 0;
		int left = 0;

		int touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

		// 手势检测
		// GestureDetector gestureDetector = new GestureDetector(this);
		// gestureDetector.setIsLongpressEnabled(false); // 解决长按屏幕后无法拖动的现象

		String msg = "top: " + top
				+ "left: " + left
				+ "touchSlop: " + touchSlop;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 滑动速度
		VelocityTracker velocityTracker = VelocityTracker.obtain();
		velocityTracker.addMovement(event);

		velocityTracker.computeCurrentVelocity(1000); // 计算最近[1s]的滑动速度
		float xVelocity = velocityTracker.getXVelocity();
		float yVelocity = velocityTracker.getYVelocity();

		velocityTracker.clear();
		velocityTracker.recycle();

		return super.onTouchEvent(event);
	}




	/**
	 * 手势检测
	 */
	public class Test implements GestureDetector.OnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return false;
		}
	}

	public class Test2 implements GestureOverlayView.OnGestureListener {
		@Override
		public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {

		}

		@Override
		public void onGesture(GestureOverlayView overlay, MotionEvent event) {

		}

		@Override
		public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

		}

		@Override
		public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {

		}
	}

	public class Test3 implements GestureDetector.OnDoubleTapListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}
	}
}
