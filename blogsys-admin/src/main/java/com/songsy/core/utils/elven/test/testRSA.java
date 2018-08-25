package com.songsy.core.utils.elven.test;


import com.songsy.core.utils.elven.encryption.BytesToHex;
import com.songsy.core.utils.elven.encryption.RSAUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public class testRSA {

    //待加密原文
    public static final String DATA = "hi, welcome to my git area!";

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = RSAUtil.initKey();

        RSAPublicKey rsaPublicKey = RSAUtil.getpublicKey(keyMap);
        RSAPrivateKey rsaPrivateKey = RSAUtil.getPrivateKey(keyMap);
        System.out.println("RSA PublicKey: " + rsaPublicKey);
        System.out.println("RSA PrivateKey: " + rsaPrivateKey);

        byte[] rsaResult = RSAUtil.encrypt(DATA.getBytes(), rsaPublicKey);
        System.out.println(DATA + "====>>>> RSA 加密,转成16进制:" + BytesToHex.fromBytesToHex(rsaResult));


        byte[] plainResult = RSAUtil.decrypt(rsaResult, rsaPrivateKey);
        System.out.println("私钥解密" + new String(plainResult));
        System.out.println(DATA + "====>>>> RSA 解密,转成16进制:" + BytesToHex.fromBytesToHex(plainResult));
    }
}
