package com.songsy.admin.dao;

import com.songsy.admin.dto.UserDTO;
import com.songsy.admin.entity.User;
import com.songsy.base.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * 用户
 * @author songshuiyang
 * @date 2017/11/28 20:12
 */
public interface UserMapper extends BaseMapper<User,Integer> {
    /**
     * 根据username获取用户
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 根据用户名获取角色code
     * @param username
     * @return
     */
    Set<String> getRoles(String username);

    /**
     * 根据用户名获取权限code
     * @param username
     * @return
     */
    Set<String> getPermissions(String username);

    /**
     * 分页查询
     * @param user
     * @return
     */
    List<User> findPageList(UserDTO user);

    /**
     * 获取所有
     * @return
     */
    List<User> findAll();
}