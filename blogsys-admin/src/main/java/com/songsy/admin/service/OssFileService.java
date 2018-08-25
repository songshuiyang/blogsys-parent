package com.songsy.admin.service;

import com.songsy.admin.entity.OssFile;
import com.songsy.base.BaseService;

/**
 * Oss上传service
 * @author songshuiyang
 * @date 2018/2/11 22:54
 */
public interface OssFileService extends BaseService<OssFile, String> {
    int insertOneRecord(OssFile record);
}
