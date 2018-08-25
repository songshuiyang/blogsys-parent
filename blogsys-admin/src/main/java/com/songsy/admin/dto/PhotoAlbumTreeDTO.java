package com.songsy.admin.dto;

import com.songsy.admin.entity.PhotoAlbum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author songshuiyang
 * @date 2018/4/6 15:01
 */
@Getter
@Setter
@ToString
public class PhotoAlbumTreeDTO {
    /**
     * 文章分类
     */
    String category;
    /**
     * 图片
     */
    List<PhotoAlbum> photoAlbums;
}
