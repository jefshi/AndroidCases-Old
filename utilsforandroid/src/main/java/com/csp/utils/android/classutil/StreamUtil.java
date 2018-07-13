package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Stream 操作工具类
 * Created by csp on 2017/04/17.
 * Modified by csp on 2017/04/17.
 *
 * @version 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class StreamUtil extends com.csp.library.java.stream.StreamUtil {

    /**
     * Bitmap -> InputStream
     *
     * @param bitmap  Bitmap
     * @param quality Hint to the compressor, 0-100.
     * @return InputStream`
     */
    @SuppressWarnings({"SameParameterValue", "TryFinallyCanBeTryWithResources"})
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
