package com.csp.library.java;

import java.util.regex.Pattern;

/**
 * 常用校验工具类
 * Created by csp on 2016/03/23.
 * Modified by csp on 2016/03/23.
 *
 * @version 1.0.0
 */
@SuppressWarnings("unused")
public class CheckingUtil {

    /**
     * 验证手机
     *
     * @return 0: 正确，1：长度不是 11 位，2：手机号码不正确
     */
    public static int checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        return phone.length() != 11 ? 1
                : (Pattern.compile(regex).matcher(phone).matches() ? 0 : 2);
    }

    /**
     * 验证手机
     *
     * @return true: 成功
     */
    public static boolean checkEmail(String email) {
        return Pattern.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)+", email);
    }
}
