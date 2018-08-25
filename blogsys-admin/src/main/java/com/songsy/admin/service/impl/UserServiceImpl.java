package com.songsy.admin.service.impl;

import com.songsy.admin.dao.UserMapper;
import com.songsy.admin.dto.UserDTO;
import com.songsy.admin.entity.User;
import com.songsy.admin.service.UserService;
import com.songsy.base.BaseMapper;
import com.songsy.core.base.impl.BaseServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 系统用户
 * @author songshuiyang
 * @date 2017/12/17 11:58
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Integer> implements UserService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper mapper;

    @Override
    public BaseMapper<User, Integer> getMappser() {
        return mapper;
    }
    /**
     * 获取所有
     * @return
     */
    public List<User> findAll() {
        return mapper.findAll();
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        return mapper.getByUsername(username);
    }

    /**
     * 根据用户名获取用户code
     *
     * @param username
     * @return
     */
    @Override
    public Set<String> getRoles(String username) {
        return mapper.getRoles(username);
    }

    /**
     * 根据用户名获取权限code
     * @param username
     * @return
     */
    @Override
    public Set<String> getPermissions(String username) {
        return mapper.getPermissions(username);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    @Override
    public Page<User> findPageList(int pageNum, int pageSize, UserDTO user) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page<User>) mapper.findPageList(user);
    }

    /**
     * 检查名称是否存在
     * @param username
     */
    @Override
    public Boolean checkUser(String username){
        Boolean result = true;
        List<User> userList = findAll();
        for (User user: userList) {
            if (user.getUsername() != null && user.getUsername().equals(username)) {
                result = false;
                return result;
            }
        }
        return result;
    }
}
