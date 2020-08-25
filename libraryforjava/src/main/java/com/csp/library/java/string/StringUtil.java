package com.csp.library.java.string;

import com.csp.library.java.EmptyUtil;
import com.csp.library.java.stream.StreamUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * String 操作工具类
 * Created by csp on 2016/09/04.
 * Modified by csp on 2018/05/17.
 *
 * @version 1.0.2
 */
@SuppressWarnings("unused")
public class StringUtil {
    private static String ENCODING = "UTF-8";

    /**
     * 输入流 -> 字符串
     *
     * @param is InputStream
     * @return String
     * @throws IOException IOException
     */
    @SuppressWarnings("TryFinallyCanBeTryWithResources")
    public static String toString(InputStream is) throws IOException {
        String result;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            StreamUtil.switchStream(is, baos);
            result = baos.toString();
        } finally {
            baos.close();
        }
        return result;
    }

    /**
     * URL 编码
     *
     * @param url url
     * @return URL 编码结果
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String encode(String url) throws UnsupportedEncodingException {
        if (EmptyUtil.isBlank(url))
            return null;

        return URLEncoder.encode(url, ENCODING);
    }

    /**
     * URL 解码
     *
     * @param url url
     * @return URL 解码结果
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static String decode(String url) throws UnsupportedEncodingException {
        if (EmptyUtil.isBlank(url))
            return null;

        return URLDecoder.decode(url, ENCODING);
    }

    /**
     * 将指定字符串的首字母转为大写, 并返回首字符大写的字符串
     *
     * @param str 指定字符串
     * @return String 首字母大写的字符串
     */
    public static String getFirstCapital(String str) {
        return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1, str.length());
    }
}
