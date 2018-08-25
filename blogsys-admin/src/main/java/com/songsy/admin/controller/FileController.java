package com.songsy.admin.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.songsy.admin.entity.OssFile;
import com.songsy.admin.entity.UeditorState;
import com.songsy.admin.service.impl.FileServiceImpl;
import com.songsy.base.BaseController;
import com.songsy.core.utils.Base64Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.songsy.core.utils.MessageUtils.success;

/**
 * 阿里云OSS文件上传控制器
 * @author songshuiyang
 * @date 2018/2/11 20:22
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileServiceImpl fileServiceImpl;

    /**
     * 文件上传
     * produces="application/json;charset=UTF-8 解决服务器返回406问题
     * @param file
     * @return
     * @throws OSSException
     * @throws ClientException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadLocal", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public UeditorState uploadLocalFile(@RequestParam(value = "upfile",required = false) MultipartFile file) throws OSSException, ClientException, IOException {
        Map<String, Object> resultMap = new HashMap<>();
        OssFile file1 = fileServiceImpl.uploadFileByMultipartFile(file);
        UeditorState ueditorState = new UeditorState("SUCCESS",file1.getFileSrc(),file1.getFileName(),file1.getFileName());
        return ueditorState;
    }
    /**
     * 上传涂鸦
     * @param upfile
     * @return
     * @throws OSSException
     * @throws ClientException
     * @throws IOException
     */
    @RequestMapping(value = "/uploadScrawl", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public UeditorState uploadscrawl(String upfile) throws Exception {
        byte [] bytes= Base64Utils.decode(upfile);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String fileType = "image/png";
        Long fileSize = new Long((long)bytes.length);
        String fileName = "scrawl" + System.currentTimeMillis() + ".png";
        String extensionName = "png";
        OssFile file1 = fileServiceImpl.uploadFileByInputStream(inputStream, fileType,fileSize,fileName,extensionName);
        UeditorState ueditorState = new UeditorState("SUCCESS",file1.getFileSrc(),file1.getFileName(),file1.getFileName());
        return ueditorState;
    }

}
