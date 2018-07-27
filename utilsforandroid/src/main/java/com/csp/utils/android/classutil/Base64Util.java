package com.csp.utils.android.classutil;

import android.graphics.Bitmap;
import android.util.Base64;

/**
 * Base64 压缩操作工具类
 * Created by csp on 2017/06/27.
 * Modified by csp on 2017/06/27.
 *
 * @version 1.0.0
 */
public class Base64Util {

    public static String encodeBase64(Bitmap bitmap) {
        byte[] bytes = ByteUtil.toBytes(bitmap, 100);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
