package com.csp.cases.activity.network.visit.retrolfit.phone;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class: SHA1
 * Created by se7enTina on 2015/11/24.
 * Description: SHA1算法
 */
@SuppressWarnings("ALL")
public class SHA1 {

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }


    public static String encryptToSHA(String paramString) {
        try {
            String str = encryptToSHA(paramString.getBytes());
            return str;
        } catch (Exception localException) {
        }
        return null;
    }


    //SHA1 加密实例
    public static String encryptToSHA(byte[] paramArrayOfByte) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            // 添加要进行计算摘要的信息
            alga.update(paramArrayOfByte);
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        String rs = byte2hex(digesta);
        return rs;
    }
}
