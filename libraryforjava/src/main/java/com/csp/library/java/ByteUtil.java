package com.csp.library.java;

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
 * @since JavaLibrary 1.0.0
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
}
