package com.csp.utils.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by chenshp on 2018/4/17.
 */

public class DrawableBitmapUtils {


    // Bitmap转换成byte[]
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // byte[]转换成Bitmap
    public Bitmap Bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return null;
    }
}
