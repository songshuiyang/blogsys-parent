package com.songsy.core.config;

import com.songsy.core.utils.PropertisUtils;
import org.springframework.beans.factory.annotation.Value;
import com.aliyun.oss.OSSClient;

/**
 * 阿里云OSS配置类
 *
 * @author songshuiyang
 * @date 2018/1/22 21:52
 */
public class AliyunOssConfig {
    /**
     * OSSClient
     */
    private static OSSClient client = null;
    /**
     * AliyunOssConfig 实例
     */
    private volatile static AliyunOssConfig aliyunOssConfig = null;

    /**
     * 连接区域地址
     */
    @Value("${oss.endpoint}")
    public static String endpoint;
    /**
     * 连接keyId
     */
    @Value("${oss.accessKeyId}")
    public static String accessKeyId;
    /**
     * 连接秘钥
     */
    @Value("${oss.accessKeySecret}")
    public static String accessKeySecret;
    /**
     * 需要存储的bucketName
     */
    @Value("${oss.bucketName}")
    public static String bucketName;
    /**
     * 图片保存路径
     */
    @Value("${oss.uploadLocation}")
    public static String uploadLocation;

    static {
        if (PropertisUtils.applicationProperties != null) {
            endpoint = PropertisUtils.applicationProperties.get("oss.endpoint");
            bucketName = PropertisUtils.applicationProperties.get("oss.bucketName");
            uploadLocation = PropertisUtils.applicationProperties.get("oss.uploadLocation");
            accessKeyId = PropertisUtils.applicationProperties.get("oss.accessKeyId");
            accessKeySecret = PropertisUtils.applicationProperties.get("oss.accessKeySecret");
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getUploadLocation() {
        return uploadLocation;
    }

    public void setUploadLocation(String uploadLocation) {
        this.uploadLocation = uploadLocation;
    }

    /**
     * 获取AliyunOssConfig 实例
     *
     * @return
     */
    public static AliyunOssConfig getInstance() {
        if (aliyunOssConfig == null) {
            synchronized (AliyunOssConfig.class) {
                if (aliyunOssConfig == null) {
                    aliyunOssConfig = new AliyunOssConfig();
                }
            }
        }
        return aliyunOssConfig;
    }

    /**
     * 获取 OSSClient
     *
     * @return
     */
    public OSSClient getClient() {
        if (client == null) {
            client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return client;
    }

    public static void main(String[] args) {
        AliyunOssConfig aliyunOssConfig = new AliyunOssConfig();
        System.out.println(aliyunOssConfig.getAccessKeyId());
    }
}
