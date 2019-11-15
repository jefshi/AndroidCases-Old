package com.csp.cases.util;

import com.csp.utils.android.log.LogCat;
import com.google.gson.Gson;

/**
 * @author 沈冲
 * @time 2018/2/13 10:24
 * @Description gson的单例工具类
 */

/**
 * gson 的单例工具类
 * Created by csp on 2017/07/14.
 * Modified by csp on 2019/09/29.
 *
 * @version 1.0.5
 */
public class GsonUtil {

    private interface Instance {

        Gson INSTANCE = new Gson();
    }

    private GsonUtil() {
    }

    public static Gson getInstance() {
        return Instance.INSTANCE;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return getInstance().fromJson(json, classOfT);
        } catch (Throwable t) {
            LogCat.printStackTrace(t);
            return null;
        }
    }

    public static String toJson(Object src) {
        try {
            return getInstance().toJson(src);
        } catch (Throwable t) {
            LogCat.printStackTrace(t);
            return null;
        }
    }
}
