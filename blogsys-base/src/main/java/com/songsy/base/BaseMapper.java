package com.songsy.base;

import java.io.Serializable;
import java.util.List;

/**
 * mapper抽象接口类, 提取接口公共方法
 * @author songshuiyang
 * @date 2018/2/28 22:52
 */
public interface BaseMapper <T,E extends Serializable>{

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(E id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(T record);

    /**
     *插入非空字段
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(E id);

    /**
     * 更新非空数据
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
     * 通用分页查询
     * @param params
     * @return
     */
    List<T> findPageList(T params);


//    /**
//     * 查询
//     * @param record
//     * @return
//     */
//    List<T> selectListByPage(T record);

}
