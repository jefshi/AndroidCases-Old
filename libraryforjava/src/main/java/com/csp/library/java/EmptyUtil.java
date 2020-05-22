package com.csp.library.java;

import java.util.Collection;
import java.util.Map;

/**
 * 判定数据是否为空
 * Created by csp on 2017/07/18
 * Modified by csp on 2019/07/16
 *
 * @version 1.0.2
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class EmptyUtil {

    /**
     * 字符串是否为空
     *
     * @param chars 字符串
     * @return true: 是
     */
    public static boolean isBlank(CharSequence chars) {
        return chars == null || chars.length() == 0;
    }

    /**
     * 字符串内容是否为空（会去掉左右空白字符后再判断）
     *
     * @param str 字符串
     * @return true: 是
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 集合（迭代器）是否为空
     *
     * @param iterable 集合
     * @return true: 是
     */
    public static boolean isEmpty(Iterable iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return true: 是
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 集合是否为空
     *
     * @param map 集合
     * @return true: 是
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 数组是否为空
     *
     * @param array 集合
     * @return true: 是
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @see #isEmpty(Object[])
     */
    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @see #isEmpty(Object[])
     */
    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @see #isEmpty(Object[])
     */
    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @see #isEmpty(Object[])
     */
    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @see #isEmpty(Object[])
     */
    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }
}
