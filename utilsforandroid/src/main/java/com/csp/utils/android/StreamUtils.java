package com.csp.utils.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
@SuppressWarnings({"unused", "TryFinallyCanBeTryWithResources"})
public class StreamUtils extends StreamJavaUtil {

    /**
     * Bitmap -> InputStream
     *
     * @param bitmap Bitmap
     * @return InputStream`
     */
    public static InputStream getInputStream(Bitmap bitmap, int quality) throws IOException {
        InputStream is = null;
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
     * InputStream -> Bitmap
     *
     * @param is InputStream
     * @return Bitmap`
     */
    public static Bitmap getBitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

//    /**
//     * Drawable -> InputStream
//     *
//     * @param drawable Drawable
//     * @return InputStream`
//     */
//    public InputStream getInputStream(Drawable drawable) {
//        Bitmap bitmap = this.drawable2Bitmap(drawable);
//        return this.Bitmap2InputStream(bitmap);
//    }
//
//    // InputStream转换成Drawable
//    public static Drawable InputStream2Drawable(InputStream is) {
//        Bitmap bitmap = getBitmap(is);
//        return this.bitmap2Drawable(bitmap);
//    }
//
//    // Drawable转换成byte[]
//    public static byte[] Drawable2Bytes(Drawable d) {
//        Bitmap bitmap = this.drawable2Bitmap(d);
//        return this.Bitmap2Bytes(bitmap);
//    }
//
//    // byte[]转换成Drawable
//    public static Drawable Bytes2Drawable(byte[] b) {
//        Bitmap bitmap = this.Bytes2Bitmap(b);
//        return this.bitmap2Drawable(bitmap);
//    }
//
//    // Drawable转换成Bitmap
//    public Bitmap drawable2Bitmap(Drawable drawable) {
//        Bitmap bitmap = Bitmap
//                .createBitmap(
//                        drawable.getIntrinsicWidth(),
//                        drawable.getIntrinsicHeight(),
//                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//                                : Bitmap.Config.RGB_565);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }
//
//    // Bitmap转换成Drawable
//    public Drawable bitmap2Drawable(Bitmap bitmap) {
//        BitmapDrawable bd = new BitmapDrawable(bitmap);
//        Drawable d = (Drawable) bd;
//        return d;
//    }
}
