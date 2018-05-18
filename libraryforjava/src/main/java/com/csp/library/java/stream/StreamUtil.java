package com.csp.library.java.stream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
public class StreamUtil {
    private final static int MIN_BUFFER_LENGTH = 8192; // 8 KB

    /**
     * InputStream -> ByteArrayOutputStream
     *
     * @param is InputStream
     * @param os OutputStream
     * @throws IOException IOException
     */
    public static void switchStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[MIN_BUFFER_LENGTH];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }
    }

    /**
     * byte[] -> InputStream
     *
     * @param bytes byte[]
     * @return InputStream
     */
    public static InputStream toInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
