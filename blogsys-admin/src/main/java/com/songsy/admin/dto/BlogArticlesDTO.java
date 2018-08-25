package com.songsy.admin.dto;

import com.songsy.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 博客文章DTO
 * @author songshuiyang
 * @date 2018/3/4 10:09
 */
@Getter
@Setter
@ToString
public class BlogArticlesDTO extends BaseEntity{

    private String title;

    private String outline;

    private String coverImage;

    private String author;

    private String category;

    private Integer type;

    private String tag;

    private Integer hitsNum;

    private Integer satisfactoryNum;

    private Integer dissatisfiedNum;

    private Integer commentNum;

    private String content;

    private Date startDate;

    private Date endDate;
}
