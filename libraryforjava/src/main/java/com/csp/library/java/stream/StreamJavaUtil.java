package com.csp.library.java.stream;

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
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings({"unused", "UnusedAssignment", "TryFinallyCanBeTryWithResources"})
public class StreamJavaUtil {
    private final static int MIN_BUFFER_LENGTH = 8192; // 8 KB

    /**
     * 流内容转换，InputStream -> ByteArrayOutputStream
     *
     * @param is   InputStream
     * @param baos ByteArrayOutputStream
     * @throws IOException IOException
     */
    protected static void switchStream(InputStream is, ByteArrayOutputStream baos) throws IOException {
        byte[] buffer = new byte[MIN_BUFFER_LENGTH];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
            baos.flush();
        }
    }

    /**
     * 输入流 -> 字符串
     *
     * @param is InputStream
     * @return String
     * @throws IOException IOException
     */
    public static String getString(InputStream is) throws IOException {
        String result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            switchStream(is, baos);
            result = baos.toString();
        } finally {
            baos.close();
        }
        return result;
    }

    /**
     * InputStream -> byte[]
     *
     * @param is InputStream
     * @return byte[]
     * @throws IOException IOException
     */
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[MIN_BUFFER_LENGTH];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }
        return os.toByteArray();
    }

    /**
     * byte[] -> InputStream
     *
     * @param bytes byte[]
     * @return InputStream
     */
    public static InputStream getInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
