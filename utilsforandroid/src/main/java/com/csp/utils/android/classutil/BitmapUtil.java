package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

/**
 * Bitmap 操作工具类
 * Created by csp on 2017/04/17.
 * Modified by csp on 2017/04/17.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BitmapUtil {

    // ==============================
    // 数据类型转换
    // ==============================

    /**
     * byte[] -> Bitmap
     *
     * @param bytes byte[]
     * @return Bitmap
     */
    public static Bitmap toBitmap(byte[] bytes) {
        return bytes == null || bytes.length == 0 ? null
                : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Drawable -> Bitmap
     *
     * @param drawable drawable
     * @return Bitmap
     */
    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable == null)
            return null;

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        width = width <= 0 ? 1 : width;
        height = height <= 0 ? 1 : height;

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE
                ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        drawable.draw(new Canvas(bitmap)); // 将 Drawable 内容画到 Canvas 中
        return bitmap;
    }

    /**
     * InputStream -> Bitmap
     *
     * @param is InputStream
     * @return Bitmap
     */
    public static Bitmap toBitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }


    static int calculateInSampleSize(BitmapFactory.Options bitmapOptions, int reqWidth, int reqHeight) {
        final int height = bitmapOptions.outHeight;
        final int width = bitmapOptions.outWidth;
        int sampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return sampleSize;
    }

    public static Bitmap decodeImage(String filePath) {
        /** Decode image size */
        BitmapFactory.Options o = new BitmapFactory.Options();
        /** 只取宽高防止oom */
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        int scale = calculateInSampleSize(o, displayStats.maxItemWidthHeight, displayStats.maxItemWidthHeight);

        BitmapFactory.Options options = new BitmapFactory.Options();
        /** Decode with inSampleSize，比直接算出options中的使用更少的内存*/
        options.inSampleSize = scale;
        /** 内存不足的时候可被擦除 */
        options.inPurgeable = true;
        /** 深拷贝 */
        options.inInputShareable = true;

        return BitmapFactory.decodeFile(filePath, options);
    }

    // ==============================
    // 缩放
    // ==============================

    /**
     * Bitmap 缩放
     *
     * @param bitmap 原图
     * @param matrix 三维坐标缩放
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, Matrix matrix) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false); // Android 2.3 以上，老 Bitmap 不需要 recycle
    }

    /**
     * Bitmap 缩放
     *
     * @param bitmap    原图
     * @param newWidth  新图的宽（X 轴）
     * @param newHeight 新图的高（Y 轴）
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float newWidth, float newHeight) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / width, newHeight / height);
        return scaleBitmap(bitmap, matrix);
    }

    /**
     * Bitmap 缩放
     *
     * @param bitmap 原图
     * @param ratio  缩放比例（X、Y 轴）
     * @return new Bitmap
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float ratio) {
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        return scaleBitmap(bitmap, matrix);
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}
