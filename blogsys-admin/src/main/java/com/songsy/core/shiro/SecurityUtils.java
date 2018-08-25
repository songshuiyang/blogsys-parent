package com.songsy.core.shiro;

import com.songsy.admin.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import sun.misc.BASE64Encoder;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author songshuiyang
 * @title: 加密密码
 * @description:
 * @date 2017/12/11 21:07
 */
public class SecurityUtils {

    // 算法名称
    public final static String HASH_ALGORITHM = "SHA-1";
    // 迭代次数
    public final static int HASH_INTERATIONS = 1024;
    // 盐值大小
    private final static int SALT_SIZE = 16;

    /**
     * 加密密码
     *
     * @param user
     */
    public void encryptPassword(User user) {
        user.setSalt(generateSalt());
        // 通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现
        String newPassword = new SimpleHash(
                HASH_ALGORITHM,           //加密算法
                user.getPassword(),       //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),  //salt盐   username + salt
                HASH_INTERATIONS   //迭代次数
        ).toHex();
        user.setPassword(newPassword);
    }


    /**
     * 利用secureRandom 生成一个随机的Salt
     */
    public static String generateSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return new BASE64Encoder().encode(salt);
    }

    /**
     * 加密密码
     * 密码 + 新盐（用户名+ 盐）”的方式生成散列值：
     *
     * @param password
     * @param salt
     * @return
     */
    public static String entryptPassword(String username, String password, String salt) {
        // 通过调用SimpleHash时指定散列算法，其内部使用了Java的MessageDigest实现
        SimpleHash hash = new SimpleHash(
                HASH_ALGORITHM,
                password,
                username + salt,
                HASH_INTERATIONS);
        String encodedPassword = hash.toHex();
        return encodedPassword;
    }

    public static void main(String[] args) {
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        // System.out.println(generateSalt());
//        System.out.println("生成密码" + entryptPassword("李老师", "123", generateSalt()));
//
        String salt = generateSalt();
        salt = "ir35g0SYRb9vz297hSsPxQ==";
        System.out.println("salt:" + salt);
        System.out.println(entryptPassword("333", "333", salt));

    }
}
