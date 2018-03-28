package com.csp.eclipselibrary.com.csp.eclipselibrary.android.view.my;

import com.common.R;
import com.common.android.view.util.MetricsUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * A-Z 字母导航
 * @version 1.0
 * @author csp
 * <p style='font-weight:bold'>Date:</p> 2016年12月13日 下午7:06:49
 * <p style='font-weight:bold'>AlterDate:</p>
 */
public class LetterNavigateView extends View {

	private int			color;
	private Paint		paint;
	private String[]	letter;

	public LetterNavigateView(Context context) {
		super(context);
		initPaint();
	}

	public LetterNavigateView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.LetterNavigateView);
		color = tArray.getColor(R.styleable.LetterNavigateView_pressBackgrondColor, Color.BLACK);

		// 获取尺寸
		// tArray.getDimensionPixelSize(index, defValue);

		tArray.recycle(); // 回收对象内容, 以便复用
		initPaint();
	}

	public LetterNavigateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	/**
	 * 初始化画笔
	 */
	public void initPaint() {
		letter = new String[27];
		for (int i = 0; i < letter.length - 1; i++) {
			letter[i] = String.valueOf((char) (i - 1 + 'A'));
		}
		letter[26] = "#"; // 非中文, 非英文打头, 如数字, 特殊符号等

		float fontSize = MetricsUtil.getPxByDip(this, TypedValue.COMPLEX_UNIT_SP, 12);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(fontSize);
	}

	/**
	 * 自定义控件用来测量高度、宽度的方式与View中所, 默认测量高宽的方式不一致时，才需要重写该方法
	 * 
	 * 默认规则
	 * 1) 如果给View指定了明确的尺寸(例如：60dp), 在设定尺寸时就按照给定值来设定
	 * 2) 如果给View指定了[wrap_content][match_parent], 在设定尺寸时一律按照match_parent来设定
	 * 
	 * 模式
	 * MeasureSpec.AT_MOST: [wrap_content]
	 * MeasureSpec.EXACTLY: 80dp, [match_parent]
	 * MeasureSpec.UNSPECIFIED: 
	 * 
	 */
	@Override
	@SuppressLint("DrawAllocation")
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 模式: 参数[widthMeasureSpec]的后30位为具体宽度值, 前2位为模式类型
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		if (specMode == MeasureSpec.AT_MOST) {
			// 计算宽度: 左内边距 + 内容宽度 + 右内边距
			int paddingLeft = getPaddingLeft();
			int paddingRight = getPaddingRight();

			int contentWidth = 0;
			Rect rect = new Rect();
			for (int i = 0; i < letter.length; i++) {
				paint.getTextBounds(letter[0], 0, letter[0].length(), rect);
				contentWidth = Math.max(rect.width(), contentWidth);
			}

			int width = paddingLeft + paddingRight + contentWidth;
			setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
		}
	}

	@Override
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 控件高宽
		int width = getWidth();
		int height = getHeight() / letter.length;

		for (int i = 0; i < letter.length; i++) {

			// 文字高宽
			Rect bounds = new Rect();
			paint.getTextBounds(letter[i], 0, letter[i].length(), bounds);

			// 文字定位(近)
			float x = width / 2 - bounds.width() / 2;
			float y = height / 2 + bounds.height() / 2 + height * i;

			// 画文本
			canvas.drawText(letter[i], x, y, paint);
		}
	}

	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();

		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
			setBackgroundColor(Color.GRAY);

			float y = event.getY();
			int index = (int) (y * letter.length / getHeight());

			if (listener != null && index > -1 && index < letter.length) {
				listener.OnTouchLetter(letter[index]);
			}
		}

		if (action == MotionEvent.ACTION_UP) {
			setBackgroundColor(Color.TRANSPARENT); // 设置透明
		}

		return true;
	}

	/**
	 * 接口: 触摸字母事件
	 * @version 1.0
	 * @author csp
	 */
	public interface OnTouchLetterListener {
		public void OnTouchLetter(String letter);
	}

	private OnTouchLetterListener listener;

	public void setOnTouchLetterListener(OnTouchLetterListener listener) {
		this.listener = listener;
	}
}
