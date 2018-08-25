package com.songsy.admin.service.impl;

import com.songsy.admin.common.CommonConstant;
import com.songsy.admin.dao.SysResourceMapper;
import com.songsy.admin.entity.SysResource;
import com.songsy.admin.service.SysResourceService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统资源service
 * @author songshuiyang
 * @date 2018/3/13 10:12
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource,Integer> implements SysResourceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysResourceMapper mapper;

    @Override
    public BaseMapper<SysResource,Integer> getMappser(){
        return mapper;
    }

    @Override
    public List<SysResource> findAll(){
        return mapper.findAll();
    }

    @Override
    public List<SysResource> findResourceByPkey(String Pkey){
        return mapper.findResourceByPkey(Pkey);
    }

    /**
     * 随机取一张头像
     * @return
     */
    @Override
    public SysResource getRandomHeadPortrait(){
        SysResource sysResource = mapper.getRandomHeadPortrait();
        if (sysResource == null) {
            sysResource = new SysResource();
            sysResource.setValue(CommonConstant.SYS_RESOURCE_HEADPORTRAIT);
        }
        return sysResource;
    }
    /**
     * 获取时间线资源
     * @return
     */
    @Override
    public List<SysResource> getTimeLine(){
        return mapper.getTimeLine();
    }

    /**
     * 获取相册分类资源
     * @return
     */
    @Override
    public List<SysResource> getPhotoAlbumCategory(){
        return mapper.getPhotoAlbumCategory();
    }
}
