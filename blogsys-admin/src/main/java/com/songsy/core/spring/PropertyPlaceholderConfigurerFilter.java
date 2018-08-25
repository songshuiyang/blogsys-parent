package com.songsy.core.spring;

import com.songsy.core.utils.encryption.RSAUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * 属性文件加密属性过滤
 *
 * @author songshuiyang
 * @date 2018/1/20 19:55
 */
public class PropertyPlaceholderConfigurerFilter extends PropertyPlaceholderConfigurer {

    @Override
    public String convertProperty(String propertyName, String propertyValue) {
        String decryptValue = null;
        // 以encryption结尾的属性值进行了RSA私钥加密, 需要使用公钥进行解密
        if (propertyName.endsWith("encryption")) {
            decryptValue = RSAUtils.decrypt(propertyValue);
        }
        if (decryptValue != null) {
            return decryptValue;
        }
        return propertyValue;
    }
}
