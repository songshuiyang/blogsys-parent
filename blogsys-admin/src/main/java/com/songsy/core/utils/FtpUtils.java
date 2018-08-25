package com.songsy.core.utils;

import com.songsy.core.config.FTPConfig;
import com.google.common.collect.Maps;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * ftp 文件上传类
 * @author songshuiyang
 * @date 2018/6/5 21:34
 */
@Component
public class FtpUtils {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

    private static FTPClient ftpClient = new FTPClient();
    /**
     * 路径分隔符
     */
    private static final String SEPARATOR = "/";
    /**
     * FTP访问路径前缀
     */
    // private static final String FTP_PREFIX = "ftp://";
    private static final String FTP_URL_PREFIX = "ftp://";
    /**
     * nginx访问路径前缀
     */
    private static final String NGINX_URL_PREFIX = "http://";

    @Autowired
    private static FTPConfig config;

    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @return 上传结果
     */
    public static Map<String, Object> upload(InputStream inputStream, String fileName)  {
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", "success");
        try {
            ftpClient.connect(FTPConfig.ftpHost, FTPConfig.ftpPort);
            ftpClient.login(FTPConfig.ftpUserName, FTPConfig.ftpUserPasswd);
            //设置ftp字节流
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "failed");
            result.put("message", "ftp连接失败" + e.toString());
            return result;
        }
        int reply;
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                result.put("status", "failed");
                result.put("message", "ftp关闭连接失败" + e.toString());
                return result;
            }
            return null;
        }
        ftpClient.enterLocalPassiveMode();
        String basePath = FTPConfig.remoteFileAddr;
        try {
            ftpClient.changeWorkingDirectory(basePath);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "failed");
            result.put("message", "ftp切换目录失败" + e.toString());
            return result;
        }

        // 为当天日期文件创建追加目录
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String appendFile = sdf.format(today);
        // 切换到上传目录
        try {
            if (!ftpClient.changeWorkingDirectory(basePath + appendFile)) {
                //如果目录不存在创建目录
                String[] dirs = appendFile.split(SEPARATOR);
                String tempTargetPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) {
                        continue;
                    }
                    tempTargetPath += SEPARATOR + dir;
                    if (!ftpClient.changeWorkingDirectory(tempTargetPath)) {
                        if (!ftpClient.makeDirectory(tempTargetPath)) {
                            logger.error("ftp文件目录创建失败!");
                            return null;
                        } else {
                            ftpClient.changeWorkingDirectory(tempTargetPath);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "failed");
            result.put("message", "ftp切换目录失败" + e.toString());
            return result;
        }
        String ftpFileName;
        String currentTime = DateUtils.getNow("HHmmss");
        try {
            // 解决文件名变?的问题
            ftpFileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
            // Oss上的文件名 添加时间字符串值防止上传相同名字的文件发生重叠的情况
            ftpFileName = currentTime + "-"+ ftpFileName;


            if (!ftpClient.storeFile(ftpFileName, inputStream)) {
                logger.error("文件{}存储过程出错", fileName);
                return null;
            }
            inputStream.close();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "failed");
            result.put("message", "ftp文件上传失败" + e.toString());
            return result;
        }
        // 直接存储为匿名用户可直接访问下载的地址
        // String finalFileUrl = FTP_URL_PREFIX + FTPConfig.ftpNetAdd + SEPARATOR + "pub" + SEPARATOR + appendFile + SEPARATOR + currentTime + "-"  + fileName;
        String finalFileUrl = NGINX_URL_PREFIX  + FTPConfig.ftpNetAdd + SEPARATOR + FTPConfig.ftpNetroute + SEPARATOR + appendFile + SEPARATOR + currentTime + "-"  + fileName;
        result.put("url", finalFileUrl);
        logger.info("文件上传FTP服务器成功, 访问文件路径为 : " + finalFileUrl);
        return result;
    }

    /**
     * 从FTP服务器上下载文件
     * @param remote 远程文件路径
     * @param local 本地文件路径
     * @return 是否成功
     * @throws Exception
     */
    public boolean download(String remote, String local) throws Exception {
        ftpClient = new FTPClient();
        ftpClient.connect(FTPConfig.ftpHost, FTPConfig.ftpPort);
        ftpClient.login(FTPConfig.ftpUserName, FTPConfig.ftpUserPasswd);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        int reply;
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return false;
        }

        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        boolean result;
        File f = new File(local);
        FTPFile[] files = ftpClient.listFiles(remote);
        if (files.length != 1) {
            System.out.println("远程文件不唯一");
            ftpClient.disconnect();
            return false;
        }
        long lRemoteSize = files[0].getSize();
        if (f.exists()) {
            OutputStream out = new FileOutputStream(f, true);
            System.out.println("本地文件大小为:" + f.length());
            if (f.length() >= lRemoteSize) {
                logger.error("本地文件大小大于远程文件大小，下载中止");
                out.close();
                ftpClient.disconnect();
                return false;
            }
            ftpClient.setRestartOffset(f.length());
            result = ftpClient.retrieveFile(remote, out);
            out.close();
        } else {
            OutputStream out = new FileOutputStream(f);
            result = ftpClient.retrieveFile(remote, out);
            out.close();
        }
        ftpClient.disconnect();
        return result;
    }

    public static void main(String[] args) throws Exception{
        InputStream inputStream = new FileInputStream(new File("D://百度.gif"));
        FtpUtils.upload(inputStream,"百度.gif");
    }

}
