package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

/**
 * Description: Byte 操作工具类
 * <p>Create Date: 2018/04/17
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class DrawableUtil {

    /**
     * Bitmap -> Drawable
     *
     * @param bitmap Bitmap
     * @return Drawable
     */
    public static BitmapDrawable toDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * byte[] -> Drawable
     *
     * @param bytes byte[]
     * @return Drawable
     */
    public static Drawable toDrawable(byte[] bytes) {
        return DrawableUtil.toDrawable(BitmapUtil.toBitmap(bytes));
    }

    /**
     * InputStream -> Drawable
     *
     * @param inputStream InputStream
     * @return Drawable
     */
    public static Drawable toDrawable(InputStream inputStream) {
        return toDrawable(BitmapUtil.toBitmap(inputStream));
    }
}
