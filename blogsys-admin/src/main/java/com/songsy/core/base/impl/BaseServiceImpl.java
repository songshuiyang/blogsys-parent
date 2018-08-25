package com.songsy.core.base.impl;

import com.songsy.base.BaseEntity;
import com.songsy.admin.entity.CurrentUser;
import com.songsy.base.BaseMapper;
import com.songsy.base.BaseService;
import com.songsy.core.shiro.ShiroUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * service抽象类, 提取接口公共方法
 * T 实体类 E 主键
 * @author songshuiyang
 * @date 2018/2/28 22:52
 */
public abstract class BaseServiceImpl<T extends BaseEntity,E extends Serializable> implements BaseService<T,E> {

    private static Logger logger= LoggerFactory.getLogger(BaseServiceImpl.class);

    public abstract BaseMapper<T,E> getMappser();

    @Override
    public int deleteByPrimaryKey(E id) {
        return getMappser().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return getMappser().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        setCurrentOperator(record);
        return getMappser().insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(E id) {
        return getMappser().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        setCurrentOperator(record);
        return getMappser().updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        setCurrentOperator(record);
        return getMappser().updateByPrimaryKeySelective(record);
    }

//    @Override
//    public List<T> selectListByPage(T record){
//        return getMappser().selectListByPage(record);
//    }

    /**
     * 分页查询
     * @param pageNum  页码
     * @param pageSize 一页数量
     * @param params   查询参数
     * @return
     */
    @Override
    public Page<T> findPageList(int pageNum, int pageSize, T params) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page<T>) getMappser().findPageList(params);
    }

    /**
     * 设置当前处理人
     * @param record
     * @return
     */
    public void  setCurrentOperator(T record){
        CurrentUser currentUser = ShiroUtils.getCurrentUser();
        if (currentUser != null && StringUtils.isNotBlank(currentUser.getNickname())) {
            // 新增记录
            if (record.getId() == null) {
                record.setCreatedBy(currentUser.getUsername());
            } else { //修改记录
               record.setLastModifiedBy(currentUser.getUsername());
            }
        }
//        //统一处理公共字段
//        Class<?> clazz=record.getClass();
//        try {
//            // 新增记录, 设置创建用户
//            if (record.getId() == null) {
//                Field field=clazz.getDeclaredField("createdBy");
//                field.setAccessible(true);
//                field.set(record,currentUser.getUsername());
//            } else { // 修改记录, 设置最后修改人
//                Field field=clazz.getDeclaredField("lastModifiedBy");
//                field.setAccessible(true);
//                field.set(record,currentUser.getUsername());
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }
//    /**
//     * 公共展示类
//     * @param t 实体
//     * @param page 页
//     * @param limit 行
//     * @return
//     */
//    @Override
//    public String  show(T t,int page,int limit){
//        List<T> tList=null;
//        Page<T> tPage= PageHelper.startPage(page,limit);
//        try{
//            tList=getMappser().selectListByPage(t);
//        }catch (MyException e){
//            logger.error("class:BaseServiceImpl ->method:show->message:"+e.getMessage());
//            e.printStackTrace();
//        }
//        ReType reType=new ReType(tPage.getTotal(),tList);
//        return JSON.toJSONString(reType);
//    }
}
