package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Description: Byte 操作工具类
 * <p>Create Date: 2018/04/17
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
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
