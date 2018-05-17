package com.csp.library.java.string;

import com.csp.library.java.EmptyUtil;
import com.csp.library.java.log.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Description: 字符串应用类
 * <p>Create Date: 2016/09/04
 * <p>Modify Date: 2018/05/17
 *
 * @author csp
 * @version 1.0.2
 * @since JavaLibrary 1.0.0
 */
@SuppressWarnings("unused")
public class StringUtil {
    private static String ENCODING = "UTF-8";

    /**
     * 将指定字符串的首字母转为大写, 并返回首字符大写的字符串
     *
     * @param str 指定字符串
     * @return String 首字母大写的字符串
     */
    public static String getFirstCapital(String str) {
        return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1, str.length());
    }

    public static String encode(String url) {
        if (EmptyUtil.isBank(url))
            return null;

        try {
            return URLEncoder.encode(url, ENCODING);
        } catch (UnsupportedEncodingException e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }

    public static String decode(String url) {
        if (EmptyUtil.isBank(url))
            return null;

        try {
            return URLDecoder.decode(url, ENCODING);
        } catch (UnsupportedEncodingException e) {
            LogUtil.printStackTrace(e);
            return null;
        }
    }
}
