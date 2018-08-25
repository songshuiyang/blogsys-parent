package com.songsy.admin.service;

import com.songsy.admin.entity.PhotoAlbum;
import com.songsy.base.BaseService;

import java.util.List;

/**
 * 相册service
 * @author songshuiyang
 * @date 2018/04/06 10:44
 */
public interface PhotoAlbumService extends BaseService<PhotoAlbum,Integer> {

    /**
     * 根据相册名获取图片
     * @param category
     * @return
     */
    List<PhotoAlbum> findPhotoAlbuByCategory(String category);
}
