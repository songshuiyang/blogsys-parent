package com.songsy.core.utils.encryption;

import com.songsy.core.utils.BytesHexUtils;
import com.songsy.core.utils.PropertisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * RSA测试类
 */
public class RSATest {

    private static final Logger logger = LoggerFactory.getLogger(PropertisUtils.class);
    // 公钥
    private static String publicKey;
    // 私钥
    private static String privateKey;

    static {
        // generateKey();
        Map<String, String> appProperties = PropertisUtils.loadProperties("src/main/resources/project.properties");
        String encryptionPublicKey = appProperties.get("encryption.public.key");
        String encryptionPrivateKey = appProperties.get("encryption.private.key");
        if (encryptionPublicKey != null) {
            publicKey = encryptionPublicKey;
        }
        if (encryptionPrivateKey != null) {
            privateKey = encryptionPrivateKey;
        }
    }

    /**
     * 生成公钥, 私钥
     */
    public static void generateKey() {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            logger.info("公钥:{}", publicKey);
            logger.info("私钥:{}", privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        test();
//        testSign();
//        testHttpSign();
        String encryption = encryption("root", privateKey);
        decrypt(encryption, publicKey);

    }

    /**
     * 使用私钥对数据进行加密
     *
     * @param content    待加密内容
     * @param privateKey 私钥
     * @return 十六进制格式的加密数据
     */
    public static String encryption(String content, String privateKey) throws Exception {
        System.out.println("原文字：\r\n" + content);
        byte[] data = content.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        String hexString = BytesHexUtils.bytesToHexString(encodedData);
        System.out.println("加密后转成十六进制：\r\n" + hexString);
        return hexString;
    }

    /**
     * 使用公钥对数据进行解密
     *
     * @param hexString 十六进制格式的加密数据
     * @param publicKey 公钥
     * @return 解密后的数据
     */
    public static String decrypt(String hexString, String publicKey) {
        String target = null;
        try {
            byte[] data = BytesHexUtils.hexStringToBytes(hexString);

            byte[] decodedData = RSAUtils.decryptByPublicKey(data, publicKey);
            target = new String(decodedData);
            System.out.println("解密后:");
            System.out.println(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }


    public static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);

        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    public static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));


        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);


        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

    public static void testHttpSign() throws Exception {
        String param = "id=1&name=张三";
        byte[] encodedData = RSAUtils.encryptByPrivateKey(param.getBytes(), privateKey);
        System.out.println("加密后：" + encodedData);

        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后：" + new String(decodedData));

        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名：" + sign);

        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
        System.err.println("签名验证结果：" + status);
    }


}