package com.songsy.admin.service.impl;

import com.songsy.admin.dao.PhotoAlbumMapper;
import com.songsy.admin.entity.*;
import com.songsy.admin.service.PhotoAlbumService;
import com.songsy.admin.service.RoleService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 相册service
 * @author songshuiyang
 * @date 2018/04/06 10:12
 */
@Service
public class PhotoAlbumServiceImpl extends BaseServiceImpl<PhotoAlbum,Integer> implements PhotoAlbumService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhotoAlbumMapper mapper;

    @Autowired
    private RoleService roleService;

    @Override
    public BaseMapper<PhotoAlbum,Integer> getMappser(){
        return mapper;
    }

    /**
     * 根据相册名获取图片
     * @param category
     * @return
     */
    @Override
    public List<PhotoAlbum> findPhotoAlbuByCategory(String category){
        return mapper.findPhotoAlbuByCategory(category);
    }

}
