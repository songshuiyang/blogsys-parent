package com.songsy.base;

import com.github.pagehelper.Page;

import java.io.Serializable;

/**
 * service抽象接口类, 提取接口公共方法
 * @author songshuiyang
 * @date 2018/2/28 22:44
 */
public interface BaseService<T,E extends Serializable> {

    /**
     * 根据id删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(E id);

    /**
     * 插入单条记录
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 选择性的插入非空字段的记录
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据id查询记录
     * @param id
     * @return
     */
    T selectByPrimaryKey(E id);

    /**
     * 根据主键更新非空数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据id更新记录
     * @param id
     * @return
     */
    int updateByPrimaryKey(T id);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param parms
     * @return
     */
    Page<T> findPageList(int pageNum, int pageSize, T parms);
}
