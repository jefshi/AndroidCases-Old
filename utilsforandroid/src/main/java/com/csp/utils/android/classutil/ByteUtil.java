package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.csp.utils.android.log.LogCat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Byte 操作工具类
 * Created by csp on 2017/09/11
 * Modified by csp on 2018/04/17
 *
 * @version 1.0.1
 */
@SuppressWarnings("unused")
public class ByteUtil {

    private final static int MIN_BUFFER_LENGTH = 8192; // 8 KB

    /**
     * InputStream -> byte[]
     *
     * @param is InputStream
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] toBytes(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[MIN_BUFFER_LENGTH];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }
        return os.toByteArray();
    }

    /**
     * Bitmap -> byte[]
     *
     * @param bitmap  Bitmap
     * @param quality Hint to the compressor, 0-100.
     * @return byte[]
     */
    public static byte[] toBytes(Bitmap bitmap, int quality) {
        if (bitmap == null)
            return new byte[0];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            LogCat.printStackTrace(e);
        }
        return bytes;
    }

    /**
     * Drawable -> byte[]
     *
     * @param drawable Drawable
     * @return byte[]
     */
    public static byte[] toBytes(Drawable drawable) {
        return toBytes(BitmapUtil.toBitmap(drawable), 100);
    }
}