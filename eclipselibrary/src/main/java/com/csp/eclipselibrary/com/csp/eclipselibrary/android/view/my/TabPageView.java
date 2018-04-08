package com.csp.eclipselibrary.com.csp.eclipselibrary.android.view.my;

import com.common.android.control.util.LogUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

/**
 * 页面指示器
 * 自定义[View]步骤:
 *   1) 绘制[View]: onDraw()
 *   2) 取得屏幕信息: onSizeChanged()
 * @version 1.0.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016-10-15 16:02:35
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class TabPageView extends View {
	static final String		LOG_TAG		= "TabPageView";
	public final static int	RECTANGLE	= 0;			// 矩形
	public final static int	CIRCLE		= 1;			// 圆

	private int		scrollX		= 0;	// [View]左下角的X轴坐标
	private int		scrollY		= 0;	// [View]左下角的Y轴坐标
	private int		position	= 0;	// 被选中的位置
	private float	offset		= 0;	// 偏移量百分比[0, 1)

	private int		shape		= RECTANGLE;	// 形状
	private int		count		= 0;			// 形状个数
	private float	diameter	= 100;			// 单个形状长度
	private float	distance	= 100;			// 各个形状间距离
	private int		borderColor	= Color.BLACK;	// 边框颜色
	private int		fillColor	= Color.RED;	// 实心颜色

	// ========================================
	// 实例域改变
	// ========================================
	public void setPosition(int position) {
		this.position = position;
	}
	
	public void setOffset(float offset) {
		this.offset = offset;
	}
	
	public void setShape(int shape) {
		this.shape = shape;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	// ========================================
	// 构造器
	// ========================================
	public TabPageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		// context.obtainStyledAttributes(attrs, R.style.MyTheme);
		
		
	}

	public TabPageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * [View]的宽高发生变化时执行, 父类为空方法
	 * 获取[View]左下角的坐标
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		LogUtil.logInfo("Size: " + w + ":" + h + ":" + oldw + ":" + oldh);
		// scrollX = 0;
		scrollY = h;
	}

	@Override
	/**
	 * 绘制[View], 父类为空方法
	 */
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 参数: 去掉锯齿

//		float drawScrollX = scrollX + diameter / 2f;
//		float drawScrollY = scrollY - diameter / 2f;

		float drawScrollX = scrollX;
		float drawScrollY = scrollY;
		LogUtil.logInfo(drawScrollX + " : " + drawScrollY);

		// 画空心(边框)形状
		paint.setStyle(Style.STROKE);
		paint.setColor(borderColor);
		for (int i = 0; i < count; i++) {
			drawShape(drawScrollX, drawScrollY, diameter, canvas, paint);
			drawScrollX += diameter + distance;
		}

		// 画实心(选中)形状
		paint.setStyle(Style.FILL);
		paint.setColor(fillColor);

		drawScrollX = scrollX + diameter * 0.2f / 2f + position * (diameter + distance); // 选中的位置
		drawScrollX += (diameter + distance) * offset; // 偏移量
		drawScrollY -= diameter * 0.2f / 2f;
		drawShape(drawScrollX, drawScrollY, diameter * 0.8f, canvas, paint);
	}

	/**
	 * 绘制单个的形状, 传入左下角坐标
	 */
	private void drawShape(float x, float y, float diameter, Canvas canvas, Paint paint) {
		if (shape == RECTANGLE) {
			canvas.drawRect(x, y - diameter, x + diameter, y, paint);
		}
		if (shape == CIRCLE) {
			float centreX = x + diameter / 2;
			float centreY = y - diameter / 2;
			canvas.drawCircle(centreX, centreY, diameter / 2f, paint);
		}
	}

	/**
	 * [View]刷新
	 */
	public void refresh() {
		invalidate();//此方法一旦调用会重写执行此view对象的onDraw
	}

}
