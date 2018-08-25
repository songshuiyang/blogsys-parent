package com.songsy.core.config;

import com.songsy.core.utils.PropertisUtils;

/**
 * ftp配置
 * @author songshuiyang
 * @date 2018/6/5 21:35
 */
public class  FTPConfig {
    /**
     * FTP服务器IP地址
     */
    public static String ftpHost;

    /**
     * FTP服务器端口
     */
    public static int ftpPort;

    /**
     * FTP服务器用户名
     */
    public static String ftpUserName;

    /**
     * FTP用户密码
     */
    public static String ftpUserPasswd;

    /**
     * 用户远程上传文件存储目录
     */
    public static String remoteFileAddr;

    /**
     * FTP公网映射地址
     */
    public static String ftpNetAdd;

    /**
     * FTP公网端口
     */
    public static int ftpNetPort;
    /**
     * Ngins路由
     */
    public static String ftpNetroute;

    static {
        if (PropertisUtils.applicationProperties != null) {
            ftpHost = PropertisUtils.applicationProperties.get("ftp.host");
            ftpPort = Integer.parseInt(PropertisUtils.applicationProperties.get("ftp.port"));
            ftpUserName = PropertisUtils.applicationProperties.get("ftp.username");
            ftpUserPasswd = PropertisUtils.applicationProperties.get("ftp.password");
            remoteFileAddr = PropertisUtils.applicationProperties.get("ftp.file.address");
            ftpNetAdd = PropertisUtils.applicationProperties.get("ftp.file.net.host");
            ftpNetPort = Integer.parseInt(PropertisUtils.applicationProperties.get("ftp.file.net.port"));
            ftpNetroute = PropertisUtils.applicationProperties.get("ftp.file.net.route");
        }
    }
}
