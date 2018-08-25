package com.songsy.core.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.songsy.core.config.AliyunOssConfig;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.util.Map;

/**
 * 阿里云OSS上传工具
 *
 * @author songshuiyang
 * @date 2018/1/22 22:32
 */
public class AliyunOssUploadUtils {

    private static final OSSClient ossClient = AliyunOssConfig.getInstance().getClient();

    private static AliyunOssConfig ossconfig = new AliyunOssConfig();

    /**
     * 上传执行方法
     * @param inputStream
     * @param fileName
     * @param fileType    文件类型
     * @return
     */
    public static Map<String, Object> uploadFile(InputStream inputStream, String fileName,String fileType) {
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
        // Oss上的文件名 添加时间字符串值防止上传相同名字的文件发生重叠的情况
        String ossFileName = DateUtils.getNow("HHmmss") + "-"+ fileName ;
        // 存放路径 blogsys/upload/20180211/ossFileName.fileType
        String fileLocation =  ossconfig.getUploadLocation() + DateUtils.getNow("yyyyMMdd") + "/" + ossFileName + "." + fileType;
        return putObject(inputStream, fileType, fileLocation);
    }

    /**
     * 阿里云OSS上传
     * @param fileType
     * @param fileName
     * @return String
     * @MethodName: putObject
     * @Description: 上传文件
     */
    private static Map<String, Object> putObject(InputStream inputStream, String fileType, String fileName){
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", "success");
        String url = null;
        try {
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传内容类型
            meta.setContentType(AliyunOssUploadUtils.contentType(fileType));
            // 被下载时网页的缓存行为
            meta.setCacheControl("no-cache");
            // 创建上传请求
            PutObjectRequest request = new PutObjectRequest(ossconfig.getBucketName(), fileName, inputStream, meta);
            ossClient.putObject(request);
            // 上传成功再返回的文件路径
            url = ossconfig.getEndpoint().replaceFirst("http://", "http://" + ossconfig.getBucketName() + ".") + "/" + fileName;
        } catch (OSSException oe) {
            oe.printStackTrace();
            result.put("status", "success");
            result.put("message", "OSSException");
            return result;
        } catch (ClientException ce) {
            ce.printStackTrace();
            result.put("status", "fail");
            result.put("message", "ClientException");
            return result;
        } finally {
        }
        result.put("url", url);
        return result;
    }

    /**
     * 获取文件类型
     * @param fileType
     * @return String
     * @MethodName: contentType
     */
    private static String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }
    public static void main(String[] args) throws Exception{
        String sdf = "124234.jsp";
        String fileName = sdf.substring(0,sdf.lastIndexOf("."));
        System.out.println(fileName);
//        File file = new File("D://oss-test.txt");
//        InputStream inputStream = new FileInputStream(file);
//        System.out.println("路径: " + AliyunOssUploadUtils.uploadFile(inputStream ,"文件名","txt"));
    }

}
