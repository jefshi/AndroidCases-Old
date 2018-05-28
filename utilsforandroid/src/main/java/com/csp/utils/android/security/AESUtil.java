package com.csp.utils.android.security;

import android.util.Base64;

import com.csp.utils.android.log.LogCat;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Description: AES 工具类
 * <p>Create Date: 2017/04/11
 * <p>Modify Date: nothing
 *
 * @author csp
 * @version 1.0.0
 * @since AndroidUtils 1.0.0
 */
public class AESUtil {
    private static final String TEXT_ENCODE = "UTF-8";
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding"; // 加密算法

    /**
     * AES 加密
     *
     * @param plaintext 明文
     * @param password  密码
     * @return 密文
     */
    public static String encrypt(String plaintext, String password) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES_ECB_PKCS5PADDING); // 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(password)); // 加密模式
            byte[] bytePlaintext = plaintext.getBytes(TEXT_ENCODE);
            byte[] byteCiphertext = cipher.doFinal(bytePlaintext);// 加密
            byte[] byteBase64 = Base64.encode(byteCiphertext, Base64.DEFAULT);
            return new String(byteBase64);
        } catch (Exception e) {
            LogCat.printStackTrace(e);
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param ciphertext 密文
     * @param password   密码
     * @return 明文
     */
    public static String decrypt(String ciphertext, String password) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES_ECB_PKCS5PADDING); // 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(password)); // 解密模式
            byte[] byteCiphertext = ciphertext.getBytes(TEXT_ENCODE);
            byte[] byteBase64 = Base64.decode(byteCiphertext, Base64.DEFAULT);
            byte[] bytePlaintext = cipher.doFinal(byteBase64);
            return new String(bytePlaintext, TEXT_ENCODE);
        } catch (Exception e) {
            LogCat.printStackTrace(e);
        }
        return null;
    }

    /**
     * 生成密钥
     *
     * @param password 密码
     * @return SecretKeySpec
     * @throws NoSuchAlgorithmException 算法错误
     */
    private static SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(128, new SecureRandom(password.getBytes())); // AES 要求密钥长度为 128
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }
}