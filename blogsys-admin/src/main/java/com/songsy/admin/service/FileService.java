package com.songsy.admin.service;

import com.songsy.admin.entity.OssFile;
import com.songsy.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理service
 * @author songshuiyang
 * @date 2018/2/11 22:54
 */
public interface FileService extends BaseService<MultipartFile,String> {

    public OssFile uploadLocalFile(MultipartFile file);

}
