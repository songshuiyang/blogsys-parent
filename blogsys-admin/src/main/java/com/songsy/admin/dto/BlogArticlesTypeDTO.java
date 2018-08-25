package com.songsy.admin.dto;

import com.songsy.admin.common.DictEnum;

/**
 * @author songshuiyang
 * @date 2018/4/4 23:32
 */
public class BlogArticlesTypeDTO {

    private String name;
    private Integer type;
    private Integer value;

    public String getName() {
        return type != null ? DictEnum.BlogArticles.Type.getMap().get(this.type) : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
