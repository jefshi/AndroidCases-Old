package com.csp.cases.activity.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.csp.cases.R;
import com.csp.cases.base.activity.BaseListActivity;
import com.csp.cases.base.dto.ItemInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: View 案例
 * <p>Create Date: 2017/8/10 010
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidCases 1.0.0
 */
public class ViewActivity extends BaseListActivity {
	@Override
	public List<ItemInfo> getItemInfos() {
		List<ItemInfo> items = new ArrayList<>();
		items.add(new ItemInfo("绘制图片", "drawPaint", ""));
		items.add(new ItemInfo("绘制图片", "drawPaint02", ""));

		return items;
	}

	/**
	 * 绘制图片
	 */
	private void drawPaint() throws IOException {
		// 1.Bitmap, Canvas, Paint
		int width = 500;
		int height = 400;
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();

		// 2.绘制第一张: 绘制矩形
		paint.setColor(Color.YELLOW);
		canvas.drawRect(0, 0, width, height, paint);

		// 3.绘制第二张: 绘制文本
		// 3.1.设置文本
		String text = "AndroidCases";
		paint.setTextSize(72);

		// 3.2.获取文本边界
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);

		// 3.3.绘制文本(居中, 文字的坐标是左下角)
		paint.setColor(Color.RED);
		canvas.drawText(text, (width - bounds.width()) / 2, (height + bounds.height()) / 2, paint);

		// 4.绘制第三张: 路径(线)
		// 4.1.生成路径
		Path path = new Path();
		path.moveTo(50, 50); // 移动到起点
		path.lineTo(50, 350);
		path.lineTo(350, 350);
		path.lineTo(350, 50);
		path.close(); // 封闭首尾

		// 4.2.绘制路径
		int unit = TypedValue.COMPLEX_UNIT_DIP;
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();

		paint.setStyle(Paint.Style.STROKE); // 空心
		paint.setStrokeWidth(TypedValue.applyDimension(unit, 10, metrics)); // 边框粗细, 向两边加大, 10dp
		paint.setColor(Color.BLUE);
		canvas.drawPath(path, paint);

		// 5.绘制的对象显示到页面
		imgItem.setImageBitmap(bitmap);

		// 6.保存到SD卡中
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, Environment.DIRECTORY_PICTURES + "/AndroidCases_View.png");
		logError("[文件保存路径]: ", file.getAbsolutePath());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	// 制作头像
	private void drawPaint02() throws Exception {
		// 1.读取
		Bitmap headPiture = BitmapFactory.decodeResource(getResources(), R.mipmap.item_card);
		int width = headPiture.getWidth() + 20;
		int height = headPiture.getHeight() + 20;

		// 2.Bitmap, Canvas, Paint
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true); // 锯齿
		// Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 锯齿

		// 3.设置合成模式
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

		// 4.1.绘制第一个图片: 实心圆
		paint.setColor(Color.BLUE);
		int radius = Math.min(width, height) / 2;
		canvas.drawCircle(width / 2, height / 2, radius, paint);

		// 4.2.叠加第二张图片: 绘制空心圆
		paint.setStyle(Paint.Style.STROKE); // 空心
		paint.setStrokeWidth(10); // 边框粗细, 以圆边向两边加大
		paint.setColor(Color.GREEN);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		canvas.drawCircle(width / 2, height / 2, radius, paint);

		// 4.3.叠加第三张图片: 读取的图片
		canvas.drawBitmap(headPiture, 10, 10, paint);

		// 5.绘制的对象显示到页面
		imgItem.setImageBitmap(bitmap);

		// 6.保存到SD卡中
		File sdcard = Environment.getExternalStorageDirectory();
		File file = new File(sdcard, Environment.DIRECTORY_PICTURES + "/AndroidCases_Circle.png");
		logError("[文件保存路径]: ", file.getAbsolutePath());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}
}
