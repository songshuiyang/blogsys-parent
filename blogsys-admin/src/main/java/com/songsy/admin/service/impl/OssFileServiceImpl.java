package com.songsy.admin.service.impl;

import com.songsy.admin.dao.OssFileMapper;
import com.songsy.admin.entity.OssFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云OSS
 * @author songshuiyang
 * @date 2018/2/11 22:45
 */
@Service
public class OssFileServiceImpl{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OssFileMapper ossFileMapper;

    public int insertOneRecord(OssFile record){
       return ossFileMapper.insert(record);
    }
}
