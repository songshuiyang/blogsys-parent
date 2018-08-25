package com.songsy.admin.service.impl;

import com.songsy.admin.common.CommonConstant;
import com.songsy.admin.entity.OssFile;
import com.songsy.core.utils.FtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 文件上传类
 *
 * @author songshuiyang
 * @date 2018/2/11 20:29
 */
@Service
public class FileServiceImpl{

    @Autowired
    private OssFileServiceImpl ossFileService;

    /**
     * 根据 MultipartFile上传文件到OSS上
     *
     * @param file
     */
    public OssFile uploadFileByMultipartFile(MultipartFile file) {
        OssFile ossfile = new OssFile();
        // 文件名
        String fileName = file.getOriginalFilename();
        // 文件大小
        Long fileSize = file.getSize();
        // 文件类型
        String fileType = file.getContentType();
        // 文件后缀名
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

        ossfile.setFileName(fileName);
        ossfile.setFileSize(fileSize);
        ossfile.setFileType(fileType);
        ossfile.setUploadStatus(CommonConstant.RESULT_STATUS_SUCCESS);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            ossfile.setUploadStatus(CommonConstant.RESULT_STATUS_EXCEPTION);
            ossfile.setUploadMessage("InputStream Exception");
        }
        // Map<String, Object> result = AliyunOssUploadUtils.uploadFile(inputStream, fileName, extensionName);
        // 阿里云OSS改为ftp文件服务器
        Map<String, Object> result = FtpUtils.upload(inputStream, fileName);
        if (result.get("status").equals("success")) {
            ossfile.setFileSrc((String)result.get("url"));
        } else {
            ossfile.setUploadStatus(CommonConstant.RESULT_STATUS_EXCEPTION);
            ossfile.setUploadMessage((String)result.get("message"));
        }
        ossFileService.insertOneRecord(ossfile);
        return ossfile;
    }

    /**
     * 根据 InputStream 输入流上传文件到OSS上
     * @param inputStream
     * @param fileType
     * @param fileSize
     * @param fileName
     * @param extensionName
     * @return
     */
    public OssFile uploadFileByInputStream(InputStream inputStream, String fileType, Long fileSize,String fileName, String extensionName) {
        OssFile ossfile = new OssFile();
        ossfile.setFileName(fileName);
        ossfile.setFileSize(fileSize);
        ossfile.setFileType(fileType);
        ossfile.setUploadStatus(CommonConstant.RESULT_STATUS_SUCCESS);
        // Map<String, Object> result = AliyunOssUploadUtils.uploadFile(inputStream, fileName, extensionName);
        // 阿里云OSS改为ftp文件服务器
        Map<String, Object> result = FtpUtils.upload(inputStream, fileName);
        if (result.get("status").equals("success")) {
            ossfile.setFileSrc((String)result.get("url"));
        } else {
            ossfile.setUploadStatus(CommonConstant.RESULT_STATUS_EXCEPTION);
            ossfile.setUploadMessage((String)result.get("message"));
        }
        ossFileService.insertOneRecord(ossfile);
        return ossfile;
    }
}
