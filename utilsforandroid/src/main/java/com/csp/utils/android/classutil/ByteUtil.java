package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Byte 操作工具类
 * Created by csp on 2018/04/17.
 * Modified by csp on 2018/04/17.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class ByteUtil {
    /**
     * Bitmap -> byte[]
     *
     * @param bitmap  Bitmap
     * @param quality Hint to the compressor, 0-100.
     * @return byte[]
     */
    public static byte[] toBytes(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return baos.toByteArray();
    }

    /**
     * Drawable -> byte[]
     *
     * @param drawable Drawable
     * @return byte[]
     */
    public static byte[] Drawable2Bytes(Drawable drawable) {
        return toBytes(BitmapUtil.toBitmap(drawable), 100);
    }
}
