package org.dlg.svtool.security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 * 对称加密
 * 简单的加密工具
 * 用于对明文进行加密
 *
 * @author lingui
 * @Date 2023/11/24 11:26
 */
public class EncryptUtil {

    /**
     * 默认密钥
     */
    public static byte[] DEF_KEY = "1234567890fedcba".getBytes(StandardCharsets.UTF_8);

    /**
     * 加密:
     *
     * @param key   密钥
     * @param input 原文
     * @return 密文
     * @throws GeneralSecurityException 异常
     */
    public static byte[] encrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回:
        return ArrayUtils.addAll(iv, data);
    }

    public static String encrypt(byte[] input) {
        try {
            return new String(encrypt(DEF_KEY, input), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密:
     *
     * @param key   密钥
     * @param input 密文
     * @return 原文
     * @throws GeneralSecurityException 异常
     */
    public static byte[] decrypt(byte[] key, byte[] input) throws GeneralSecurityException {
        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密:
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }

    /**
     * 默认密钥解密
     *
     * @param input 密文
     * @return 原文
     */
    public static String decrypt(byte[] input) {
        try {
            return new String(decrypt(DEF_KEY, input), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws GeneralSecurityException {
        String passwd = "12345678";
        System.out.println("原文：" + passwd);
        byte[] encrypt = encrypt(DEF_KEY, passwd.getBytes(StandardCharsets.UTF_8));
        // c839a2084ff3c54e1d4b39c84ccbdb7df4fd0eaf883e366e53f4c1424c7f58c9
        System.out.println("密文 ： " + Hex.encodeHexString(encrypt));
        byte[] decrypt = decrypt(DEF_KEY, encrypt);
        System.out.println("原文 ： " + new String(decrypt));
    }

}
