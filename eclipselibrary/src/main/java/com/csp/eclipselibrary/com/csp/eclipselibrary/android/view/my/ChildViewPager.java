package com.csp.eclipselibrary.com.csp.eclipselibrary.android.view.my;

import com.common.android.control.util.LogUtil;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager, 用于解决ViewPager嵌套导致的滑动冲突
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016年12月13日 下午7:07:42
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class ChildViewPager extends ViewPager {
	static PointF	downPoint	= new PointF();	// 触摸时按下的点 
	static PointF	movePoint	= new PointF();	// 触摸时松开的点 
	static PointF	upPoint		= new PointF();	// 触摸时松开的点 

	public ChildViewPager(Context context) {
		super(context);
	}

	public ChildViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true); // 本事件由当前UI处理
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		super.onTouchEvent(ev);

		LogUtil.logInfo("adpter:" + getAdapter());
		LogUtil.logInfo("getCurrentItem():" + getCurrentItem());

		boolean isLast = getCurrentItem() == getAdapter().getCount() - 1;
		boolean isFirst = getCurrentItem() == 0;
		LogUtil.logInfo("本事件由当前UI处理:" + isLast + "." + isFirst);

		if (isLast || isFirst) {
			// 记录按下的点
			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
				downPoint.set(ev.getX(), ev.getY());
//				movePoint.set(ev.getX(), ev.getY());
			}
			LogUtil.logInfo("downPoint:" + downPoint.x + " : " + downPoint.y);

			// 记录松开的点
			if (ev.getAction() == MotionEvent.ACTION_MOVE) {
				movePoint.set(ev.getX(), ev.getY());
			}
//			if (ev.getAction() == MotionEvent.ACTION_UP) {
//				movePoint.set(ev.getX(), ev.getY());
//			}
			
			LogUtil.logInfo("movePoint:" + movePoint.x + " : " + movePoint.y);

			// 处理尾页右滑事件
			boolean isToLeft = movePoint.x > downPoint.x;
			boolean isToRight = movePoint.x < downPoint.x;

			LogUtil.logInfo("页面<-:" + isToLeft);
			LogUtil.logInfo("页面->:" + isToRight);
			LogUtil.logInfo("isFirst + 页面<-:" + (isFirst && isToLeft));
			LogUtil.logInfo("isLast + 页面->:" + (isLast && isToRight));

			if ((isFirst && isToLeft)
					|| (isLast && isToRight)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
