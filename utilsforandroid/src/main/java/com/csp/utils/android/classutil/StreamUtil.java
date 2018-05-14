package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.csp.library.java.stream.StreamJavaUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 流操作工具类
 * <p>Create Date: 2017/09/11
 * <p>Modify Date: 无
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess", "TryFinallyCanBeTryWithResources"})
public class StreamUtil extends StreamJavaUtil {

    /**
     * Bitmap -> InputStream
     *
     * @param bitmap  Bitmap
     * @param quality Hint to the compressor, 0-100.
     * @return InputStream`
     */
    public static InputStream toInputStream(Bitmap bitmap, int quality) throws IOException {
        InputStream is;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
            is = new ByteArrayInputStream(baos.toByteArray());
        } finally {
            baos.close();
        }
        return is;
    }

    /**
     * Drawable -> InputStream
     *
     * @param drawable Drawable
     * @return InputStream`
     */
    public InputStream toInputStream(Drawable drawable) throws IOException {
        return toInputStream(BitmapUtil.toBitmap(drawable), 100);
    }
}
