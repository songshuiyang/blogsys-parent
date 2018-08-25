package com.songsy.core.utils.elven.encryption;

import java.util.Base64;

/**
 * BASE64算法实现加解密
 *
 * @author xzb
 */
public class Base64Util {

    /**
     * base64算法加密
     *
     * @param data
     * @return
     */
    public static String base64Encrypt(byte[] data) {


        return new String(Base64.getEncoder().encode(data));
    }

    /**
     * base64算法解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String base64Decrypt(String data) throws Exception {
        byte[] resultBytes = Base64.getDecoder().decode(data.getBytes());
        return new String(resultBytes);
    }
}
