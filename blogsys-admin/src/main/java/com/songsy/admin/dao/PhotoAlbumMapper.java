package com.songsy.admin.dao;

import com.songsy.admin.entity.PhotoAlbum;
import com.songsy.base.BaseMapper;

import java.util.List;

/**
 * 相册
 * @author songshuiyang
 * @date 2018/04/06 10:43
 */
public interface PhotoAlbumMapper extends BaseMapper<PhotoAlbum,Integer> {
    /**
     * 根据相册名获取图片
     * @param category
     * @return
     */
    List<PhotoAlbum> findPhotoAlbuByCategory(String category);

}