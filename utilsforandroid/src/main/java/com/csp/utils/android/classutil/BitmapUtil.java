package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
}
